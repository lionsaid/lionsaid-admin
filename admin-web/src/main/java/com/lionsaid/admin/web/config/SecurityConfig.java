package com.lionsaid.admin.web.config;

import com.alibaba.fastjson2.JSON;
import com.lionsaid.admin.web.business.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final UserService userService;
    private final StringRedisTemplate redisTemplate;
    private final TokenFilter tokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/login/**").permitAll() // 登录请求放行
                        .requestMatchers("/**").authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .headers(h -> h.addHeaderWriter(new StaticHeadersWriter(Arrays.asList(
                        new Header("Access-control-Allow-Origin", "*"),
                        new Header("Access-Control-Expose-Headers", "Authorization")
                ))))
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationManager(authenticationManager());
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