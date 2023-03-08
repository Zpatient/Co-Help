package com.cohelp.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cohelp.server.model.entity.Ask;

/**
* @author 县城之子丶
* @description 针对表【ask】的数据库操作Mapper
* @createDate 2023-03-04 12:49:19
* @Entity com.cohelp.server.model.entity.Ask
*/
public interface AskMapper extends BaseMapper<Ask> {


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
    long getMonthPublish(Integer userId, Integer month);

}




