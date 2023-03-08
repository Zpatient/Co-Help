package com.cohelp.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.History;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.model.vo.DetailResponse;

import java.util.List;

/**
* @author jianping5
* @description 针对表【history(浏览记录表)】的数据库操作Service
* @createDate 2022-09-19 21:36:09
*/
public interface HistoryService extends IService<History> {
    /**
     * 指定页码和数量分页查询浏览记录
     * @author: ZGY
     * @date: 2022-10-24 22:46
     * @return com.cohelp.server.model.domain.Result
     */
    Result<List<DetailResponse>> listHistory(User user,Integer page,Integer limit);
    /**
     * 根据History对象插入浏览记录
     * @author: ZGY
     * @date: 2022-10-24 22:52
     * @param history 待插入的浏览记录
     * @return com.cohelp.server.model.domain.Result
     */
    Result insertHistoryRecord(History history);
    /**
     * 根据id删除指定浏览记录
     * @author: ZGY
     * @date: 2022-10-24 22:52
     * @param id 待删除的记录id
     * @return com.cohelp.server.model.domain.Result
     */
    Result deleteHistoryRecord(Integer id);
    /**
     * 根据id删除指定浏览记录
     * @author: ZGY
     * @date: 2022-10-24 22:52
     * @param ids 待删除的记录id
     * @return com.cohelp.server.model.domain.Result
     */
    Result deleteHistoryRecord(List<Integer> ids);
    /**
     * 获取用户参与评论的话题
     * @return com.cohelp.server.model.domain.Result
     */
    Result<List<DetailResponse>> listInvolvedRecord(User user,Integer page,Integer limit);

}
