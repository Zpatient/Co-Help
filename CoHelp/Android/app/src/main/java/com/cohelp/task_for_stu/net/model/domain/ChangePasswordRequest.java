package com.cohelp.task_for_stu.net.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zgy
 * @create 2022-10-15 19:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest implements Serializable {
    /**
     * 需要找回密码的用户邮箱
     */
    private String userEmail;
    /**
     * 验证码
     */
    private String confirmCode;
    /**
     * 新密码
     */
    private String newPassword;
    /**
     * 确认新密码
     */
    private String confirmNewPassword;
    private static final long serialVersionUID = 1L;
}
