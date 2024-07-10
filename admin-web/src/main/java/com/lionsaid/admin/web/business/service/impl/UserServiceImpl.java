package com.lionsaid.admin.web.business.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lionsaid.admin.web.business.model.po.SysUser;
import com.lionsaid.admin.web.business.repository.*;
import com.lionsaid.admin.web.business.service.UserService;
import com.lionsaid.admin.web.common.IServiceImpl;
import com.lionsaid.admin.web.exception.LionSaidException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl extends IServiceImpl<SysUser, Long, UserRepository> implements UserService {

    private UserRepository userRepository;
    private SysRoleJoinRepository sysRoleJoinRepository;
    private SysOrganizationJoinRepository sysOrganizationJoinRepository;
    private SysMenuJoinRepository sysMenuJoinRepository;
    private SysAuthoritiesJoinRepository sysAuthoritiesJoinRepository;

    @Override
    public SysUser loadUserByUsername(String username) throws RuntimeException {
        //根据用户名查询用户信息
        Optional<SysUser> optionalUser = userRepository.findByUsername(username);
        //如果查询不到数据就通过抛出异常来给出提示
        if (optionalUser.isEmpty()) {
            return null;
        }
        SysUser sysUser = optionalUser.get();

        List<String> joinList = Lists.newArrayList();
        joinList.add(sysUser.getId().toString());
        HashSet<@Nullable String> authoritiesSet = Sets.newHashSet();
        sysOrganizationJoinRepository.findByJoinId(joinList).forEach(o -> joinList.add(o.getId()));
        sysRoleJoinRepository.findByJoinId(joinList).forEach(o -> joinList.add(o.getId()));
        sysMenuJoinRepository.findByJoinId(joinList).forEach(o -> joinList.add(o.getId()));
        sysAuthoritiesJoinRepository.findByJoinId(joinList).forEach(o -> authoritiesSet.add(o.getAuthorities()));
        sysUser.setAuthorities(String.join(",", authoritiesSet.stream().toList()));
        return sysUser;
    }
}