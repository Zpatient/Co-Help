package com.cohelp.server.mapper;

import com.cohelp.server.model.entity.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;
import java.util.List;

/**
* @author jianping5
* @description 针对表【activity(活动表)】的数据库操作Mapper
* @createDate 2022-10-21 21:22:49
* @Entity com.cohelp.server.model.entity.Activity
*/
public interface ActivityMapper extends BaseMapper<Activity> {

    /**
     * 根据热度查询（4*点赞+3*评论+3*收藏）
     * @return
     */
    List<Activity> listByHot();

}




