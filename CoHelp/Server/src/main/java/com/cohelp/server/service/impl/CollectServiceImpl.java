package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.mapper.CollectMapper;
import com.cohelp.server.model.domain.DetailResponse;
import com.cohelp.server.model.domain.IdAndType;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.*;
import com.cohelp.server.service.*;
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
* @author zgy
* @description 针对表【collect(收藏表)】的数据库操作Service实现
* @createDate 2022-10-25 12:09:43
*/
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect>
    implements CollectService{
    @Resource
    ActivityService activityService;
    @Resource
    HelpService helpService;
    @Resource
    HoleService holeService;
    @Resource
    GeneralService generalService;

    @Override
    public Result listCollect(User user) {
        //返回查询结果
        List<Collect> records = list(new QueryWrapper<Collect>().eq("user_id",user.getId()).select().orderByDesc("collect_time"));
        List<IdAndType> idAndTypeList = getIdAndTypeList(records);
        List<DetailResponse> detailResponses = generalService.listDetailResponse(user.getTeamId(),idAndTypeList);
        return ResultUtil.returnResult(SUCCESS_GET_DATA,detailResponses,"数据查询成功！");
    }
    @Override
    public Result insertCollectRecord(Collect collect) {
        //检验参数合法性
        if(ObjectUtils.anyNull(collect)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Integer topicId = collect.getTopicId();
        Integer topicType = collect.getTopicType();
        Integer userId = collect.getUserId();
        Date collectTime = collect.getCollectTime();
        if(ObjectUtils.anyNull(topicId,userId,collectTime)||!TypeEnum.isTopic(topicType)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        // //判断当前用户权限
        User user = UserHolder.getUser();
        if(!userId.equals(user.getId()))
            return ResultUtil.fail(INTERCEPTOR_LOGIN, "未登录");
        //判断该收藏记录是否已存在
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<Collect>()
                .eq("user_id", userId)
                .eq("topic_type", topicType)
                .eq("topic_id", topicId);
        Collect oldCollect = getOne(queryWrapper);
        if(oldCollect!=null){
            collect.setId(oldCollect.getId());
        }
        //返回数据库操作结果
        boolean bool = saveOrUpdate(collect);
        if(TypeEnum.isActivity(topicType)){
            Activity activity = activityService.getById(topicId);
            activity.setActivityCollect(activity.getActivityCollect()+1);
        }else if(TypeEnum.isHelp(topicType)){
            Help help = helpService.getById(topicId);
            help.setHelpCollect(help.getHelpCollect()+1);
        }else {
            Hole hole = holeService.getById(topicId);
            hole.setHoleCollect(hole.getHoleCollect()+1);
        }
        if(bool)
            return ResultUtil.ok(SUCCESS_REQUEST,"记录插入/更新成功！");
        else
            return ResultUtil.fail(ERROR_REQUEST,"记录插入失败");
    }
    @Override
    public Result deleteCollectRecord(Integer id) {
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
        Integer topicType = getById(id).getTopicType();
        Integer topicId = getById(id).getTopicId();
        boolean bool = removeById(id);
        if(TypeEnum.isActivity(topicType)){
            Activity activity = activityService.getById(topicId);
            activity.setActivityCollect(activity.getActivityCollect()-1);
        }else if(TypeEnum.isHelp(topicType)){
            Help help = helpService.getById(topicId);
            help.setHelpCollect(help.getHelpCollect()-1);
        }else {
            Hole hole = holeService.getById(topicId);
            hole.setHoleCollect(hole.getHoleCollect()-1);
        }
        if(bool)
            return ResultUtil.ok(SUCCESS_REQUEST,"记录删除成功！");
        else
            return ResultUtil.fail(ERROR_REQUEST,"记录删除失败！");
    }
    private List<IdAndType> getIdAndTypeList(List<Collect> collectList){
        if(collectList==null) return null;
        List<IdAndType> idAndTypes = new ArrayList<IdAndType>();
        for(Collect collect : collectList){
            Integer topicType = collect.getTopicType();
            Integer topicId = collect.getTopicId();
            IdAndType idAndType = new IdAndType();
            idAndType.setId(topicId);
            idAndType.setType(topicType);
            idAndTypes.add(idAndType);
        }
        return idAndTypes;
    }
}




