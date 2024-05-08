package com.lionsaid.admin.web.config;

import com.lionsaid.admin.web.business.repository.SecurityRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class TokenFilter extends OncePerRequestFilter {
    private final StringRedisTemplate redisTemplate;
    private final SecurityRepository securityRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getRequestURI().contains("login")) {
            SecurityContext deferredSecurityContext = securityRepository.loadContext(new HttpRequestResponseHolder(request, response));
            SecurityContextHolder.getContext().setAuthentication(deferredSecurityContext.getAuthentication());
        }
        filterChain.doFilter(request, response);
    }

}
