package com.lionsaid.admin.web.business.controller;

import com.google.common.collect.Lists;
import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.business.model.dto.UserLoginDTO;
import com.lionsaid.admin.web.business.model.po.SysUser;
import com.lionsaid.admin.web.business.service.UserService;
import com.lionsaid.admin.web.exception.LionSaidException;
import com.lionsaid.admin.web.response.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;
    private final UserService userService;

    @SysLog(value = "用户登录")
    @SneakyThrows
    @GetMapping("/password")
    public ResponseEntity password(HttpServletRequest request, @NotNull @Valid UserLoginDTO dto) {
        SysUser userDetails = userService.loadUserByUsername(dto.getUsername());
        if (passwordEncoder.matches(dto.getUsername(), userDetails.getPassword())) {
            Base64.Encoder encoder = Base64.getEncoder();
            String prefix = "USER" + userDetails.getId() + "AUTH";
            ArrayList<@Nullable String> keyList = findKey(prefix);
            String token = prefix + keyList.size() + "SIZE" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
            token = encoder.encodeToString(token.getBytes(StandardCharsets.UTF_8));
            redisTemplate.opsForValue().set(token, String.valueOf(userDetails));
            redisTemplate.expire(token, 7, TimeUnit.DAYS);
            // 设置键的过期时间为7天
            return ResponseEntity.ok(ResponseResult.success(token));
        } else {
            throw new LionSaidException("用户名或密码错误", 4000001);
        }
    }

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
