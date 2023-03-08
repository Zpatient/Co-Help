package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.mapper.AnswerMapper;
import com.cohelp.server.model.entity.Answer;
import com.cohelp.server.service.AnswerService;
import org.springframework.stereotype.Service;

/**
* @author jianping5
* @description 针对表【answer】的数据库操作Service实现
* @createDate 2023-03-01 14:31:07
*/
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer>
    implements AnswerService{

}




