package com.lionsaid.admin.web.business.controller;

import com.google.common.collect.Lists;
import com.lionsaid.admin.web.business.repository.SecurityRepository;
import com.lionsaid.admin.web.business.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/private/log")
public class LoginController {

    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;
    private final UserService userService;
    private final SecurityRepository securityRepository;





    /**
     * 登录用户数量查询
     *
     * @param pattern
     * @return
     */
    public ArrayList<@Nullable String> findKey(String pattern) {
        return redisTemplate.execute((RedisCallback<ArrayList<@Nullable String>>) connection -> {
            ArrayList<@Nullable String> list = Lists.newArrayList();
            try (Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(pattern + "*").build())) {
                while (cursor.hasNext()) {
                    byte[] key = cursor.next();
                    // 检查 key 是否存在
                    list.add(new String(key));
                }
            }
            return list;
        });
    }

}
