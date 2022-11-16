package com.cohelp.task_for_stu.net.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 注册请求体
 *
 * @author jianping5
 * @create 2022/10/10 20:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest implements Serializable {

    private String userAccount;

    private String userPassword;

    private String userConfirmPassword;

    private String userEmail;

    /**
     * 验证码
     */
    private String confirmCode;

    private static final long serialVersionUID = 1L;

}
