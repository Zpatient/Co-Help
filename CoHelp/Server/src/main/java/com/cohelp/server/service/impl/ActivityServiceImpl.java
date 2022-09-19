package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.entity.Activity;
import com.cohelp.server.service.ActivityService;
import com.cohelp.server.mapper.ActivityMapper;
import org.springframework.stereotype.Service;

/**
* @author jianping5
* @description 针对表【activity(活动表)】的数据库操作Service实现
* @createDate 2022-09-19 21:34:53
*/
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity>
    implements ActivityService {

}




