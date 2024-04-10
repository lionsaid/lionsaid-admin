package com.lionsaid.admin.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

/**
 * @author lengleng
 * @date 2021/8/18
 */
@EnableWebSecurity
public class DefaultSecurityConfig {

    // @formatter:off
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        //做请求忽略规则
        return (web) -> web.ignoring().requestMatchers("/**","/login/**", "/assets/**");
    }
}