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
     * @param userIdList
     * @return
     */
    List<Hole> listByHot(List<Integer> userIdList);

    List<Hole> search(Integer userId, String key, String[] keywords);
}




