package com.lionsaid.admin.web.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.lionsaid.admin.web.business.model.po.SysSetting;
import com.lionsaid.admin.web.business.repository.SecurityRepository;
import com.lionsaid.admin.web.business.repository.SysSettingRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class TokenFilter extends OncePerRequestFilter {
    private final SecurityRepository securityRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.error("{}", request.getRequestURI());

        if (request.getRequestURI().contains("private")) {
            SecurityContext deferredSecurityContext = securityRepository.loadContext(new HttpRequestResponseHolder(request, response));
            SecurityContextHolder.getContext().setAuthentication(deferredSecurityContext.getAuthentication());
        }
        filterChain.doFilter(request, response);
    }

}
