package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.mapper.CollectMapper;
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
import java.util.ArrayList;
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
    @Resource
    private AskService askService;

    @Override
    public Result listCollect(User user,Integer page,Integer limit) {
        if (ObjectUtils.anyNull(user,page,limit)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        //返回查询结果
        List<Collect> records = list(new QueryWrapper<Collect>()
                .eq("user_id",user.getId())
                .select()
                .orderByDesc("collect_time"));
        for(Collect collect:records){
            Integer topicId = collect.getTopicId();
            Integer topicType = collect.getTopicType();
            if(!TypeEnum.isTopic(topicType)&&!topicType.equals(TypeEnum.ASK.ordinal())){
                continue;
            }
            if(TypeEnum.isActivity(topicType)){
                Activity byId = activityService.getById(topicId);
                if(!byId.getTeamId().equals(user.getTeamId())){
                    records.remove(collect);
                }
            }else if (TypeEnum.isHelp(topicType)){
                Help byId = helpService.getById(topicId);
                if(!byId.getTeamId().equals(user.getTeamId())){
                    records.remove(collect);
                }
            }else if(TypeEnum.isHole(topicType)){
                Hole byId = holeService.getById(topicId);
                if(!byId.getTeamId().equals(user.getTeamId())){
                    records.remove(collect);
                }
            }
        }
        List<IdAndType> idAndTypeList = getIdAndTypeList(records);
        List<IdAndType> idAndTypes = PageUtil.pageByList(idAndTypeList, page, limit);
        List<DetailResponse> detailResponses = generalService.listDetailResponse(idAndTypes);
        return ResultUtil.returnResult(SUCCESS_GET_DATA,detailResponses,"数据查询成功！");
    }
    @Override
    public Result collectTopic(Collect collect) {
        //检验参数合法性
        if(ObjectUtils.anyNull(collect)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Integer topicId = collect.getTopicId();
        Integer topicType = collect.getTopicType();
        Integer userId = UserHolder.getUser().getId();
        if(ObjectUtils.anyNull(topicId,userId)||!TypeEnum.isTopic(topicType)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        //判断该收藏记录是否已存在
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<Collect>()
                .eq("user_id", userId)
                .eq("topic_type", topicType)
                .eq("topic_id", topicId);
        Collect oldCollect = getOne(queryWrapper);
        //已存在,则删除收藏记录并同步更新话题收藏数
        boolean bool = false;
        if(oldCollect!=null){
            if(TypeEnum.isActivity(topicType)){
                Activity activity = activityService.getById(topicId);
                activity.setActivityCollect(activity.getActivityCollect()-1);
                activityService.saveOrUpdate(activity);
            }else if(TypeEnum.isHelp(topicType)){
                Help help = helpService.getById(topicId);
                help.setHelpCollect(help.getHelpCollect()-1);
                helpService.saveOrUpdate(help);
            }else if(TypeEnum.isHole(topicType)){
                Hole hole = holeService.getById(topicId);
                hole.setHoleCollect(hole.getHoleCollect()-1);
                holeService.saveOrUpdate(hole);
            }
            bool = removeById(oldCollect);
        }else {//不存在则添加收藏记录并同步更新话题收藏数
            if(TypeEnum.isActivity(topicType)){
                Activity activity = activityService.getById(topicId);
                activity.setActivityCollect(activity.getActivityCollect()+1);
                activityService.saveOrUpdate(activity);
            }else if(TypeEnum.isHelp(topicType)){
                Help help = helpService.getById(topicId);
                help.setHelpCollect(help.getHelpCollect()+1);
                helpService.saveOrUpdate(help);
            }else if(TypeEnum.isHole(topicType)){
                Hole hole = holeService.getById(topicId);
                hole.setHoleCollect(hole.getHoleCollect()+1);
                holeService.saveOrUpdate(hole);
            }
            collect.setUserId(userId);
            bool = saveOrUpdate(collect);
        }
        if(bool){
            return ResultUtil.ok(SUCCESS_REQUEST,"记录更新成功！");
        } else{
            return ResultUtil.fail(ERROR_REQUEST,"记录更新失败");
        }
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
        if(!userId.equals(user.getId())){
            return ResultUtil.fail(INTERCEPTOR_LOGIN, "未登录");
        }
        //返回数据库操作结果
        Integer topicType = getById(id).getTopicType();
        Integer topicId = getById(id).getTopicId();
        boolean bool = removeById(id);
        if(bool){
            if(TypeEnum.isActivity(topicType)){
                Activity activity = activityService.getById(topicId);
                if(activity!=null){
                    activity.setActivityCollect(activity.getActivityCollect()-1);
                    activityService.saveOrUpdate(activity);
                }
            }else if(TypeEnum.isHelp(topicType)){
                Help help = helpService.getById(topicId);
                if(help!=null){
                    help.setHelpCollect(help.getHelpCollect()-1);
                    helpService.saveOrUpdate(help);
                }
            }else if(TypeEnum.isHole(topicType)){
                Hole hole = holeService.getById(topicId);
                if(hole!=null){
                    hole.setHoleCollect(hole.getHoleCollect()-1);
                    holeService.saveOrUpdate(hole);
                }
            }else if(topicType.equals(TypeEnum.ASK.ordinal())){
                Ask ask = askService.getById(topicId);
                if(ask!=null){
                    ask.setCollectCount(ask.getCollectCount());
                    askService.saveOrUpdate(ask);
                }
            }
        }
        if(bool){
            return ResultUtil.ok(SUCCESS_REQUEST,"删除成功！");
        }
        else{
            return ResultUtil.fail(ERROR_REQUEST,"删除失败！");
        }
    }
    @Override
    public Result deleteCollectRecord(List<Integer> ids) {
        //检验参数合法性
        if(ObjectUtils.anyNull(ids)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空！");
        }
        Boolean flag = false;
        for (Integer id:ids){
            Result result = deleteCollectRecord(id);
            if(!result.getMessage().equals("删除成功！")){
                flag = true;
            }
        }
        if(flag){
            return ResultUtil.ok(ERROR_REQUEST,"删除异常！");
        }else {
            return ResultUtil.fail(SUCCESS_REQUEST,"删除成功！");
        }
    }
    private List<IdAndType> getIdAndTypeList(List<Collect> collectList){
        if(collectList==null){
            return null;
        }
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




