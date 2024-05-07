package com.lionsaid.admin.web.business.service;

import com.lionsaid.admin.web.common.IService;
import com.lionsaid.admin.web.business.model.po.SysUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends IService<SysUser, Long>, UserDetailsService {
    SysUser loadUserByUsername(String username) throws UsernameNotFoundException;

}
