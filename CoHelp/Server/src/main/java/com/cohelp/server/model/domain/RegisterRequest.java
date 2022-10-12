package com.cohelp.server.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 注册请求体
 *
 * @author jianping5
 * @create 2022/10/10 20:29
 */
@Data
@AllArgsConstructor
public class RegisterRequest implements Serializable {

    private String userAccount;

    private String userPassword;

    private String userConfirmPassword;

    /**
     * 手机号
     */
    private String phoneNumber;

    private static final long serialVersionUID = 1L;

}
