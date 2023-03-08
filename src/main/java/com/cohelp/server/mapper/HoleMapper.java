package com.cohelp.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cohelp.server.model.entity.Hole;

import java.util.List;

/**
* @author jianping5
* @description 针对表【hole(树洞表)】的数据库操作Mapper
* @createDate 2022-10-21 21:26:01
* @Entity com.cohelp.server.model.entity.Hole
*/
public interface HoleMapper extends BaseMapper<Hole> {

    /**
     * 根据热度排序查询树洞
     * @param teamId
     * @return
     */
    List<Hole> listByHot(Integer teamId);
    /**
     * 根据关键词搜索某人发布的树洞
     * @param teamId 组织Id
     * @param key 关键词
     * @param keywords 分词结果
     * @return java.util.List<com.cohelp.server.model.entity.Activity>
     */
    List<Hole> search(Integer teamId, String key, String[] keywords);


    /**
     * 查询用户当天发布的树洞数
     * @param userId 用户Id
     * @return long
     */
    long getCurrentDayPublish(Integer userId);
    /**
     * 查询用户本年度某月发布的树洞数
     * @param userId 用户Id
     * @return long
     */
    long getMonthPublish(Integer userId,Integer month);
}




