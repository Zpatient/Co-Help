package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.model.entity.QuestionBank;
import com.cohelp.server.service.QuestionBankService;
import com.cohelp.server.mapper.QuestionBankMapper;
import org.springframework.stereotype.Service;

/**
* @author jianping5
* @description 针对表【question_bank】的数据库操作Service实现
* @createDate 2023-03-01 14:31:07
*/
@Service
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankMapper, QuestionBank>
    implements QuestionBankService{

}




