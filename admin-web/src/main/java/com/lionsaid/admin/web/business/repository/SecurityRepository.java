package com.lionsaid.admin.web.business.repository;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class SecurityRepository implements SecurityContextRepository {
    private final StringRedisTemplate redisTemplate;

    /**
     * @param requestResponseHolder
     * @deprecated
     */

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpServletRequest request = requestResponseHolder.getRequest();
        String authorization = request.getHeader("Authorization");
        SecurityContext securityContext = JSON.parseObject(redisTemplate.opsForValue().get(authorization), SecurityContext.class);
        return securityContext != null ? securityContext : SecurityContextHolder.createEmptyContext();
    }


    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        String authorization = request.getAttribute("Authorization").toString();
        redisTemplate.opsForValue().set(authorization, JSON.toJSONString(context));
        // 设置 Redis 键的过期时间，以确保不会永久保存安全上下文
        redisTemplate.expire(authorization, 7, TimeUnit.DAYS);
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        return redisTemplate.hasKey(authorization);
    }

    public boolean deleteContext(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        return redisTemplate.delete(authorization);
    }
}
