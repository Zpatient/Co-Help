package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.mapper.TeamMapper;
import com.cohelp.server.model.PageResponse;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Team;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.model.entity.UserTeam;
import com.cohelp.server.service.TeamService;
import com.cohelp.server.service.UserService;
import com.cohelp.server.service.UserTeamService;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.UserHolder;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author jianping5
* @description 针对表【team(组织表)】的数据库操作Service实现
* @createDate 2023-01-22 17:54:40
*/
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
    implements TeamService{

    @Resource
    private TeamService teamService;

    @Resource
    private UserService userService;

    @Resource
    private UserTeamService userTeamService;

    @Override
    public Result<List<Team>> fuzzyQuery(String teamName) {

        // 模糊查询所有团体
        QueryWrapper<Team> teamQueryWrapper = new QueryWrapper<>();
        teamQueryWrapper.like("team_name", teamName);
        List<Team> teamList = teamService.list(teamQueryWrapper);

        return ResultUtil.ok(teamList);
    }

    @Override
    public Result<Boolean> changeTeam(Integer conditionType, Integer teamId) {

        // 参数校验
        if (conditionType != 0 && conditionType != 1 && teamId <= 0) {
            return ResultUtil.fail(false, "参数错误");
        }

        // 获取当前登录用户 id
        User user = UserHolder.getUser();
        Integer userId = user.getId();

        // 构造器
        UpdateWrapper<User> teamUpdateWrapper = new UpdateWrapper<>();

        // 加入组织
        if (conditionType == 0) {
            // 判断组织是否存在
            QueryWrapper<Team> teamQueryWrapper = new QueryWrapper<>();
            teamQueryWrapper.eq("id", teamId);
            Team team = teamService.getOne(teamQueryWrapper);
            if (team == null) {
                return ResultUtil.fail(false, "抱歉！该组织不存在");
            }

            // 提交加入申请
            QueryWrapper<UserTeam> userTeamQueryWrapper = new QueryWrapper<>();
            userTeamQueryWrapper.eq("user_id",userId)
                    .eq("join_state",0);
            UserTeam userTeam = userTeamService.getOne(userTeamQueryWrapper,false);
            if(userTeam==null){
                userTeam = new UserTeam();
            }
            userTeam.setUserId(userId);
            userTeam.setTargetTeamId(teamId);
            userTeam.setJoinState(0);
            boolean update = userTeamService.saveOrUpdate(userTeam);
            if (!update) {
                return ResultUtil.fail(false, "申请失败");
            }
        }

        // 退出组织
        if (conditionType == 1) {
            // 修改用户的组织
            teamUpdateWrapper.eq("id", userId);
            teamUpdateWrapper.set("team_id", 1);
            boolean update = userService.update(teamUpdateWrapper);
            if (!update) {
                return ResultUtil.fail(false, "退出组织失败");
            }
        }

        return ResultUtil.ok( true,"成功修改组织");
    }

    @Override
    public String insertTeam(Team team) {
        if(team==null){
            return null;
        }
        // 判断组织是否存在
        QueryWrapper<Team> teamQueryWrapper = new QueryWrapper<>();
        teamQueryWrapper.eq("team_name", team.getTeamName());
        Team oldTeam = teamService.getOne(teamQueryWrapper);
        if (oldTeam != null) {
            return "组织名已存在，换个名字试试！";
        }
        team.setCreateState(0);
        boolean b = teamService.saveOrUpdate(team);
        if(b){
            return "组织创建申请提交成功！";
        }
        return "组织创建申请提交失败！";
    }

    @Override
    public PageResponse<Team> listNotApproved(Integer currentPage, Integer pageSize) {
        if(ObjectUtils.anyNull(currentPage,pageSize)){
            return null;
        }
        //分页查询数据
        Page<Team> teamPage = getBaseMapper().selectPage(new Page<>(currentPage, pageSize),
                new QueryWrapper<Team>().eq("create_state",0));
        List<Team> records = teamPage.getRecords();
        for(Team team:records){
            if(team!=null){
                Integer userId = team.getTeamCreator();
                User user = userService.getById(userId);
                if(user!=null){
                    team.setCreatorName(user.getUserName());
                }else {
                    team.setCreatorName("用户不存在！");
                }
            }
        }
        PageResponse<Team> teamPageResponse = new PageResponse<>(records, teamPage.getTotal());
        return teamPageResponse;
    }

    @Override
    public String adminTeam(Team team) {
        if(team==null||team.getId()==null) {
            return "参数为空！";
        }
        // 判断申请是否存在
        QueryWrapper<Team> teamQueryWrapper = new QueryWrapper<>();
        teamQueryWrapper.eq("id", team.getId()).eq("create_state",0);
        Team one = getOne(teamQueryWrapper);
        if (one == null) {
            return "抱歉，申请不存在！";
        }
        //更新用户信息
        if(team.getCreateState()!=null&&team.getCreateState().equals(1)){
            Integer userId=one.getTeamCreator();
            User user = userService.getById(userId);
            if(user==null){
                return "申请人不存在！";
            }
            user.setTeamId(one.getId());
            user.setUserRole(1);
            boolean update = userService.saveOrUpdate(user);
            if(!update){
                return "申请人权限修改失败!";
            }
        }
        //更新申请审批状态
        boolean b = this.updateById(team);
        if (b == false) {
            return "组织信息更新失败!";
        }else {
            return "组织信息更新成功！";
        }
    }

}




