package com.cohelp.server.controller;

import com.cohelp.server.model.domain.ChangePasswordRequest;
import com.cohelp.server.model.domain.LoginRequest;
import com.cohelp.server.model.domain.RegisterRequest;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息控制器
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

    @PostMapping("/changepassword")
    public Result<User> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, HttpServletRequest request) {
        return userService.userChangePassword(changePasswordRequest, request);
    }
    @PostMapping("/getuseremail")
    public Result<User> getUserEmail(@RequestParam String userAccount){
        return userService.getUserEmail(userAccount);
    }

    @PostMapping("/sendconfirmcode")
    public Result<User> sendConfirmCode(@RequestParam String userEmail, HttpServletRequest request) {
        return userService.sendConfirmCode(userEmail, request);
    }
}
