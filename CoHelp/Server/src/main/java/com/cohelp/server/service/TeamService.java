package com.cohelp.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cohelp.server.model.PageResponse;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Team;

import java.util.List;

/**
* @author jianping5
* @description 针对表【team(组织表)】的数据库操作Service
* @createDate 2023-01-22 17:54:40
*/
public interface TeamService extends IService<Team> {

    /**
     * 根据输入的关键词进行模糊查询
     * @param teamName
     * @return
     */
    Result<List<Team>> fuzzyQuery(String teamName);

    /**
     * 更改团体
     * @param conditionType
     * @param teamId
     * @return
     */
    Result<Boolean> changeTeam(Integer conditionType, Integer teamId);
    /**
     * 创建新组织
     * @param team 待插入的组织
     * @return java.lang.Boolean
     */
    String insertTeam(Team team);

    /**
     * 获取某待审批的组织创建申请
     * @param teamName 组织名称
     * @return java.util.List<com.cohelp.server.model.entity.UserTeam>
     */
    PageResponse<Team> listNotApproved(Integer currentPage, Integer pageSize);
    /**
     * 更新指定创建组织申请
     * @param team 已处理的team
     * @return java.lang.String
     */
    String adminTeam(Team team);
}
