package com.cohelp.server.controller;

import com.cohelp.server.constant.StatusCode;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.model.entity.UserTeam;
import com.cohelp.server.service.UserTeamService;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.UserHolder;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zgy
 * @create 2023-02-02 1:08
 */
@RestController
@RequestMapping("/userteam")
public class UserTeamController {
    @Resource
    UserTeamService userTeamService;
    @GetMapping("/listuserteam")
    public Result<List<UserTeam>> listUserTeam(@RequestParam Integer page, @RequestParam Integer limit){
        if(ObjectUtils.anyNull(page,limit)){
            return ResultUtil.fail("参数不能为空！");
        }
        User currentUser = UserHolder.getUser();
        if(currentUser==null||(!currentUser.getUserRole().equals(1)&&!currentUser.getUserRole().equals(2))){
            return ResultUtil.fail("未登录或权限不足！");
        }
        List<UserTeam> userTeams = userTeamService.listUserTeam(currentUser.getTeamId(), page, limit);
        if(userTeams!=null){
            return ResultUtil.ok(StatusCode.SUCCESS_GET_DATA,userTeams,"数据获取成功");
        }else {
            return ResultUtil.fail("数据获取失败！");
        }
    }
    @PostMapping("/changeuserteam")
    public Result changeUserTeam(@RequestBody UserTeam userTeam){
        if(ObjectUtils.anyNull(userTeam)){
            return ResultUtil.fail("参数不能为空！");
        }
        User currentUser = UserHolder.getUser();
        if(currentUser==null||(!currentUser.getUserRole().equals(1)&&!currentUser.getUserRole().equals(2))){
            return ResultUtil.fail("未登录或权限不足！");
        }
        String result = userTeamService.changeUserTeam(userTeam);
        return ResultUtil.ok(result);
    }
}
