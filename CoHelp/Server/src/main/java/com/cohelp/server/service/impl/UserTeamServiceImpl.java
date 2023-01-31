package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.mapper.UserTeamMapper;
import com.cohelp.server.model.entity.UserTeam;
import com.cohelp.server.service.UserTeamService;
import org.springframework.stereotype.Service;

/**
* @author 县城之子丶
* @description 针对表【user_team(用户组织表)】的数据库操作Service实现
* @createDate 2023-01-31 19:48:51
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService{

}




