package com.cohelp.server.service;

import com.cohelp.server.model.domain.LoginRequest;
import com.cohelp.server.model.domain.RegisterRequest;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
* @author jianping5
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2022-09-19 21:36:51
*/
public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param loginRequest
     * @param request
     * @return
     */
    Result userLogin(LoginRequest loginRequest, HttpServletRequest request);

    /**
     * 用户注册
     * @param registerRequest
     * @return
     */
    Result userRegister(RegisterRequest registerRequest);
}
