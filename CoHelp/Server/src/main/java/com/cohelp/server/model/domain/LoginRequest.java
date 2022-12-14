package com.cohelp.server.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录请求体
 *
 * @author jianping5
 * @create 2022/10/10 20:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements Serializable {

    private String userAccount;

    private String userPassword;

    private static final long serialVersionUID = 1L;

}
