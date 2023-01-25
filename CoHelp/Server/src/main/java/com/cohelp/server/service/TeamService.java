package com.cohelp.server.service;

import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Team;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
