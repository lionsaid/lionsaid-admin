package com.lionsaid.admin.web.config;

import com.lionsaid.admin.web.business.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import java.util.Arrays;

@Slf4j
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final UserService userService;
    private final TokenFilter tokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/private/**").authenticated()
                )
                //启用 HTTP 严格传输安全 (HSTS)
                //强制客户端只能通过 HTTPS 连接服务器，防止中间人攻击：
                .headers(headers -> headers
                       // .httpStrictTransportSecurity(i -> i.includeSubDomains(true).maxAgeInSeconds(31536000))
                        // 添加 XSS 和 Clickjacking 防护
                        //启用 Spring Security 自带的 X-Content-Type-Options、X-XSS-Protection 和 X-Frame-Options 来防止 XSS 和点击劫持攻击：
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::deny)
                        .contentTypeOptions(HeadersConfigurer.ContentTypeOptionsConfig::disable)
                        .xssProtection(HeadersConfigurer.XXssConfig::disable)
                        .addHeaderWriter(new StaticHeadersWriter(Arrays.asList(
                                new Header("Access-Control-Allow-Origin", "*"),
                                new Header("Access-Control-Expose-Headers", "Authorization")
                        )))
                )
                //当会话策略设置为 STATELESS 时，Spring Security 不会在服务器端创建或使用 HTTP 会话（HttpSession）来保存任何用户信息或身份验证状态。
                //这意味着应用程序完全依赖于每个请求所携带的身份验证信息（如 JWT 令牌或 API 密钥）来验证用户身份。服务器不会在内存或存储中维护会话状态。
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationManager(authenticationManager())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                            log.error("Unauthorized error: {}", authException.getMessage());
                            log.debug("Request Details: Method={}, URI={}", request.getMethod(), request.getRequestURI());
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            log.error("Forbidden error: {}", accessDeniedException.getMessage());
                            log.debug("Request Details: Method={}, URI={}", request.getMethod(), request.getRequestURI());
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
                        })
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
    ) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        return providerManager;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}