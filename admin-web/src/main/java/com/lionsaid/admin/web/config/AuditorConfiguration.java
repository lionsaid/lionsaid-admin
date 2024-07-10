package com.lionsaid.admin.web.config;


import com.lionsaid.admin.web.business.repository.SecurityRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * The type Auditor configuration.
 *
 * @author sunwei
 */
@Slf4j
@Component
@Configuration
@AllArgsConstructor
public class AuditorConfiguration implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            Object userId = request.getAttribute("userId");
            if (ObjectUtils.anyNull(userId)) {
                return Optional.of("anonymous");
            }
            return Optional.of(userId.toString());
        } catch (Exception e) {
            log.error("AuditorConfiguration -> getCurrentAuditor: [{}]", e.getMessage());
        }
        return Optional.of("anonymous");
    }
}