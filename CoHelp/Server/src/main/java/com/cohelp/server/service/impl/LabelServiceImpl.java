package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.entity.Label;
import com.cohelp.server.service.LabelService;
import com.cohelp.server.mapper.LabelMapper;
import org.springframework.stereotype.Service;

/**
* @author jianping5
* @description 针对表【label(标签表)】的数据库操作Service实现
* @createDate 2022-09-19 21:36:27
*/
@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label>
    implements LabelService {

}




