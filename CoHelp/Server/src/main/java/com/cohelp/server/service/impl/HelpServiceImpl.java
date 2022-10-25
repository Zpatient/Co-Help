package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.model.entity.Help;
import com.cohelp.server.service.HelpService;
import com.cohelp.server.mapper.HelpMapper;
import org.springframework.stereotype.Service;

/**
* @author 县城之子丶
* @description 针对表【help(互助表)】的数据库操作Service实现
* @createDate 2022-10-25 12:09:43
*/
@Service
public class HelpServiceImpl extends ServiceImpl<HelpMapper, Help>
    implements HelpService{

}




