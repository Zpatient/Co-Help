package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.mapper.HistoryMapper;
import com.cohelp.server.model.domain.DetailResponse;
import com.cohelp.server.model.domain.HistoryAndCollectRequest;
import com.cohelp.server.model.domain.IdAndType;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.History;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.service.GeneralService;
import com.cohelp.server.service.HistoryService;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.UserHolder;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.cohelp.server.constant.StatusCode.*;

/**
* @author jianping5
* @description 针对表【history(浏览记录表)】的数据库操作Service实现
* @createDate 2022-09-19 21:36:09
*/
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History>
    implements HistoryService {

    @Resource
    GeneralService generalService;
    @Override
    public Result listHistory(HistoryAndCollectRequest historyRequest) {
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
        if(!userId.equals(user.getId()))
            return ResultUtil.fail(INTERCEPTOR_LOGIN, "未登录");
        //分页查询数据
        Page<History> historyPage = getBaseMapper().selectPage(new Page<>(pageNum, recordMaxNum),
                new QueryWrapper<History>().eq("user_id",userId).select().orderByDesc("view_time"));
        //返回查询结果
        if(ObjectUtils.anyNull(historyPage)){
            return ResultUtil.fail(ERROR_GET_DATA,"数据查询失败！");
        }
        else{
            List<History> records = historyPage.getRecords();
            List<IdAndType> idAndTypeList = getIdAndTypeList(records);
            List<DetailResponse> detailResponses = generalService.listDetailResponse(idAndTypeList);
            return ResultUtil.returnResult(SUCCESS_GET_DATA,detailResponses,"数据获取成功！");
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
        if(!userId.equals(user.getId()))
            return ResultUtil.fail(INTERCEPTOR_LOGIN, "未登录");
        //判断该历史记录是否已存在
        QueryWrapper<History> queryWrapper = new QueryWrapper<History>()
                .eq("user_id", userId)
                .eq("topic_type", topicType)
                .eq("topic_id", topicId);
        History oldHistory = getOne(queryWrapper);
        if(oldHistory!=null){
            history.setId(oldHistory.getId());
        }
        //返回数据库操作结果
        boolean bool = saveOrUpdate(history);
        if(bool)
            return ResultUtil.ok(SUCCESS_REQUEST,"记录插入/更新成功！");
        else
            return ResultUtil.fail(ERROR_REQUEST,"记录插入失败");
    }
    @Override
    public Result deleteHistoryRecord(Integer id) {
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
        if(!userId.equals(user.getId()))
            return ResultUtil.fail(INTERCEPTOR_LOGIN, "未登录");
        //返回数据库操作结果
        boolean bool = removeById(id);
        if(bool)
            return ResultUtil.ok(SUCCESS_REQUEST,"记录删除成功！");
        else
            return ResultUtil.fail(ERROR_REQUEST,"记录删除失败！");
    }

    @Override
    public Result listInvolvedRecord(Integer currentUserId) {
        if(currentUserId==null){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        QueryWrapper<History> historyQueryWrapper = new QueryWrapper<History>()
                .eq("user_id", currentUserId)
                .eq("is_involved",1)
                .select()
                .orderByDesc("view_time");
        List<History> list = list(historyQueryWrapper);
        List<IdAndType> idAndTypeList = getIdAndTypeList(list);
        List<DetailResponse> detailResponses = generalService.listDetailResponse(idAndTypeList);
        if(detailResponses==null) return ResultUtil.fail("获取失败！");
        return ResultUtil.ok(SUCCESS_GET_DATA,detailResponses,"数据获取成功！");
    }
    private List<IdAndType> getIdAndTypeList(List<History> historyList){
        if(historyList==null) return null;
        List<IdAndType> idAndTypes = new ArrayList<IdAndType>();
        for(History history : historyList){
            Integer topicType = history.getTopicType();
            Integer topicId = history.getTopicId();
            IdAndType idAndType = new IdAndType();
            idAndType.setId(topicId);
            idAndType.setType(topicType);
            idAndTypes.add(idAndType);
        }
        return idAndTypes;
    }
}




