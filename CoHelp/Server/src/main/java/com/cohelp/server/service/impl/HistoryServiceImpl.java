package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.model.domain.HistoryAndCollectRequest;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.History;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.service.HistoryService;
import com.cohelp.server.mapper.HistoryMapper;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.UserHolder;
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
    public Result getHistoryList(HistoryAndCollectRequest historyRequest) {
        //判断参数合法性
        if(ObjectUtils.anyNull(historyRequest)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Integer userId = historyRequest.getUserId();
        Integer pageNum = historyRequest.getPageNum();
        Integer recordMaxNum = historyRequest.getRecordMaxNum();
        if(ObjectUtils.anyNull(userId,pageNum,recordMaxNum)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        //判断当前用户权限
        User user = UserHolder.getUser();
        if(userId != user.getId())
            return ResultUtil.fail(ERROR_GET_DATA,"用户不一致！");
        //分页查询数据
        Page<History> historyPage = getBaseMapper().selectPage(new Page<>(pageNum, recordMaxNum),
                new QueryWrapper<History>().eq("user_id",userId).select().orderByDesc("view_time"));
        //返回查询结果
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
        //检验参数合法性
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
        //判断当前用户权限
        User user = UserHolder.getUser();
        if(userId != user.getId())
            return ResultUtil.fail(ERROR_GET_DATA,"用户不一致！");
        //返回数据库操作结果
        boolean bool = saveOrUpdate(history);
        if(bool)
            return ResultUtil.ok(SUCCESS_REQUEST,"记录插入/更新成功！");
        else
            return ResultUtil.fail(ERROR_REQUEST,"记录插入失败");
    }
    @Override
    public Result deleteHistoryRecord(String id) {
        //检验参数合法性
        if(ObjectUtils.anyNull(id)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空！");
        }
        if(ObjectUtils.anyNull(getById(id))){
            return ResultUtil.fail(ERROR_PARAMS,"记录不存在！");
        }
        //判断当前用户权限
        Integer userId = getById(id).getUserId();
        User user = UserHolder.getUser();
        if(userId != user.getId())
            return ResultUtil.fail(ERROR_GET_DATA,"用户不一致！");
        //返回数据库操作结果
        boolean bool = removeById(id);
        if(bool)
            return ResultUtil.ok(SUCCESS_REQUEST,"记录删除成功！");
        else
            return ResultUtil.fail(ERROR_REQUEST,"记录删除失败！");
    }
}




