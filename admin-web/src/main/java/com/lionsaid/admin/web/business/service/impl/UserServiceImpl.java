package com.lionsaid.admin.web.business.service.impl;

import com.lionsaid.admin.web.common.IServiceImpl;
import com.lionsaid.admin.web.exception.LionSaidException;
import com.lionsaid.admin.web.business.model.po.SysUser;
import com.lionsaid.admin.web.business.repository.UserRepository;
import com.lionsaid.admin.web.business.service.UserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl extends IServiceImpl<SysUser, Long, UserRepository> implements UserService {

    private UserRepository userRepository;

    @Override
    @SneakyThrows
    public SysUser loadUserByUsername(String username) {
        //根据用户名查询用户信息
        Optional<SysUser> optionalUser = userRepository.findByUsername(username);
        //如果查询不到数据就通过抛出异常来给出提示
        if (optionalUser.isEmpty()) {
            throw new LionSaidException("用户名或密码错误", 4000001);
        }
        return optionalUser.get();
    }
}