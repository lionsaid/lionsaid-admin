package com.lionsaid.admin.web.controller;

import com.alibaba.fastjson.JSON;
import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.exception.LionSaidException;
import com.lionsaid.admin.web.model.dto.UserLoginDTO;
import com.lionsaid.admin.web.model.po.User;
import com.lionsaid.admin.web.response.ResponseResult;
import com.lionsaid.admin.web.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity password(HttpServletRequest request, @Valid UserLoginDTO dto) {
        User userDetails = userService.loadUserByUsername(dto.getUsername());
        if (passwordEncoder.matches(dto.getUsername(), userDetails.getPassword())) {
            throw new LionSaidException("用户名或密码错误", 4000001);
        }


        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(token, JSON.toJSONString(userDetails));
        redisTemplate.expire(token, 60, TimeUnit.SECONDS); // 设置键的过期时间为60秒
        return ResponseEntity.ok(ResponseResult.success(token));
    }
}
