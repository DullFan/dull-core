package com.dullfan.system.entity.po;

import lombok.Data;

@Data
public class ResetPassword {
    /**
     * 旧密码
     */
    private String oldPassword;
    /**
     * 新密码
     */
    private String newPassword;
}
