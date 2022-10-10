package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.model.entity.History;
import com.cohelp.server.service.HistoryService;
import com.cohelp.server.mapper.HistoryMapper;
import org.springframework.stereotype.Service;

/**
* @author jianping5
* @description 针对表【history(浏览记录表)】的数据库操作Service实现
* @createDate 2022-09-19 21:36:09
*/
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History>
    implements HistoryService {

}




