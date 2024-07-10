package com.lionsaid.admin.web.config;

import com.lionsaid.admin.web.business.repository.SecurityRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
@Component
@AllArgsConstructor
public class SecurityInterceptor implements HandlerInterceptor {
    private final SecurityRepository securityRepository;

    @SneakyThrows
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        DeferredSecurityContext deferredSecurityContext = securityRepository.loadDeferredContext(request);
        SecurityContextHolder.getContext().setAuthentication(deferredSecurityContext.get().getAuthentication());

        return true;
    }
}
