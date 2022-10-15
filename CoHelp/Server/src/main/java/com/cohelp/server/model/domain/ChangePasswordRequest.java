package com.cohelp.server.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zgy
 * @create 2022-10-15 19:53
 */
@Data
@AllArgsConstructor
public class ChangePasswordRequest implements Serializable {
    //需要找回密码的用户账号
    private String userAccount;
    //验证码
    private String confirmCode;
    //新密码
    private String newPassword;
    //确认密码
    private String confirmNewPassword;
    private static final long serialVersionUID = 1L;
}
