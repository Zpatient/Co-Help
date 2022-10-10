package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.service.UserService;
import com.cohelp.server.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author jianping5
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2022-09-19 21:36:51
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




