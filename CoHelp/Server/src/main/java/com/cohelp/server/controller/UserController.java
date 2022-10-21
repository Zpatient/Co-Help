package com.cohelp.server.controller;

import com.cohelp.server.model.domain.ChangePasswordRequest;
import com.cohelp.server.model.domain.LoginRequest;
import com.cohelp.server.model.domain.RegisterRequest;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.service.UserService;
import org.omg.CORBA.INTERNAL;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.beans.IntrospectionException;

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
    public Result<Integer> userRegister(@RequestBody RegisterRequest registerRequest, HttpServletRequest request) {
        return userService.userRegister(registerRequest, request);
    }

    @PostMapping("/login")
    public Result<User> userLogin(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        return userService.userLogin(loginRequest, request);
    }

    @PostMapping("/changepassword")
    public Result<User> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, HttpServletRequest request) {
        return userService.userChangePassword(changePasswordRequest, request);
    }

    @GetMapping("/getuseremail")
    public Result<User> getUserEmail(@RequestParam String userAccount){
        return userService.getUserEmail(userAccount);
    }

    @GetMapping("/sendconfirmcode")
    public Result<User> sendConfirmCode(@RequestParam String userEmail, HttpServletRequest request) {
        return userService.sendConfirmCode(userEmail, request);
    }

    @GetMapping("/current")
    public Result<User> getCurrentUser() {
        return userService.getCurrentUser();
    }

    @GetMapping("/viewpage/{userAccount}")
    public Result<User> viewPage(@PathVariable("userAccount") String userAccount) {
        return userService.viewPage(userAccount);
    }

    @PostMapping("/changeuserinfo")
    public Result<Boolean> changeUserInfo(@RequestBody User user) {
        return userService.changeUserInfo(user);
    }

    @PostMapping("/logout")
    public Result<Boolean> userLogout(HttpServletRequest request) {
        return userService.userLogout(request);
    }
}
