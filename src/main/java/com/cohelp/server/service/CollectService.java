package com.cohelp.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Collect;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.model.vo.DetailResponse;

import java.util.List;

/**
* @author zgy
* @description 针对表【collect(收藏表)】的数据库操作Service
* @createDate 2022-10-25 12:09:43
*/
public interface CollectService extends IService<Collect> {

    /**
     * 指定页码和数量分页查询收藏记录
     * @author: ZGY
     * @date: 2022-10-26 22:46
     * @return com.cohelp.server.model.domain.Result
     */
    Result<List<DetailResponse>> listCollect(User user, Integer page, Integer limit);
    /**
     * 根据Collect对象插入/删除收藏记录
     * @author: ZGY
     * @date: 2022-10-26 22:52
     * @param collect 待插入的收藏记录
     * @return com.cohelp.server.model.domain.Result
     */
    Result collectTopic(Collect collect);
    /**
     * 根据id删除指定收藏记录
     * @author: ZGY
     * @date: 2022-10-26 22:52
     * @param id 待删除的记录id
     * @return com.cohelp.server.model.domain.Result
     */
    Result deleteCollectRecord(Integer id);
    /**
     * 根据id删除指定收藏记录
     * @param ids 待删除的记录ids
     * @return com.cohelp.server.model.domain.Result
     */
    Result deleteCollectRecord(List<Integer> ids);
}
