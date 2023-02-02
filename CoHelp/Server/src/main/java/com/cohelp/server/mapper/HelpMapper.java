package com.cohelp.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cohelp.server.model.entity.Help;

import java.util.List;

/**
* @author jianping5
* @description 针对表【help(互助表)】的数据库操作Mapper
* @createDate 2022-10-21 21:25:55
* @Entity com.cohelp.server.model.entity.Help
*/
public interface HelpMapper extends BaseMapper<Help> {

    /**
     * 根据热度排序查询互助
     * @param teamId
     * @return
     */
    List<Help> listByHot(Integer teamId);
    /**
     * 根据关键词搜索某人发布的互助
     * @param userId 用户Id
     * @param key 关键词
     * @param keywords 分词结果
     * @return java.util.List<com.cohelp.server.model.entity.Activity>
     */
    List<Help> search(Integer userId, String key, String[] keywords);
    /**
     * 查询用户当天发布的互助数
     * @param userId 用户Id
     * @return long
     */
    long getCurrentDayPublish(Integer userId);
    /**
     * 查询用户本年度某月发布的互助数
     * @param userId 用户Id
     * @return long
     */
    long getMonthPublish(Integer userId,Integer month);

}




