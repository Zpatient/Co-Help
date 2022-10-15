package com.cohelp.server.mapper;

import com.cohelp.server.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author jianping5
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2022-09-19 21:36:51
* @Entity com.cohelp.server.model.entity.User
*/
public interface UserMapper extends BaseMapper<User> {
    String getEmailByUserAccount (String userAccount);

}




