package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.model.entity.Ask;
import com.cohelp.server.service.AskService;
import com.cohelp.server.mapper.AskMapper;
import org.springframework.stereotype.Service;

/**
* @author jianping5
* @description 针对表【ask】的数据库操作Service实现
* @createDate 2023-03-01 14:31:07
*/
@Service
public class AskServiceImpl extends ServiceImpl<AskMapper, Ask>
    implements AskService{

}




