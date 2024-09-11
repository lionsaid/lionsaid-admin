package com.lionsaid.admin.web.business.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.business.model.dto.UserDTO;
import com.lionsaid.admin.web.business.model.dto.UserLoginDTO;
import com.lionsaid.admin.web.business.model.po.SysSetting;
import com.lionsaid.admin.web.business.model.po.SysUser;
import com.lionsaid.admin.web.business.model.po.SysUserDeviceInfo;
import com.lionsaid.admin.web.business.repository.SecurityRepository;
import com.lionsaid.admin.web.business.repository.SysMailLogRepository;
import com.lionsaid.admin.web.business.repository.SysSettingRepository;
import com.lionsaid.admin.web.business.service.MailService;
import com.lionsaid.admin.web.business.service.MenuService;
import com.lionsaid.admin.web.business.service.RoleService;
import com.lionsaid.admin.web.business.service.UserService;
import com.lionsaid.admin.web.enums.CommonVariables;
import com.lionsaid.admin.web.exception.LionSaidException;
import com.lionsaid.admin.web.response.ResponseResult;
import com.lionsaid.admin.web.utils.LionSaidIdGenerator;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/public")
public class PublicController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final MenuService menuService;
    private final SecurityRepository securityRepository;
    private final SysSettingRepository sysSettingRepository;
    private final SysMailLogRepository sysMailLogRepository;
    private final RoleService roleService;
    private final MailService mailService;

    @SysLog(value = "获取验证码")
    @SneakyThrows
    @PostMapping("generateVerificationCode")
    public ResponseEntity generateVerificationCode(HttpServletRequest request, @RequestBody @Valid UserLoginDTO dto) {
        SysUser sysUser = userService.loadUserByUsername(dto.getUsername());
        if (sysMailLogRepository.countByToAndTypeAndCreatedDate(dto.getUsername(), "", LocalDate.now()) > 3) {
            throw new LionSaidException(dto.getUsername() + "验证码发送已达最大次数", 4000002, request.getLocale());
        }
        if (ObjectUtils.anyNotNull(sysUser)) {
            String code = RandomStringUtils.randomNumeric(6);
            mailService.sendVerificationCodeMail(dto.getUsername(), code);
            userService.updateVerificationCodeByEmail(code, dto.getUsername());
            return ResponseEntity.ok(ResponseResult.success(""));
        }
        throw new LionSaidException(dto.getUsername() + "用户未注册", 4000002, request.getLocale());
    }


    @SneakyThrows
    @GetMapping("getQueryString")
    public ResponseEntity getQueryString(HttpServletRequest request) {
        return ResponseEntity.ok(ResponseResult.success(request.getQueryString()));
    }


    @SysLog(value = "用户注册")
    @SneakyThrows
    @PostMapping("userRegister")
    public ResponseEntity userRegister(HttpServletRequest request, @RequestBody @Valid UserDTO dto) {
        SysUser user1 = userService.loadUserByUsername(dto.getUsername());
        if (ObjectUtils.anyNotNull(user1)) {
            throw new LionSaidException("用户名已经存在,您可以尝试重置密码", 4000002, request.getLocale());
        }
        SysUser user = JSONObject.parseObject(JSON.toJSONString(dto), SysUser.class);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        user.setAuthorities(Lists.newArrayList("vip1", "generalUser").stream().collect(Collectors.joining(",")));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getUsername().contains("@")) {
            user.setEmail(user.getUsername());
            user.setEmailVerify(0);
        }
        SysUser sysUser = userService.saveAndFlush(user);
        Optional<SysSetting> sysSetting = sysSettingRepository.findBySettingKey("generalUser");
        sysSetting.ifPresent(o -> roleService.postRoleJoin(o.getSettingValue(), Lists.newArrayList(sysUser.getId().toString())));
        return ResponseEntity.ok(ResponseResult.success(""));
    }


    @SysLog(value = "用户登录")
    @SneakyThrows
    @PostMapping("/login/password")
    public ResponseEntity password(HttpServletRequest request, HttpServletResponse response, @RequestBody @NotNull @Valid UserLoginDTO dto) {
        request.getLocale().getLanguage();
        SysUser userDetails = userService.loadUserByUsername(dto.getUsername());
        if (ObjectUtils.anyNull(userDetails)) {
            throw new LionSaidException("用户名或密码错误", 4000001, request.getLocale());
        }
        if (passwordEncoder.matches(dto.getPassword(), userDetails.getPassword()) || (StringUtils.equalsIgnoreCase(userDetails.getVerificationCode(), dto.getPassword()) && LocalDateTime.now().isBefore(userDetails.getVerificationCodeExpiryDate()))) {
            HashSet<@Nullable String> authorities = userService.getUserAuthorities(userDetails.getId());
            authorities.add(CommonVariables.loginUser);
            userDetails.setAuthorities(String.join(",", authorities));
            Base64.Encoder encoder = Base64.getEncoder();
            String prefix = "USER" + userDetails.getId() + "AUTH";
            String token = encoder.encodeToString((prefix + LionSaidIdGenerator.snowflakeId()).getBytes(StandardCharsets.UTF_8));
            Authentication authenticationRequest = UsernamePasswordAuthenticationToken.authenticated(userDetails.getId(), dto.getUsername(), userDetails.getAuthorities());
            request.setAttribute("Authorization", token);
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authenticationRequest);
            securityRepository.saveContext(securityContext, request, response);
            log.error("authenticationResponse {}", authenticationRequest);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", token);
            jsonObject.put("authorities", authorities);
            jsonObject.put("userDetail", userDetails);
            return ResponseEntity.ok(ResponseResult.success(jsonObject));
        } else {
            throw new LionSaidException("用户名或密码|验证码 错误", 4000001, request.getLocale());
        }
    }

    @SysLog(value = "登出")
    @SneakyThrows
    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        securityRepository.deleteContext(request);
        return ResponseEntity.ok(ResponseResult.success("登出成功"));

    }

    @SysLog(value = "保存用户登录设备信息")
    @SneakyThrows
    @PostMapping("/saveUserDeviceInfo")
    @Operation(description = "新增用户设备信息", summary = "新增用户设备信息")
    public ResponseEntity saveUserDeviceInfo(@RequestAttribute String userId, HttpServletRequest request) {
        String jsonString = IOUtils.toString(request.getInputStream());
        if (StringUtils.isNotEmpty(jsonString)) {
            JSONObject parseObject = JSONObject.parseObject(jsonString);
            SysUserDeviceInfo userDeviceInfo = SysUserDeviceInfo.builder()
                    .id(userId + parseObject.getString("deviceId"))
                    .deviceInfo(parseObject.getString("deviceInfo"))
                    .targetPlatform(parseObject.getString("targetPlatform"))
                    .deviceId(parseObject.getString("deviceId")).build();
            userService.saveUserDeviceInfo(userDeviceInfo);
        }
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    @SysLog(value = "获取用户菜单")
    @SneakyThrows
    @GetMapping("/getUserMenu")
    @Operation(description = "获取用户菜单", summary = "获取用户菜单")
    public ResponseEntity<ResponseResult> getUserMenu(@RequestAttribute String authorities) {
        log.debug("getUserMenu {}", authorities);
        return ResponseEntity.ok(ResponseResult.success(menuService.getUserMenu(Arrays.stream(authorities.split(",")).toList())));
    }

}
