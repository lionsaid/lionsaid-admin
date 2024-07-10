package com.lionsaid.admin.web.config;

import com.lionsaid.admin.web.business.repository.SecurityRepository;
import com.lionsaid.admin.web.exception.LionSaidException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
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
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        DeferredSecurityContext deferredSecurityContext = securityRepository.loadDeferredContext(request);
        Authentication authentication = deferredSecurityContext.get().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (ObjectUtils.anyNotNull(authentication)){
            request.setAttribute("userId", authentication.getPrincipal());
        }
        filterChain.doFilter(request, response);
    }

}

