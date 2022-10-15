package com.cohelp.server.controller;

import com.cohelp.server.model.domain.LoginRequest;
import com.cohelp.server.model.domain.RegisterRequest;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 登录控制器
 *
 * @author jianping5
 * @create 2022/10/13 18:13
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Result<Integer> userRegister(@RequestBody RegisterRequest registerRequest) {
        return userService.userRegister(registerRequest);
    }

    @PostMapping("/login")
    public Result<User> userLogin(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        return userService.userLogin(loginRequest, request);
    }

}
