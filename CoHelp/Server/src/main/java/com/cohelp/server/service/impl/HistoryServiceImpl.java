package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.model.domain.HistoryRequest;
import com.cohelp.server.model.domain.HistoryRequest;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.History;
import com.cohelp.server.model.entity.History;
import com.cohelp.server.service.HistoryService;
import com.cohelp.server.mapper.HistoryMapper;
import com.cohelp.server.utils.ResultUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.cohelp.server.constant.StatusCode.*;
import static com.cohelp.server.constant.StatusCode.ERROR_REQUEST;

/**
* @author jianping5
* @description 针对表【history(浏览记录表)】的数据库操作Service实现
* @createDate 2022-09-19 21:36:09
*/
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History>
    implements HistoryService {

    @Override
    public Result getHistoryList(HistoryRequest historyRequest) {
        if(ObjectUtils.anyNull(historyRequest)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Integer pageNum = historyRequest.getPageNum();
        Integer recordMaxNum = historyRequest.getRecordMaxNum();
        if(ObjectUtils.anyNull(pageNum,recordMaxNum)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        Page<History> historyPage = getBaseMapper().selectPage(new Page<>(pageNum, recordMaxNum),
                new QueryWrapper<History>().select().orderByDesc("view_time"));

        if(ObjectUtils.anyNull(historyPage)){
            return ResultUtil.fail(ERROR_GET_DATA,"数据查询失败！");
        }
        else{
            List<History> records = historyPage.getRecords();
            return ResultUtil.returnResult(SUCCESS_GET_DATA,records,"数据获取成功！");
        }
    }
    @Override
    public Result insertHistoryRecord(History history) {
        if(ObjectUtils.anyNull(history)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Integer topicId = history.getTopicId();
        Integer topicType = history.getTopicType();
        Integer userId = history.getUserId();
        Date historyTime = history.getViewTime();
        if(ObjectUtils.anyNull(topicId,userId,historyTime)||!TypeEnum.isTopic(topicType)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        boolean bool = saveOrUpdate(history);
        if(bool)
            return ResultUtil.ok(SUCCESS_REQUEST,"记录插入/更新成功！");
        else
            return ResultUtil.fail(ERROR_REQUEST,"记录插入失败");
    }

    @Override
    public Result deleteHistoryRecord(String id) {
        if(ObjectUtils.anyNull(id)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空！");
        }
        if(ObjectUtils.anyNull(getById(id))){
            return ResultUtil.fail(ERROR_PARAMS,"记录不存在！");
        }
        boolean bool = removeById(id);
        if(bool)
            return ResultUtil.ok(SUCCESS_REQUEST,"记录删除成功！");
        else
            return ResultUtil.fail(ERROR_REQUEST,"记录删除失败！");
    }
}




