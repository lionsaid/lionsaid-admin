package com.lionsaid.admin.web.service.impl;

import com.lionsaid.admin.web.exception.LionSaidException;
import com.lionsaid.admin.web.model.po.User;
import com.lionsaid.admin.web.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        //根据用户名查询用户信息
        Optional<User> optionalUser = userRepository.findByUsername(username);
        //如果查询不到数据就通过抛出异常来给出提示
        if (optionalUser.isEmpty()) {
            throw new LionSaidException("用户名或密码错误", 4000001);
        }
        return optionalUser.get();
    }
}