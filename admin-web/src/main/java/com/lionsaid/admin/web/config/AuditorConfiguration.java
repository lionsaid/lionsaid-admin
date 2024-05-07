package com.lionsaid.admin.web.config;


import com.alibaba.fastjson2.JSONObject;
import com.lionsaid.admin.web.business.model.po.SysUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    private final StringRedisTemplate redisTemplate;

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String authorization = request.getHeader("authorization");
            if (StringUtils.isEmpty(authorization)) {
                authorization = request.getHeader("Authorization");
            }
            if (StringUtils.isNotEmpty(authorization)) {
                SysUser user = JSONObject.parseObject(redisTemplate.opsForValue().get(authorization), SysUser.class);
                return Optional.of(user.getId().toString());
            }
        } catch (Exception e) {
            log.error("AuditorConfiguration -> getCurrentAuditor: [{}]", e.getMessage());
        }
        return Optional.of("anonymous");
    }
}