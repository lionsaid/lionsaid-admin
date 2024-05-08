package com.lionsaid.admin.web.business.controller;

import com.google.common.collect.Lists;
import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.business.model.dto.UserLoginDTO;
import com.lionsaid.admin.web.business.model.po.SysUser;
import com.lionsaid.admin.web.business.repository.SecurityRepository;
import com.lionsaid.admin.web.business.service.UserService;
import com.lionsaid.admin.web.exception.LionSaidException;
import com.lionsaid.admin.web.response.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/login")
public class LoginController {

    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;
    private final UserService userService;
    private final SecurityRepository securityRepository;

    @SysLog(value = "用户登录")
    @SneakyThrows
    @PostMapping("/password")
    public ResponseEntity password(HttpServletRequest request, HttpServletResponse response, @NotNull @Valid UserLoginDTO dto) {
        SysUser userDetails = userService.loadUserByUsername(dto.getUsername());
        if (passwordEncoder.matches(dto.getUsername(), userDetails.getPassword())) {
            userDetails.setAuthorities("administration,1111,2222,3333");
            Base64.Encoder encoder = Base64.getEncoder();
            String prefix = encoder.encodeToString(("USER" + userDetails.getId() + "AUTH").getBytes(StandardCharsets.UTF_8));
            ArrayList<@Nullable String> keyList = findKey(prefix);
            String token = prefix + keyList.size() + "SIZE" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
            // 设置键的过期时间为7天
            Authentication authenticationRequest =
                    UsernamePasswordAuthenticationToken.authenticated(dto.getUsername(), dto.getPassword(), userDetails.getAuthorities());
            request.setAttribute("Authorization", token);
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authenticationRequest);
            securityRepository.saveContext(securityContext, request, response);
            log.error("authenticationResponse {}", authenticationRequest);
            return ResponseEntity.ok(ResponseResult.success(token));
        } else {
            throw new LionSaidException("用户名或密码错误", 4000001);
        }
    }

    @SysLog(value = "登出")
    @SneakyThrows
    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        securityRepository.deleteContext(request);
        return ResponseEntity.ok(ResponseResult.success("登出成功"));

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
