package com.lionsaid.admin.web.config;

import com.google.common.collect.Lists;
import com.lionsaid.admin.web.business.model.po.SysAuthorities;
import com.lionsaid.admin.web.business.model.po.SysRole;
import com.lionsaid.admin.web.business.model.po.SysSetting;
import com.lionsaid.admin.web.business.model.po.SysUser;
import com.lionsaid.admin.web.business.repository.SysSettingRepository;
import com.lionsaid.admin.web.business.service.AuthoritiesService;
import com.lionsaid.admin.web.business.service.RoleService;
import com.lionsaid.admin.web.business.service.UserService;
import com.lionsaid.admin.web.utils.LionSaidIdGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;

@Component
@AllArgsConstructor
@Slf4j
public class AdminRunConfig {
    private final ApplicationContext applicationContext;
    private final SysSettingRepository sysSettingRepository;
    private final RoleService roleService;
    private final AuthoritiesService authoritiesService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void systemInit() {
        String key = "systemInit";
        if (!sysSettingRepository.existsBySettingKey(key)) {
            LocalDateTime now = LocalDateTime.now();
            sysSettingRepository.save(SysSetting.builder()
                    .id(LionSaidIdGenerator.snowflakeId())
                    .settingKey(key)
                    .settingValue(now.toString())
                    .build());
            System.out.println("初始化菜单信息 开始");
            String userId = initUser();
            scanPreAuthorizeAnnotations(applicationContext, userId);
            System.out.println("初始化菜单信息 结束");
        }


    }


    private String initUser() {
        LocalDateTime now = LocalDateTime.now();
        String username = RandomStringUtils.randomAscii(6);
        String pass = RandomStringUtils.randomAscii(10);
        System.out.println("初始化管理员 username :" + username + ";  password :" + pass);
        SysUser user = SysUser.builder().username(username).build();
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        user.setPassword(passwordEncoder.encode(pass));
        user.setCreatedDate(now);
        user.setLastModifiedDate(now);
        userService.saveAndFlush(user);
        String userId = user.getId().toString();
        SysRole sysRole = roleService.saveAndFlush(SysRole.builder().id(LionSaidIdGenerator.snowflakeId()).name("系统管理员").build());
        roleService.saveAndFlush(SysRole.builder().id(LionSaidIdGenerator.snowflakeId()).name("普通用户").build());
        roleService.postRoleJoin(sysRole.getId(), Lists.newArrayList(userId));
        SysAuthorities sysAuthorities = authoritiesService.saveAndFlush(SysAuthorities.builder().id(LionSaidIdGenerator.snowflakeId()).authorities("administration").name("系统管理员").build());
        authoritiesService.postAuthoritiesJoin(sysAuthorities.getId(), Lists.newArrayList(sysRole.getId()));
        return userId;
    }

    public void scanPreAuthorizeAnnotations(ApplicationContext applicationContext, String userId) {
        LocalDateTime now = LocalDateTime.now();
        // 获取所有带有 @RestController 注解的 Bean
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(RestController.class);
        int sort = 10000;
        ArrayList<@Nullable SysAuthorities> list = Lists.newArrayList();
        Set<String> preAuthorizeValues = new HashSet<>();
        for (Object bean : beans.values()) {
            List<String> list1 = Lists.newArrayList();
            list1.add("administration");
            String groupId = LionSaidIdGenerator.snowflakeId();
            SysAuthorities sysAuthorities = SysAuthorities.builder().id(LionSaidIdGenerator.snowflakeId()).groupId(groupId).sort(sort).build();
            // 获取实际类
            Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
            Tag tag = AnnotationUtils.findAnnotation(targetClass, Tag.class);
            RequestMapping preAuthorize = AnnotationUtils.findAnnotation(targetClass, RequestMapping.class);
            if (preAuthorize != null) {
                list1.add(preAuthorize.name());
                sysAuthorities.setAuthorities(preAuthorize.name());
            }
            if (tag != null) {
                sysAuthorities.setName(tag.name());
                sysAuthorities.setDescription(tag.description());
                list.add(sysAuthorities);
                Method[] methods = targetClass.getMethods();
                for (Method method : methods) {
                    PreAuthorize preAuthorize1 = AnnotationUtils.findAnnotation(method, PreAuthorize.class);
                    Operation operation = AnnotationUtils.findAnnotation(method, Operation.class);
                    if (preAuthorize1 != null) {
                        List<String> preAuthoriz = new ArrayList<>(Arrays.stream(preAuthorize1.value().replaceAll("hasAnyAuthority\\(", "").replaceAll("\\)", "").replaceAll("'", "").split(",")).toList());
                        preAuthoriz.removeAll(list1);
                        for (String preAuthoriz1 : preAuthoriz) {
                            sort++;
                            SysAuthorities sysAuthorities1 = SysAuthorities.builder().id(LionSaidIdGenerator.snowflakeId()).groupId(groupId).sort(sort).build();
                            if (operation != null) {
                                sysAuthorities1.setName(operation.description());
                                sysAuthorities1.setSummary(operation.summary());
                                sysAuthorities1.setDescription(operation.description());
                            }
                            sysAuthorities1.setAuthorities(preAuthoriz1.trim());
                            list.add(sysAuthorities1);
                        }
                    }
                    sort++;
                }
            }
        }
        authoritiesService.saveAllAndFlush(list);
        // 打印所有找到的 PreAuthorize 值
        preAuthorizeValues.forEach(System.out::println);
        System.out.println("初始化菜单信息结束。");
    }
}
