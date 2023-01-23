package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.mapper.ActivityMapper;
import com.cohelp.server.mapper.HelpMapper;
import com.cohelp.server.mapper.HoleMapper;
import com.cohelp.server.model.domain.*;
import com.cohelp.server.model.entity.*;
import com.cohelp.server.model.vo.ActivityVO;
import com.cohelp.server.model.vo.HelpVO;
import com.cohelp.server.model.vo.HoleVO;
import com.cohelp.server.service.*;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.SensitiveUtils;
import com.cohelp.server.utils.UserHolder;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static com.cohelp.server.constant.StatusCode.*;

/**
 * @author zgy
 * @create 2022-10-23 15:48
 */
@Service
public class GeneralServiceImpl implements GeneralService {

    @Resource
    ActivityService activityService;
    @Resource
    ActivityMapper activityMapper;
    @Resource
    HelpService helpService;
    @Resource
    HelpMapper helpMapper;
    @Resource
    HoleService holeService;
    @Resource
    HoleMapper holeMapper;
    @Resource
    ImageService imageService;
    @Resource
    RemarkActivityService remarkActivityService;
    @Resource
    RemarkHelpService remarkHelpService;
    @Resource
    RemarkHoleService remarkHoleService;
    @Resource
    HistoryService historyService;
    @Resource
    private UserService userService;

    @Override
    public Result getDetail(IdAndType idAndType) {
        //判断参数合法性
        if(ObjectUtils.anyNull(idAndType)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Integer type = idAndType.getType();
        Integer id = idAndType.getId();
        if(!TypeEnum.isTopic(type)|| ObjectUtils.anyNull(id)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        //获取该话题对应的的图片URL列表
        ArrayList<String> imagesUrl = imageService.getImageList(idAndType);
        if(ObjectUtils.anyNull(imagesUrl)){
            imagesUrl = new ArrayList<>();
        }
        //判断请求哪种话题的详情并执行相应操作
        if(TypeEnum.isActivity(type)){
            Activity activity = activityService.getBaseMapper().selectById(id);
            if(!ObjectUtils.anyNull(activity)){
                ActivityServiceImpl activityServiceImpl = (ActivityServiceImpl)activityService;
                ActivityVO activityVO = activityServiceImpl.traverseActivity(activity);
                String publisherAvatarUrl = imageService.getById(activityVO.getAvatar()).getImageUrl();
                DetailResponse detailResponse = new DetailResponse();
                detailResponse.setActivityVO(activityVO);
                detailResponse.setPublisherAvatarUrl(publisherAvatarUrl);
                detailResponse.setImagesUrl(imagesUrl);
                //插入历史记录
                QueryWrapper<History> queryWrapper = new QueryWrapper<History>()
                        .eq("user_id", UserHolder.getUser().getId())
                        .eq("topic_type", type)
                        .eq("topic_id",id);
                History oldHistory = historyService.getOne(queryWrapper);
                if(oldHistory==null){
                    History history = new History();
                    history.setUserId(UserHolder.getUser().getId());
                    history.setTopicType(type);
                    history.setTopicId(id);
                    historyService.saveOrUpdate(history);
                }
                return ResultUtil.returnResult(SUCCESS_GET_DATA,detailResponse,"数据获取成功！");
            }
            else{
                return ResultUtil.returnResult(ERROR_GET_DATA,null,"数据获取失败");
            }
        }
        else if(TypeEnum.isHelp(type)){
            Help help = helpService.getById(id);
            if(!ObjectUtils.anyNull(help)){
                HelpServiceImpl helpServiceImpl = (HelpServiceImpl)helpService;
                HelpVO helpVO = helpServiceImpl.traverseHelp(help);
                String publisherAvatarUrl = imageService.getById(helpVO.getAvatar()).getImageUrl();
                DetailResponse detailResponse = new DetailResponse();
                detailResponse.setHelpVO(helpVO);
                detailResponse.setPublisherAvatarUrl(publisherAvatarUrl);
                detailResponse.setImagesUrl(imagesUrl);
                //插入历史记录
                QueryWrapper<History> queryWrapper = new QueryWrapper<History>()
                        .eq("user_id", UserHolder.getUser().getId())
                        .eq("topic_type", type)
                        .eq("topic_id",id);
                History oldHistory = historyService.getOne(queryWrapper);
                if(oldHistory==null){
                    History history = new History();
                    history.setUserId(UserHolder.getUser().getId());
                    history.setTopicType(type);
                    history.setTopicId(id);
                    historyService.saveOrUpdate(history);
                }
                return ResultUtil.returnResult(SUCCESS_GET_DATA,detailResponse,"数据获取成功！");
            }
            else{
                return ResultUtil.returnResult(ERROR_GET_DATA,null,"数据获取失败！");
            }
        }
        else {
            Hole hole = holeService.getById(id);
            if(!ObjectUtils.anyNull(hole)) {
                HoleServiceImpl holeServiceImpl = (HoleServiceImpl)holeService;
                HoleVO holeVO = holeServiceImpl.traverseHole(hole);
                String publisherAvatarUrl = imageService.getById(holeVO.getAvatar()).getImageUrl();
                DetailResponse detailResponse = new DetailResponse();
                detailResponse.setHoleVO(holeVO);
                detailResponse.setPublisherAvatarUrl(publisherAvatarUrl);
                detailResponse.setImagesUrl(imagesUrl);
                //插入历史记录
                QueryWrapper<History> queryWrapper = new QueryWrapper<History>()
                        .eq("user_id", UserHolder.getUser().getId())
                        .eq("topic_type", type)
                        .eq("topic_id",id);
                History oldHistory = historyService.getOne(queryWrapper);
                if(oldHistory==null){
                    History history = new History();
                    history.setUserId(UserHolder.getUser().getId());
                    history.setTopicType(type);
                    history.setTopicId(id);
                    historyService.saveOrUpdate(history);
                }
                return ResultUtil.returnResult(SUCCESS_GET_DATA,detailResponse, "数据获取成功");
            }
            else{
                return ResultUtil.returnResult(ERROR_GET_DATA,null,"数据获取失败");
            }
        }
    }

    @Override
    public Result search(SearchRequest searchRequest) {
        //判断参数合法性
        if(ObjectUtils.anyNull(searchRequest)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        String key = searchRequest.getKey();
        List<Integer> types = searchRequest.getTypes();
        if(!TypeEnum.isTopic(types)|| StringUtils.isBlank(key)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        //获取用户所在组织成员Id列表
        List<Integer> userIdList = getUserIdList();
        //查询数据
        String[] keywords = getKeywords(key).split(",");
        List<IdAndType> idAndTypeList = new ArrayList<>();
        for(Integer userId : userIdList){
            for(Integer type : types){
                if(TypeEnum.isActivity(type)){
//                    QueryWrapper<Activity> queryWrapper = new QueryWrapper<Activity>()
//                            .select("id")
//                            .eq("activity_owner_id",userId)
//                            .like("activity_title",key)
//                            .or().like("activity_detail",key)
//                            .or().like("activity_label",key);
//                    for(String keyword : keywords){
//                        queryWrapper.or().like("activity_title",keyword)
//                                .or().like("activity_detail",keyword)
//                                .or().like("activity_label",keyword);
//                    }
//                    List<Activity> activityList = activityService.list(queryWrapper);
                    List<Activity> activityList = activityMapper.search(userId, key, keywords);
                    for(Activity activity:activityList){
                        Integer id = activity.getId();
                        idAndTypeList.add(new IdAndType(id,type));
                    }
                }
                else if(TypeEnum.isHelp(type)){
//                    QueryWrapper<Help> queryWrapper = new QueryWrapper<Help>()
//                            .select("id")
//                            .eq("help_owner_id",userId)
//                            .like("help_title",key)
//                            .or().like("help_detail",key)
//                            .or().like("help_label",key);
//                    for(String keyword : keywords){
//                        queryWrapper.or().like("help_title",keyword)
//                                .or().like("help_detail",keyword)
//                                .or().like("help_label",keyword);
//                    }
//                    List<Help> helpList = helpService.list(queryWrapper);
                    List<Help> helpList = helpMapper.search(userId, key, keywords);
                    for(Help help:helpList) {
                        Integer id = help.getId();
                        idAndTypeList.add(new IdAndType(id,type));
                    }
                }
                else{
//                    QueryWrapper<Hole> queryWrapper = new QueryWrapper<Hole>()
//                            .select("id")
//                            .eq("hole_owner_id",userId)
//                            .like("hole_title",key)
//                            .or().like("hole_detail",key)
//                            .or().like("hole_label",key);
//                    for(String keyword : keywords){
//                        queryWrapper.or().like("hole_title",keyword)
//                                .or().like("hole_detail",keyword)
//                                .or().like("hole_label",keyword);
//                    }
//                    List<Hole> holeList = holeService.list(queryWrapper);
                    List<Hole> holeList = holeMapper.search(userId, key, keywords);
                    for(Hole hole:holeList) {
                        Integer id = hole.getId();
                        idAndTypeList.add(new IdAndType(id,type));
                    }
                }
            }
        }
        List<DetailResponse> detailResponses = listDetailResponse(idAndTypeList);
        return ResultUtil.ok(SUCCESS_GET_DATA, detailResponses,"数据查询成功！");
    }

    @Override
    public Result insertRemark(RemarkRequest remarkRequest) {
        //判断参数合法性
        if(ObjectUtils.anyNull(remarkRequest)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        //插入评论到评论表
        Integer type = remarkRequest.getType();
        if(!TypeEnum.isTopic(type)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        else if(TypeEnum.isActivity(type)){
            RemarkActivity remarkActivity = remarkRequest.getRemarkActivity();
            if(ObjectUtils.anyNull(remarkActivity))
                return ResultUtil.fail(ERROR_PARAMS,"参数不合法");

            // 设置评论用户者id
            remarkActivity.setRemarkOwnerId(UserHolder.getUser().getId());

            // 敏感词过滤
            String remarkContent = remarkActivity.getRemarkContent();
            if (SensitiveUtils.contains(remarkContent)) {
                return ResultUtil.fail("文本涉及敏感词汇");
            }

            //插入评论（仅插入）
            boolean result = remarkActivityService.save(remarkActivity);
            if(!result){
                return ResultUtil.fail("评论失败！");
            }
            else{
                QueryWrapper<History> historyQueryWrapper = new QueryWrapper<History>()
                        .eq("user_id",UserHolder.getUser().getId())
                        .eq("topic_type",TypeEnum.ACTIVITY.ordinal())
                        .eq("topic_id",remarkActivity.getRemarkActivityId());
                History oldHistory = historyService.getOne(historyQueryWrapper);
                if(oldHistory!=null){
                    oldHistory.setIsInvolved(1);
                    historyService.saveOrUpdate(oldHistory);
                }
                else{
                    History history = new History();
                    history.setUserId(UserHolder.getUser().getId());
                    history.setTopicType(TypeEnum.ACTIVITY.ordinal());
                    history.setTopicId(remarkActivity.getRemarkActivityId());
                    history.setIsInvolved(1);
                    historyService.saveOrUpdate(history);
                }
                return ResultUtil.fail("评论成功！");
            }
        }
        else if(TypeEnum.isHelp(type)){
            RemarkHelp remarkHelp = remarkRequest.getRemarkHelp();
            if(ObjectUtils.anyNull(remarkHelp))
                return ResultUtil.fail(ERROR_PARAMS,"参数不合法");

            // 设置评论用户者id
            remarkHelp.setRemarkOwnerId(UserHolder.getUser().getId());

            // 敏感词过滤
            String remarkContent = remarkHelp.getRemarkContent();
            if (SensitiveUtils.contains(remarkContent)) {
                return ResultUtil.fail("文本涉及敏感词汇");
            }

            //插入评论
            boolean result = remarkHelpService.save(remarkHelp);

            if(!result)
                return ResultUtil.fail("评论失败！");
            else {
                QueryWrapper<History> historyQueryWrapper = new QueryWrapper<History>()
                        .eq("user_id", UserHolder.getUser().getId())
                        .eq("topic_type", TypeEnum.HELP.ordinal())
                        .eq("topic_id", remarkHelp.getRemarkHelpId());
                History oldHistory = historyService.getOne(historyQueryWrapper);
                if (oldHistory != null) {
                    oldHistory.setIsInvolved(1);
                    historyService.saveOrUpdate(oldHistory);
                } else {
                    History history = new History();
                    history.setUserId(UserHolder.getUser().getId());
                    history.setTopicType(TypeEnum.HELP.ordinal());
                    history.setTopicId(remarkHelp.getRemarkHelpId());
                    history.setIsInvolved(1);
                    historyService.saveOrUpdate(history);
                }
                return ResultUtil.fail("评论成功！");
            }
        }
        else {
            RemarkHole remarkHole = remarkRequest.getRemarkHole();
            if (ObjectUtils.anyNull(remarkHole))
                return ResultUtil.fail(ERROR_PARAMS, "参数不合法");

            // 设置评论用户者id
            remarkHole.setRemarkOwnerId(UserHolder.getUser().getId());

            // 敏感词过滤
            String remarkContent = remarkHole.getRemarkContent();
            if (SensitiveUtils.contains(remarkContent)) {
                return ResultUtil.fail("文本涉及敏感词汇");
            }

            //插入评论
            boolean result = remarkHoleService.save(remarkHole);

            if (!result)
                return ResultUtil.fail("评论失败！");
            else {
                QueryWrapper<History> historyQueryWrapper = new QueryWrapper<History>()
                        .eq("user_id", UserHolder.getUser().getId())
                        .eq("topic_type", TypeEnum.HOLE.ordinal())
                        .eq("topic_id", remarkHole.getRemarkHoleId());
                History oldHistory = historyService.getOne(historyQueryWrapper);
                if (oldHistory != null) {
                    oldHistory.setIsInvolved(1);
                    historyService.saveOrUpdate(oldHistory);
                }
                else{
                    History history = new History();
                    history.setUserId(UserHolder.getUser().getId());
                    history.setTopicType(TypeEnum.HOLE.ordinal());
                    history.setTopicId(remarkHole.getRemarkHoleId());
                    history.setIsInvolved(1);
                    historyService.saveOrUpdate(history);
                }
                return ResultUtil.fail("评论成功！");
            }
        }
    }

    @Override
    public Result deleteRemark(IdAndType idAndType) {
        //判断参数合法性
        if(ObjectUtils.anyNull(idAndType)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Integer type = idAndType.getType();
        Integer id = idAndType.getId();
        //根据type和id到相应评论表删除评论
        User user = UserHolder.getUser();
        if(ObjectUtils.anyNull(type,id)||!TypeEnum.isTopic(type)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        else if(TypeEnum.isActivity(type)){
            RemarkActivity remarkActivity = remarkActivityService.getById(id);
            Integer remarkOwnerId = remarkActivity.getRemarkOwnerId();
            if(!remarkOwnerId.equals(user.getId()))
                return ResultUtil.fail(INTERCEPTOR_LOGIN, "未登录");
            boolean result = remarkActivityService.removeById(id);
            if(!result)
                return ResultUtil.fail("评论删除失败！");
            else
                return ResultUtil.ok("评论删除成功！");
        }
        else if(TypeEnum.isHelp(type)){
            RemarkHelp remarkHelp = remarkHelpService.getById(id);
            Integer remarkOwnerId = remarkHelp.getRemarkOwnerId();
            if(!remarkOwnerId.equals(user.getId()))
                return ResultUtil.fail(INTERCEPTOR_LOGIN, "未登录");
            boolean result = remarkHelpService.removeById(id);
            if(!result)
                return ResultUtil.fail("评论删除失败！");
            else
                return ResultUtil.ok("评论删除成功！");
        }
        else{
            RemarkHole remarkHole = remarkHoleService.getById(id);
            Integer remarkOwnerId = remarkHole.getRemarkOwnerId();
            if(!remarkOwnerId.equals(user.getId()))
                return ResultUtil.fail(INTERCEPTOR_LOGIN, "未登录");
            boolean result = remarkHoleService.removeById(id);
            if(!result)
                return ResultUtil.fail("评论删除失败！");
            else
                return ResultUtil.ok("评论删除成功！");
        }
    }

    @Override
    public Result listRemark(IdAndType idAndType) {
        //判断参数合法性
        if(ObjectUtils.anyNull(idAndType)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Integer type = idAndType.getType();
        Integer id = idAndType.getId();
        //根据type和id到相应评论表删除评论
        if(ObjectUtils.anyNull(type,id)||!TypeEnum.isTopic(type)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        else if(TypeEnum.isActivity(type)){
            QueryWrapper<RemarkActivity> remarkQueryWrapper = new QueryWrapper<RemarkActivity>().eq("remark_activity_id", id);
            List<RemarkActivity> remarkList = remarkActivityService.list(remarkQueryWrapper);
            return ResultUtil.returnResult(SUCCESS_GET_DATA,remarkList,"评论查询成功");
        }
        else if(TypeEnum.isHelp(type)){
            QueryWrapper<RemarkHelp> remarkQueryWrapper = new QueryWrapper<RemarkHelp>().eq("remark_help_id", id);
            List<RemarkHelp> remarkList = remarkHelpService.list(remarkQueryWrapper);
            return ResultUtil.returnResult(SUCCESS_GET_DATA,remarkList,"评论查询成功");
        }
        else{
            QueryWrapper<RemarkHole> remarkQueryWrapper = new QueryWrapper<RemarkHole>().eq("remark_hole_id", id);
            List<RemarkHole> remarkList = remarkHoleService.list(remarkQueryWrapper);
            return ResultUtil.returnResult(SUCCESS_GET_DATA,remarkList,"评论查询成功");
        }
    }

    /**
     * @param key 搜索关键词
     * @return 分词词组(,拼接)
     */
    private String getKeywords(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        StringReader reader = new StringReader(key);
        IKSegmenter iks = new IKSegmenter(reader, true);
        StringBuilder buffer = new StringBuilder();
        try {
            Lexeme lexeme;
            while ((lexeme = iks.next()) != null) {
                buffer.append(lexeme.getLexemeText()).append(',');
            }
        } catch (IOException e) {
        }
        if (buffer.length() > 0) {
            buffer.setLength(buffer.length() - 1);
        }
        return buffer.toString();
    }

    @Override
    public List<Integer> getUserIdList() {
        // 获取当前用户的组织id
        User currentUser = UserHolder.getUser();
        Integer teamId = currentUser.getTeamId();

        // 获取该组织的所有成员
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("team_id", teamId).or().eq("team_id", 1);
        List<User> userList = userService.list(queryWrapper);

        // 获取该组织的所有成员的 id 数组
        List<Integer> userIdList = new ArrayList<>();
        for (User user : userList) {
            userIdList.add(user.getId());
        }

        return userIdList;
    }

    @Override
    public List<DetailResponse> listDetailResponse(List<IdAndType> idAndTypes) {
        if(idAndTypes==null) return null;
        List<DetailResponse> detailResponses = new ArrayList<>();
        for(IdAndType idAndType : idAndTypes){
            Integer type = idAndType.getType();
            Integer id = idAndType.getId();
            if(!TypeEnum.isTopic(type)|| ObjectUtils.anyNull(id)){
                continue;
            }
            //获取该话题对应的的图片URL列表
            ArrayList<String> imagesUrl = imageService.getImageList(idAndType);
            if(ObjectUtils.anyNull(imagesUrl)){
                imagesUrl = new ArrayList<>();
            }
            //判断请求哪种话题的详情并执行相应操作
            if(TypeEnum.isActivity(type)){
                Activity activity = activityService.getBaseMapper().selectById(id);
                if(!ObjectUtils.anyNull(activity)){
                    ActivityServiceImpl activityServiceImpl = (ActivityServiceImpl)activityService;
                    ActivityVO activityVO = activityServiceImpl.traverseActivity(activity);
                    String publisherAvatarUrl = imageService.getById(activityVO.getAvatar()).getImageUrl();
                    DetailResponse detailResponse = new DetailResponse();
                    detailResponse.setActivityVO(activityVO);
                    detailResponse.setPublisherAvatarUrl(publisherAvatarUrl);
                    detailResponse.setImagesUrl(imagesUrl);
                    detailResponses.add(detailResponse);
                }
            }
            else if(TypeEnum.isHelp(type)){
                Help help = helpService.getById(id);
                if(!ObjectUtils.anyNull(help)){
                    HelpServiceImpl helpServiceImpl = (HelpServiceImpl)helpService;
                    HelpVO helpVO = helpServiceImpl.traverseHelp(help);
                    String publisherAvatarUrl = imageService.getById(helpVO.getAvatar()).getImageUrl();
                    DetailResponse detailResponse = new DetailResponse();
                    detailResponse.setHelpVO(helpVO);
                    detailResponse.setPublisherAvatarUrl(publisherAvatarUrl);
                    detailResponse.setImagesUrl(imagesUrl);
                    detailResponse.setImagesUrl(imagesUrl);
                    detailResponses.add(detailResponse);
                }
            }
            else {
                Hole hole = holeService.getById(id);
                if(!ObjectUtils.anyNull(hole)) {
                    HoleServiceImpl holeServiceImpl = (HoleServiceImpl)holeService;
                    HoleVO holeVO = holeServiceImpl.traverseHole(hole);
                    String publisherAvatarUrl = imageService.getById(holeVO.getAvatar()).getImageUrl();
                    DetailResponse detailResponse = new DetailResponse();
                    detailResponse.setHoleVO(holeVO);
                    detailResponse.setPublisherAvatarUrl(publisherAvatarUrl);
                    detailResponse.setImagesUrl(imagesUrl);
                    detailResponse.setImagesUrl(imagesUrl);
                    detailResponses.add(detailResponse);
                }
            }
        }
        return detailResponses;
    }
}
