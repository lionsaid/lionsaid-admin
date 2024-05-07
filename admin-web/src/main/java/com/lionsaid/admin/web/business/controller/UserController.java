package com.lionsaid.admin.web.business.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.business.model.dto.UserDTO;
import com.lionsaid.admin.web.business.model.po.SysUser;
import com.lionsaid.admin.web.response.ResponseResult;
import com.lionsaid.admin.web.business.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @SysLog(value = "用户新增")
   // @LionSaidAuth(value = {"admin", "user_post"})
    @SneakyThrows
    @PostMapping()
    public ResponseEntity post(HttpServletRequest request,@RequestBody @Valid UserDTO dto) {
        SysUser user = JSONObject.parseObject(JSON.toJSONString(dto), SysUser.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveAndFlush(user);
        return ResponseEntity.ok(ResponseResult.success(""));
    }
}
