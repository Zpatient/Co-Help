package com.cohelp.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cohelp.server.model.entity.UserTeam;

import java.util.List;

/**
* @author 县城之子丶
* @description 针对表【user_team(用户组织表)】的数据库操作Service
* @createDate 2023-01-31 19:48:51
*/
public interface UserTeamService extends IService<UserTeam> {
    /**
     * 获取某组织待处理的加入申请
     * @param teamName 组织名称
     * @return java.util.List<com.cohelp.server.model.entity.UserTeam>
     */
    List<UserTeam> listUserTeam(Integer teamId,Integer currentPage,Integer pageSize);
    /**
     * 更新指定加入组织申请
     * @param userTeam 已处理的userTeam
     * @return java.lang.String
     */
    String changeUserTeam(UserTeam userTeam);
}
