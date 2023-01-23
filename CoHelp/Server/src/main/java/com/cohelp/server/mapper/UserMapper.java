package com.cohelp.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cohelp.server.model.entity.User;

/**
* @author 县城之子丶
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2023-01-24 04:11:57
* @Entity com.cohelp.server.model.entity.User
*/
public interface UserMapper extends BaseMapper<User> {
    String getEmailByUserAccount (String userAccount);
}




