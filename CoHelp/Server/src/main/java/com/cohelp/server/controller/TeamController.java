package com.cohelp.server.controller;

import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.domain.TeamUpdateRequest;
import com.cohelp.server.model.entity.Team;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.service.TeamService;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jianping5
 * @createDate 22/1/2023 下午 5:56
 */
@RestController
@RequestMapping("/team")
public class TeamController {

    @Resource
    private TeamService teamService;

    @GetMapping("/query")
    public Result<List<Team>> fuzzyQuery(@RequestParam String teamName) {
        if (StringUtils.isBlank(teamName)) {
            return ResultUtil.fail("输入为空");
        }
        return teamService.fuzzyQuery(teamName);
    }

    @PostMapping("/change")
    public Result<Boolean> changeTeam(@RequestBody TeamUpdateRequest teamUpdateRequest) {
        if (teamUpdateRequest == null) {
            return ResultUtil.fail("参数错误");
        }

        // 获取参数
        Integer conditionType = teamUpdateRequest.getConditionType();
        Integer teamId = teamUpdateRequest.getTeamId();

        return teamService.changeTeam(conditionType, teamId);

    }

    @RequestMapping("/{id}")
    public Result<Team> getTeamById(@PathVariable Integer id) {
        if (id <= 0) {
            return ResultUtil.fail("参数错误");
        }
        return ResultUtil.ok(teamService.getById(id));
    }
    @GetMapping("/insertteam")
    public Result insertTeam(@RequestParam String teamName){
        if(teamName==null||teamName.equals("")){
            return ResultUtil.fail("组织名不能为空！");
        }
        User user = UserHolder.getUser();
        if(user==null){
            return ResultUtil.fail("未登录！");
        }
        Team team = new Team();
        team.setTeamName(teamName);
        team.setTeamCreator(user.getId());
        String s = teamService.insertTeam(team);
        return ResultUtil.ok(s);
    }


}
