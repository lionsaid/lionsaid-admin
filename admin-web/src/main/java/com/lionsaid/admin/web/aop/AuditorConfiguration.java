package com.lionsaid.admin.web.aop;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * The type Auditor configuration.
 *
 * @author sunwei
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class AuditorConfiguration implements AuditorAware<String> {

    private final StringRedisTemplate redisTemplate;

    @Override
    public Optional<String> getCurrentAuditor() {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.of("anonymity");
            }
            // 假设当前认证的用户是字符串类型的用户名
            return Optional.of(authentication.getName());
        } catch (Exception e) {
            log.error("AuditorConfiguration -> getCurrentAuditor: [{}]", e.getMessage());
        }
        return Optional.of("anonymity");
    }
}