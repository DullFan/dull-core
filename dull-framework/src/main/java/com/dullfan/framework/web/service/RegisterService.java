package com.dullfan.framework.web.service;

import com.dullfan.common.constant.UserConstants;
import com.dullfan.common.entity.po.RegisterBody;
import com.dullfan.common.entity.po.SysUser;
import com.dullfan.common.utils.SecurityUtils;
import com.dullfan.common.utils.StringUtils;
import com.dullfan.system.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * 注册校验方法
 *
 * @author ruoyi
 */
@Component
public class RegisterService {
    @Resource
    private UserService userService;

    /**
     * 注册
     */
    public String register(RegisterBody registerBody) {
        String msg = "";
        String username = registerBody.getUsername(), password = registerBody.getPassword();
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);

        if (StringUtils.isEmpty(username)) {
            msg = "用户名不能为空";
        } else if (StringUtils.isEmpty(password)) {
            msg = "用户密码不能为空";
        } else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            msg = "账户长度必须在2到20个字符之间";
        } else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            msg = "密码长度必须在5到20个字符之间";
        } else if (!userService.checkUserNameUnique(sysUser)) {
            msg = "保存用户'" + username + "'失败，注册账号已存在";
        } else {
            sysUser.setNickName(username);
            sysUser.setPassword(SecurityUtils.encryptPassword(password));
            sysUser.setUserName(registerBody.getUsername());
            Integer add = userService.add(sysUser);
            if (add <= 0) {
                msg = "注册失败,请联系系统管理人员";
            }
        }

        return msg;
    }
}
