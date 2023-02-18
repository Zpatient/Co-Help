package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.mapper.UserTeamMapper;
import com.cohelp.server.model.domain.PageResponse;
import com.cohelp.server.model.entity.Team;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.model.entity.UserTeam;
import com.cohelp.server.service.TeamService;
import com.cohelp.server.service.UserService;
import com.cohelp.server.service.UserTeamService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 县城之子丶
* @description 针对表【user_team(用户组织表)】的数据库操作Service实现
* @createDate 2023-01-31 19:48:51
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService{
    @Resource
    UserService userService;
    @Resource
    TeamService teamService;

    @Override
    public PageResponse<UserTeam> listUserTeam(Integer teamId, Integer currentPage, Integer pageSize) {
        if( ObjectUtils.anyNull(teamId,currentPage,pageSize)) return null;
        //分页查询数据
        Page<UserTeam> userTeamPage = getBaseMapper().selectPage(new Page<>(currentPage, pageSize),
                new QueryWrapper<UserTeam>().eq("target_team_id",teamId).eq("join_state",0));
        List<UserTeam> records = userTeamPage.getRecords();
        for(UserTeam userTeam:records){
            if(userTeam!=null){
                Integer userId = userTeam.getUserId();
                Team team = teamService.getById(teamId);
                if(team!=null){
                    userTeam.setTeamName(team.getTeamName());
                }else {
                    userTeam.setTeamName("组织不存在！");
                }
                User user = userService.getById(userId);
                if(user!=null){
                    userTeam.setUserName(user.getUserName());
                }else {
                    userTeam.setUserName("用户不存在！");
                }
            }
        }
        return new PageResponse<UserTeam>(records,userTeamPage.getTotal());
    }

    @Override
    public String changeUserTeam(UserTeam userTeam) {
        if(userTeam==null) {
            return "参数为空！";
        }
        // 判断申请是否存在
        QueryWrapper<UserTeam> userTeamQueryWrapper = new QueryWrapper<>();
        userTeamQueryWrapper.eq("id", userTeam.getId()).eq("join_state",0);
        UserTeam one = getOne(userTeamQueryWrapper);
        if (one == null) {
            return "抱歉，申请不存在！";
        }
        //更新用户信息
        if(userTeam.getJoinState()!=null&&userTeam.getJoinState().equals(1)){
            Integer userId= one.getUserId();
            User user = userService.getById(userId);
            if(user==null){
                return "申请人不存在！";
            }
            user.setTeamId(one.getTargetTeamId());
            boolean update = userService.saveOrUpdate(user);
            if(!update){
                return "申请人组织信息修改失败!";
            }
        }
        //更新申请审批状态
        boolean b = this.updateById(userTeam);
        if (b == false) {
            return "修改失败!";
        }{
            return "修改成功！";
        }
    }

    @Override
    public String getChangeTeam(User user) {
        if(user==null){
            return null;
        }
        Integer id = user.getId();
        QueryWrapper<UserTeam> eq = new QueryWrapper<UserTeam>().eq("user_id", id).eq("join_state", 0);
        UserTeam one = getOne(eq);
        if(one==null){
            return "";
        }else {
            Integer targetTeamId = one.getTargetTeamId();
            Team target = teamService.getById(targetTeamId);
            if(target==null){
                return "";
            }else {
                return target.getTeamName();
            }
        }
    }
}




