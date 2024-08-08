package com.lionsaid.admin.web.business.service;

import com.lionsaid.admin.web.business.model.po.SysUser;
import com.lionsaid.admin.web.business.model.po.SysUserDeviceInfo;
import com.lionsaid.admin.web.common.IService;
import com.lionsaid.admin.web.exception.LionSaidException;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;

public interface UserService extends IService<SysUser, String>, UserDetailsService {
    SysUser loadUserByUsername(String username) throws RuntimeException;

    HashSet<@Nullable String> getUserAuthorities(String userId);

    void updateVerificationCodeByEmail(String emailVerify, String email);

    void saveUserDeviceInfo(SysUserDeviceInfo userDeviceInfo);
}
