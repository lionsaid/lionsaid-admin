package com.lionsaid.admin.web.service;

import com.lionsaid.admin.web.common.IService;
import com.lionsaid.admin.web.model.po.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends IService<User, Long>, UserDetailsService {
    User loadUserByUsername(String username) throws UsernameNotFoundException;

}
