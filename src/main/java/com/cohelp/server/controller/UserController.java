package com.cohelp.server.controller;

import com.cohelp.server.model.domain.*;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.model.vo.DetailResponse;
import com.cohelp.server.service.UserService;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.UserHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

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

    @GetMapping("/sendconfirmcode")
    public Result<User> sendConfirmCode(@RequestParam String userEmail, HttpServletRequest request) {
        return userService.sendConfirmCode(userEmail, request);
    }

    @PostMapping("/changepassword")
    public Result<User> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, HttpServletRequest request) {
        return userService.userChangePassword(changePasswordRequest, request);
    }
    @Deprecated
    @GetMapping("/getuseremail")
    public Result<User> getUserEmail(@RequestParam String userAccount){
        return userService.getUserEmail(userAccount);
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

    @PostMapping("/deletepub")
    public Result<Boolean> deletePublish(@RequestBody PublishDeleteRequest publishDeleteRequest) {
        return userService.deletePublish(publishDeleteRequest);
    }

    @GetMapping("/searchpub")
    public Result<List<DetailResponse>> searchPublish() {
        return userService.searchPublish();
    }

    @PostMapping("/logout")
    public Result<Boolean> userLogout(HttpServletRequest request) {
        return userService.userLogout(request);
    }

    @PostMapping("/changeAvatar")
    public Result<Boolean> changeAvatar(@RequestParam(name = "file") MultipartFile file) {
        return userService.changeAvatar(file);
    }

    @GetMapping("/listteamuser")
    public Result<PageResponse<User>> listTeamUser(@RequestParam Integer page,@RequestParam Integer limit){
        User user = UserHolder.getUser();
        if(user==null){
            return ResultUtil.fail("未登录！");
        }
        PageResponse<User> users = userService.listTeamUser(user.getTeamId(),page,limit);
        return ResultUtil.ok(users);
    }
    @PostMapping("/adminuserinfo")
    public Result adminUserInfo(@RequestBody User user){
        User currentUser = UserHolder.getUser();
        if(currentUser==null||(!currentUser.getUserRole().equals(1)&&!currentUser.getUserRole().equals(2))){
            return ResultUtil.fail("未登录或权限不足！");
        }
        String result = userService.adminUserInfo(user);
        return ResultUtil.ok(result);
    }
    @GetMapping("/listuserbyname")
    public Result<PageResponse<User>> listUserByName(@RequestParam Integer page,@RequestParam Integer limit,@RequestParam String searchObj){
        User user = UserHolder.getUser();
        if(user==null){
            return ResultUtil.fail("未登录！");
        }
        PageResponse<User> users = userService.listUserByName(user.getTeamId(),page,limit,searchObj);
        return ResultUtil.ok(users);
    }

    @GetMapping("/semester")
    public Result<Set<String>> listSemester() {
        return userService.listSemester();
    }

}
