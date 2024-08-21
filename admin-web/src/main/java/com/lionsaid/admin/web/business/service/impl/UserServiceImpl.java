package com.lionsaid.admin.web.business.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lionsaid.admin.web.business.model.po.SysUser;
import com.lionsaid.admin.web.business.model.po.SysUserDeviceInfo;
import com.lionsaid.admin.web.business.repository.*;
import com.lionsaid.admin.web.business.service.UserService;
import com.lionsaid.admin.web.common.IServiceImpl;
import com.lionsaid.admin.web.enums.CommonVariables;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl extends IServiceImpl<SysUser, String, UserRepository> implements UserService {

    private UserRepository userRepository;
    private SysRoleJoinRepository sysRoleJoinRepository;
    private SysOrganizationJoinRepository sysOrganizationJoinRepository;
    private SysMenuJoinRepository sysMenuJoinRepository;
    private SysAuthoritiesJoinRepository sysAuthoritiesJoinRepository;
    private SysUserDeviceInfoRepository userDeviceInfoRepository;

    @Override
    public SysUser loadUserByUsername(String username) throws RuntimeException {
        //根据用户名查询用户信息
        Optional<SysUser> optionalUser = userRepository.findByUsername(username);
        //如果查询不到数据就通过抛出异常来给出提示
        if (optionalUser.isEmpty()) {
            return null;
        }
        return optionalUser.get();
    }
    @Override
    public HashSet<@Nullable String> getUserAuthorities(String userId) {
        List<String> joinList = Lists.newArrayList();
        joinList.add(userId);
        HashSet<@Nullable String> authoritiesSet = Sets.newHashSet();
        sysOrganizationJoinRepository.findByJoinId(joinList).forEach(o -> joinList.add(o.getId()));
        sysRoleJoinRepository.findByJoinId(joinList).forEach(o -> joinList.add(o.getId()));
        sysMenuJoinRepository.findByJoinId(joinList).forEach(o -> joinList.add(o.getId()));
        sysAuthoritiesJoinRepository.findByJoinId(joinList).forEach(o -> authoritiesSet.add(o.getAuthorities()));
        authoritiesSet.addAll(joinList);
        return authoritiesSet;
    }


    @Override
    public SysUser saveAndFlush(SysUser sysUser) {
        sysUser.setId(getUserId());
        return repository.saveAndFlush(sysUser);
    }
    @Override
    public void updateVerificationCodeByEmail(String verificationCode, String email){
        repository.updateVerificationCodeAndVerificationCodeExpiryDateByUsername(verificationCode, LocalDateTime.now().plusHours(2L),email);
    }

    @Override
    public void saveUserDeviceInfo(SysUserDeviceInfo userDeviceInfo) {
        userDeviceInfoRepository.saveAndFlush(userDeviceInfo);
    }


    private String getUserId() {
        String id = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(6, 14)).toUpperCase(Locale.ROOT);
        if (repository.existsById(id)) {
            return getUserId();
        } else {
            return id;
        }
    }

}