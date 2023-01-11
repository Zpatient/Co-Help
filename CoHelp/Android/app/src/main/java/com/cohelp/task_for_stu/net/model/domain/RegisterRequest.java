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

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserConfirmPassword() {
        return userConfirmPassword;
    }

    public void setUserConfirmPassword(String userConfirmPassword) {
        this.userConfirmPassword = userConfirmPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getConfirmCode() {
        return confirmCode;
    }

    public void setConfirmCode(String confirmCode) {
        this.confirmCode = confirmCode;
    }

    private static final long serialVersionUID = 1L;

}
