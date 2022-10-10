package com.cohelp.server.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jianping5
 * @create 2022/10/10 20:29
 */
@Data
@AllArgsConstructor
public class RegisterRequest implements Serializable {

    private String userAccount;

    private String userPassword;

    private String userConfirmPassword;

    private static final long serialVersionUID = 1L;

}
