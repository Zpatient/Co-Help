package com.cohelp.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cohelp.server.model.entity.User;

/**
* @author jianping5
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2023-01-20 21:22:54
* @Entity com.cohelp.server.model.entity.User
*/
public interface UserMapper extends BaseMapper<User> {

    String getEmailByUserAccount (String userAccount);
//    List<User>  listTeamUser(Integer teamId);
}




