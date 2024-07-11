package com.dullfan.framework.web.service;

import com.dullfan.common.entity.po.LoginUser;
import com.dullfan.common.entity.po.User;
import com.dullfan.common.enums.UserStatusEnum;
import com.dullfan.common.exception.ServiceException;
import com.dullfan.common.utils.StringTools;
import com.dullfan.system.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;

    @Resource
    private PasswordService passwordService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        user = userService.selectUserByUserName(username);

        if (StringTools.isNull(user)) {
            throw new ServiceException("用户不存在或密码错误");
        } else if (UserStatusEnum.DELETED.getCode().equals(user.getDelFlag())) {
            throw new ServiceException("用户已被删除");
        } else if (UserStatusEnum.DISABLE.getCode().equals(user.getStatus())) {
            throw new ServiceException("用户已被禁用");
        }

        passwordService.validate(user);
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(User user) {
        return new LoginUser(user.getUserId(), user);
    }
}
