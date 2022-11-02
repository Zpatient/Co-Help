package com.cohelp.server.service;

import com.cohelp.server.model.domain.HistoryAndCollectRequest;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.History;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @param historyRequest 浏览记录查询请求参数
     * @return com.cohelp.server.model.domain.Result
     */
    Result getHistoryList(HistoryAndCollectRequest historyRequest);
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
    Result deleteHistoryRecord(String id);
}
