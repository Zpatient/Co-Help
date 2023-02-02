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
     * @param teamId
     * @return
     */
    List<Activity> listByHot(Integer teamId);

    /**
     * 根据关键词搜索某人发布的活动
     * @param userId 用户Id
     * @param key 关键词
     * @param keywords 分词结果
     * @return java.util.List<com.cohelp.server.model.entity.Activity>
     */
    List<Activity> search(Integer userId,String key,String[] keywords);

    /**
     * 查询用户当天发布的活动数
     * @param userId 用户Id
     * @return long
     */
    long getCurrentDayPublish(Integer userId);
    /**
     * 查询用户本年度某月发布的活动数
     * @param userId 用户Id
     * @return long
     */
    long getMonthPublish(Integer userId,Integer month);

}




