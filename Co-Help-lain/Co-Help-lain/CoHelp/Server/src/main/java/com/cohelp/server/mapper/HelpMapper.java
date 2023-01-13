package com.cohelp.server.mapper;

import com.cohelp.server.model.entity.Help;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
     * @return
     */
    List<Help> listByHot();

}




