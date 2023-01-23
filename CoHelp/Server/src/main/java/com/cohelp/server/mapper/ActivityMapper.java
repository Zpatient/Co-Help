package com.cohelp.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cohelp.server.model.entity.Activity;

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
     * @param userIdList
     * @return
     */
    List<Activity> listByHot(List<Integer> userIdList);

    List<Activity> search(Integer userId,String key,String[] keywords);

}




