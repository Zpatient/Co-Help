package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.mapper.HistoryMapper;
import com.cohelp.server.model.domain.IdAndType;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.*;
import com.cohelp.server.model.vo.DetailResponse;
import com.cohelp.server.service.*;
import com.cohelp.server.utils.PageUtil;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.UserHolder;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Resource
    ActivityService activityService;
    @Resource
    HelpService helpService;
    @Resource
    HoleService holeService;
    @Override
    public Result listHistory(User user,Integer page,Integer limit) {
        //参数判空
        if(ObjectUtils.anyNull(user,page,limit)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        //返回查询结果
        List<History> records = list(new QueryWrapper<History>().eq("user_id",user.getId()).select().orderByDesc("view_time"));
        for(History history:records){
            Integer topicId = history.getTopicId();
            Integer topicType = history.getTopicType();
            if(TypeEnum.isTopic(topicType)) {
                continue;
            }
            if(TypeEnum.isActivity(topicType)){
                Activity byId = activityService.getById(topicId);
                if(!byId.getTeamId().equals(user.getTeamId())){
                    records.remove(history);
                }
            }else if (TypeEnum.isHelp(topicType)){
                Help byId = helpService.getById(topicId);
                if(!byId.getTeamId().equals(user.getTeamId())){
                    records.remove(history);
                }
            }else if(TypeEnum.isHole(topicType)){
                Hole byId = holeService.getById(topicId);
                if(!byId.getTeamId().equals(user.getTeamId())){
                    records.remove(history);
                }
            }
        }
        List<IdAndType> idAndTypeList = getIdAndTypeList(records);
        //分页
        List<IdAndType> idAndTypes = PageUtil.pageByList(idAndTypeList, page, limit);
        List<DetailResponse> detailResponses = generalService.listDetailResponse(idAndTypes);
        return ResultUtil.returnResult(SUCCESS_GET_DATA,detailResponses,"数据查询成功");
    }
    @Override
    public Result<List<DetailResponse>> listInvolvedRecord(User user,Integer page,Integer limit) {
        if(ObjectUtils.anyNull(user,page,limit)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        QueryWrapper<History> historyQueryWrapper = new QueryWrapper<History>()
                .eq("user_id", user.getId())
                .eq("is_involved",1)
                .select()
                .orderByDesc("view_time");
        List<History> records = list(historyQueryWrapper);
        for(History history:records){
            Integer topicId = history.getTopicId();
            Integer topicType = history.getTopicType();
            if(TypeEnum.isTopic(topicType)){
                continue;
            }
            if(TypeEnum.isActivity(topicType)){
                Activity byId = activityService.getById(topicId);
                if(!byId.getTeamId().equals(user.getTeamId())){
                    records.remove(history);
                }
            }else if (TypeEnum.isHelp(topicType)){
                Help byId = helpService.getById(topicId);
                if(!byId.getTeamId().equals(user.getTeamId())){
                    records.remove(history);
                }
            }else if(TypeEnum.isHole(topicType)){
                Hole byId = holeService.getById(topicId);
                if(!byId.getTeamId().equals(user.getTeamId())){
                    records.remove(history);
                }
            }
        }
        List<IdAndType> idAndTypeList = getIdAndTypeList(records);
        List<IdAndType> idAndTypes = PageUtil.pageByList(idAndTypeList, page, limit);
        List<DetailResponse> detailResponses = generalService.listDetailResponse(idAndTypes);
        if(detailResponses==null){
            return ResultUtil.fail("获取失败！");
        }
        return ResultUtil.ok(SUCCESS_GET_DATA,detailResponses,"数据获取成功！");
    }
    @Override
    public Result insertHistoryRecord(History history) {
        //检验参数合法性
        if(ObjectUtils.anyNull(history)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Integer topicId = history.getTopicId();
        Integer topicType = history.getTopicType();
        Integer userId = UserHolder.getUser().getId();
        if(ObjectUtils.anyNull(topicId,userId)||!TypeEnum.isTopic(topicType)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        //判断该历史记录是否已存在
        QueryWrapper<History> queryWrapper = new QueryWrapper<History>()
                .eq("user_id", userId)
                .eq("topic_type", topicType)
                .eq("topic_id", topicId);
        History oldHistory = getOne(queryWrapper);
        if(oldHistory!=null){
            history.setId(oldHistory.getId());
            history.setViewTime(LocalDateTime.now());
        }
        //返回数据库操作结果
        history.setUserId(userId);
        boolean bool = saveOrUpdate(history);
        if(bool){
            return ResultUtil.ok(SUCCESS_REQUEST,"记录插入/更新成功！");
        }else{
            return ResultUtil.fail(ERROR_REQUEST,"记录插入失败");
        }
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
        //返回数据库操作结果
        boolean bool = removeById(id);
        if(bool) {
            return ResultUtil.ok(SUCCESS_REQUEST,"删除成功！");
        } else {
            return ResultUtil.fail(ERROR_REQUEST,"删除失败！");
        }
    }
    @Override
    public Result deleteHistoryRecord(List<Integer> ids) {
        //检验参数合法性
        if(ObjectUtils.anyNull(ids)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空！");
        }
        Boolean flag = false;
        for (Integer id :ids){
            Result result = deleteHistoryRecord(id);
            if(!result.getMessage().equals("删除成功！")){
                flag = true;
            }
        }
        if(!flag) {
            return ResultUtil.ok(SUCCESS_REQUEST,"删除成功！");
        } else {
            return ResultUtil.fail(ERROR_REQUEST,"删除失败！");
        }
    }


    private List<IdAndType> getIdAndTypeList(List<History> historyList){
        if(historyList==null){ return null;}
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




