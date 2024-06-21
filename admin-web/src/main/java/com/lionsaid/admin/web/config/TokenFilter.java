package com.lionsaid.admin.web.config;

import com.lionsaid.admin.web.business.repository.SecurityRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class TokenFilter extends OncePerRequestFilter {
    private final SecurityRepository securityRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        DeferredSecurityContext deferredSecurityContext = securityRepository.loadDeferredContext(request);
        SecurityContextHolder.getContext().setAuthentication(deferredSecurityContext.get().getAuthentication());
        filterChain.doFilter(request, response);
    }

}
