package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.mapper.*;
import com.cohelp.server.model.domain.*;
import com.cohelp.server.model.entity.*;
import com.cohelp.server.model.vo.DetailRemark;
import com.cohelp.server.model.vo.DetailResponse;
import com.cohelp.server.model.vo.RemarkVO;
import com.cohelp.server.service.*;
import com.cohelp.server.utils.PageUtil;
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
import java.time.LocalDateTime;
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
    private ActivityService activityService;
    @Resource
    private AskService askService;
    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private RemarkActivityMapper remarkActivityMapper;
    @Resource
    private HelpService helpService;
    @Resource
    private HelpMapper helpMapper;
    @Resource
    private RemarkHelpMapper remarkHelpMapper;
    @Resource
    private HoleService holeService;
    @Resource
    private HoleMapper holeMapper;
    @Resource
    private RemarkHoleMapper remarkHoleMapper;
    @Resource
    private ImageService imageService;
    @Resource
    private RemarkActivityService remarkActivityService;
    @Resource
    private RemarkHelpService remarkHelpService;
    @Resource
    private RemarkHoleService remarkHoleService;
    @Resource
    private HistoryService historyService;
    @Resource
    private UserService userService;
    @Resource
    private RemarkLikeService remarkLikeService;
    @Override
    public Result getDetail(IdAndType idAndType) {
        //判断参数合法性
        if(ObjectUtils.anyNull(idAndType)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Integer type = idAndType.getType();
        Integer id = idAndType.getId();
        if((!TypeEnum.isTopic(type)&&!type.equals(TypeEnum.ASK.ordinal()))|| ObjectUtils.anyNull(id)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        DetailResponse detailResponse = null;
        //判断请求哪种话题的详情并执行相应操作
        if(TypeEnum.isActivity(type)){
            Activity activity = activityService.getBaseMapper().selectById(id);
            if(!ObjectUtils.anyNull(activity)){
                //增加阅读量
                Integer readNum = activity.getReadNum();
                activity.setReadNum(readNum+1);
                activityService.saveOrUpdate(activity);
                //获取详情
                detailResponse = activityService.getDetailResponse(activity);
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
                } else {
                    oldHistory.setViewTime(LocalDateTime.now());
                    historyService.saveOrUpdate(oldHistory);
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
                //增加阅读量
                Integer readNum = help.getReadNum();
                help.setReadNum(readNum+1);
                helpService.saveOrUpdate(help);
                //获取详情
                detailResponse = helpService.getDetailResponse(help);
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
        else if(TypeEnum.isHole(type)){
            Hole hole = holeService.getById(id);
            if(!ObjectUtils.anyNull(hole)) {
                //增加阅读量
                Integer readNum = hole.getReadNum();
                hole.setReadNum(readNum+1);
                holeService.saveOrUpdate(hole);
                //获取详情
                detailResponse = holeService.getDetailResponse(hole);
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
        }else if(type.equals(TypeEnum.ASK.ordinal())){
            Ask ask = askService.getById(id);
            if(!ObjectUtils.anyNull(ask)) {
                //获取详情
                detailResponse = askService.getDetailResponse(ask);
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
        }else {
            return ResultUtil.returnResult(ERROR_GET_DATA,null,"数据获取失败");
        }
    }

    @Override
    public Result search(SearchRequest searchRequest,Integer page,Integer limit) {
        //判断参数合法性
        if(ObjectUtils.anyNull(searchRequest)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        String key = searchRequest.getKey();
        List<Integer> types = searchRequest.getTypes();
        if(!TypeEnum.isTopic(types)|| StringUtils.isBlank(key)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        User user = UserHolder.getUser();
        if(user==null){
            ResultUtil.fail(ERROR_LOGIN,"未登录");
        }
        Integer teamId = user.getTeamId();
        //查询数据
        String[] keywords = getKeywords(key).split(",");
        List<IdAndType> idAndTypeList = new ArrayList<>();
        if(teamId!=null){
            for(Integer type : types){
                if(TypeEnum.isActivity(type)){
                    List<Activity> activityList = activityMapper.search(teamId, key, keywords);
                    for(Activity activity:activityList){
                        Integer id = activity.getId();
                        idAndTypeList.add(new IdAndType(id,type));
                    }
                }
                else if(TypeEnum.isHelp(type)){
                    List<Help> helpList = helpMapper.search(teamId, key, keywords);
                    for(Help help:helpList) {
                        Integer id = help.getId();
                        idAndTypeList.add(new IdAndType(id,type));
                    }
                }
                else{
                    List<Hole> holeList = holeMapper.search(teamId, key, keywords);
                    for(Hole hole:holeList) {
                        Integer id = hole.getId();
                        idAndTypeList.add(new IdAndType(id,type));
                    }
                }
            }
        }
        List<IdAndType> idAndTypes = PageUtil.pageByList(idAndTypeList, page, limit);
        List<DetailResponse> detailResponses = listDetailResponse(idAndTypes);
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

            //点赞量置0
            remarkActivity.setRemarkLike(0);

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
                //存在浏览记录则将将参与字段置1
                if(oldHistory!=null){
                    oldHistory.setIsInvolved(1);
                    historyService.saveOrUpdate(oldHistory);
                }
                else{//否则插入新浏览记录
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

            //点赞量置0
            remarkHelp.setRemarkLike(0);

            // 敏感词过滤
            String remarkContent = remarkHelp.getRemarkContent();
            if (SensitiveUtils.contains(remarkContent)) {
                return ResultUtil.fail("文本涉及敏感词汇");
            }

            //插入评论
            boolean result = remarkHelpService.save(remarkHelp);

            if(!result) {
                return ResultUtil.fail("评论失败！");
            }
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

            //点赞量置0
            remarkHole.setRemarkLike(0);

            // 敏感词过滤
            String remarkContent = remarkHole.getRemarkContent();
            if (SensitiveUtils.contains(remarkContent)) {
                return ResultUtil.fail("文本涉及敏感词汇");
            }

            //插入评论
            boolean result = remarkHoleService.save(remarkHole);

            if (!result){
                return ResultUtil.fail("评论失败！");
            }
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
        if(ObjectUtils.anyNull(type,id)||!TypeEnum.isTopic(type)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        else if(TypeEnum.isActivity(type)){
            boolean result = remarkActivityService.removeById(id);
            if(!result){
                return ResultUtil.fail("评论删除失败！");
            }else {
                //删除对评论的点赞
                QueryWrapper<RemarkLike> eq = new QueryWrapper<RemarkLike>()
                        .eq("remark_type", type)
                        .eq("remark_id", id);
                remarkLikeService.remove(eq);
                return ResultUtil.ok("评论删除成功！");
            }
        }
        else if(TypeEnum.isHelp(type)){
            boolean result = remarkHelpService.removeById(id);
            if(!result){
                return ResultUtil.fail("评论删除失败！");
            }else {
                //删除对评论的点赞
                QueryWrapper<RemarkLike> eq = new QueryWrapper<RemarkLike>()
                        .eq("remark_type", type)
                        .eq("remark_id", id);
                remarkLikeService.remove(eq);
                return ResultUtil.ok("评论删除成功！");
            }
        }
        else{
            boolean result = remarkHoleService.removeById(id);
            if(!result){
                return ResultUtil.fail("评论删除失败！");
            }else {
                //删除对评论的点赞
                QueryWrapper<RemarkLike> eq = new QueryWrapper<RemarkLike>()
                        .eq("remark_type", type)
                        .eq("remark_id", id);
                remarkLikeService.remove(eq);
                return ResultUtil.ok("评论删除成功！");
            }
        }
    }

    @Override
    public Result<List<RemarkVO>> listRemark(IdAndType idAndType,Integer userId) {
        //判断参数合法性
        if(ObjectUtils.anyNull(idAndType,userId)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Integer type = idAndType.getType();
        Integer id = idAndType.getId();
        ArrayList<RemarkVO> remarkVOS = new ArrayList<>();
        //获取对应话题的评论
        if(ObjectUtils.anyNull(type,id)||!TypeEnum.isTopic(type)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        else if(TypeEnum.isActivity(type)){
            QueryWrapper<RemarkActivity> remarkQueryWrapper = new QueryWrapper<RemarkActivity>().eq("remark_activity_id", id);
            List<RemarkActivity> remarkList = remarkActivityService.list(remarkQueryWrapper);
            for(RemarkActivity remark:remarkList){
                RemarkVO remarkVO = traverseRemark(TypeEnum.REMARKACTIVITY.ordinal(), userId,remark);
                remarkVOS.add(remarkVO);
            }
            return ResultUtil.returnResult(SUCCESS_GET_DATA,remarkVOS,"评论查询成功");
        }
        else if(TypeEnum.isHelp(type)){
            QueryWrapper<RemarkHelp> remarkQueryWrapper = new QueryWrapper<RemarkHelp>().eq("remark_help_id", id);
            List<RemarkHelp> remarkList = remarkHelpService.list(remarkQueryWrapper);
            for(RemarkHelp remark:remarkList){
                RemarkVO remarkVO = traverseRemark(TypeEnum.REMARKHELP.ordinal(), userId,remark);
                remarkVOS.add(remarkVO);
            }
            return ResultUtil.returnResult(SUCCESS_GET_DATA,remarkVOS,"评论查询成功");
        }
        else{
            QueryWrapper<RemarkHole> remarkQueryWrapper = new QueryWrapper<RemarkHole>().eq("remark_hole_id", id);
            List<RemarkHole> remarkList = remarkHoleService.list(remarkQueryWrapper);
            for(RemarkHole remark:remarkList){
                RemarkVO remarkVO = traverseRemark(TypeEnum.REMARKHOLE.ordinal(), userId,remark);
                remarkVOS.add(remarkVO);
            }
            return ResultUtil.returnResult(SUCCESS_GET_DATA,remarkVOS,"评论查询成功");
        }
    }


    public RemarkVO traverseRemark(Integer type,Integer userId,Object remark){
        if(ObjectUtils.anyNull(type,remark)||!TypeEnum.isRemark(type)){
            return null;
        }
        if(TypeEnum.isRemarkActivity(type)){
            RemarkActivity remarkActivity = (RemarkActivity) remark;
            RemarkVO remarkVO = new RemarkVO();
            remarkVO.setId(remarkActivity.getId());
            remarkVO.setTopicId(remarkActivity.getRemarkActivityId());
            remarkVO.setRemarkTargetId(remarkActivity.getRemarkTargetId());
            remarkVO.setTopId(remarkActivity.getTopId());
            remarkVO.setRemarkLike(remarkActivity.getRemarkLike());
            remarkVO.setTargetIsTopic(remarkActivity.getTargetIsActivity());
            User user = userService.getById(remarkActivity.getRemarkOwnerId());
            if(user!=null){
                remarkVO.setRemarkOwnerName(user.getUserName());
                Image image = imageService.getById(user.getAvatar());
                if(image!=null){
                    remarkVO.setRemarkOwnerAvatar(image.getImageUrl());
                }else {
                    remarkVO.setRemarkOwnerAvatar("https://img-blog.csdnimg.cn/img_convert/b573b00bed7126db2c209ed01eb35189.png");
                }
            }else {
                remarkVO.setRemarkOwnerName("用户不存在");
                remarkVO.setRemarkOwnerAvatar("https://img-blog.csdnimg.cn/img_convert/b573b00bed7126db2c209ed01eb35189.png");
            }
            remarkVO.setRemarkContent(remarkActivity.getRemarkContent());
            remarkVO.setRemarkTime(remarkActivity.getRemarkTime());
            remarkVO.setIsLiked(0);
            if(userId!=null){
                //判断用户是否对该话题点赞
                QueryWrapper<RemarkLike> eq = new QueryWrapper<RemarkLike>()
                        .eq("user_id", userId)
                        .eq("remark_type", TypeEnum.ACTIVITY.ordinal())
                        .eq("remark_id", remarkActivity.getId())
                        .eq("is_liked", 1);
                RemarkLike one = remarkLikeService.getOne(eq);
                if (one!=null){
                    remarkVO.setIsLiked(1);
                }else {
                    remarkVO.setIsLiked(0);
                }
            }
            return remarkVO;
        }else if(TypeEnum.isRemarkHelp(type)){
            RemarkHelp remarkHelp = (RemarkHelp) remark;
            RemarkVO remarkVO = new RemarkVO();
            remarkVO.setId(remarkHelp.getId());
            remarkVO.setTopicId(remarkHelp.getRemarkHelpId());
            remarkVO.setRemarkTargetId(remarkHelp.getRemarkTargetId());
            remarkVO.setTopId(remarkHelp.getTopId());
            remarkVO.setRemarkLike(remarkHelp.getRemarkLike());
            remarkVO.setTargetIsTopic(remarkHelp.getTargetIsHelp());
            User user = userService.getById(remarkHelp.getRemarkOwnerId());
            if(user!=null){
                remarkVO.setRemarkOwnerName(user.getUserName());
                Image image = imageService.getById(user.getAvatar());
                if(image!=null){
                    remarkVO.setRemarkOwnerAvatar(image.getImageUrl());
                }else {
                    remarkVO.setRemarkOwnerAvatar("https://img-blog.csdnimg.cn/img_convert/b573b00bed7126db2c209ed01eb35189.png");
                }

            }else {
                remarkVO.setRemarkOwnerName("用户不存在");
                remarkVO.setRemarkOwnerAvatar("https://img-blog.csdnimg.cn/img_convert/b573b00bed7126db2c209ed01eb35189.png");
            }
            remarkVO.setRemarkContent(remarkHelp.getRemarkContent());
            remarkVO.setRemarkTime(remarkHelp.getRemarkTime());
            remarkVO.setIsLiked(0);
            if(userId!=null){
                //判断用户是否对该话题点赞
                QueryWrapper<RemarkLike> eq = new QueryWrapper<RemarkLike>()
                        .eq("user_id", userId)
                        .eq("remark_type", TypeEnum.HELP.ordinal())
                        .eq("remark_id", remarkHelp.getId())
                        .eq("is_liked", 1);
                RemarkLike one = remarkLikeService.getOne(eq);
                if (one!=null){
                    remarkVO.setIsLiked(1);
                }else {
                    remarkVO.setIsLiked(0);
                }
            }
            return remarkVO;
        }else {
            RemarkHole remarkHole = (RemarkHole) remark;
            RemarkVO remarkVO = new RemarkVO();
            remarkVO.setId(remarkHole.getId());
            remarkVO.setTopicId(remarkHole.getRemarkHoleId());
            remarkVO.setRemarkTargetId(remarkHole.getRemarkTargetId());
            remarkVO.setTopId(remarkHole.getTopId());
            remarkVO.setRemarkLike(remarkHole.getRemarkLike());
            remarkVO.setTargetIsTopic(remarkHole.getTargetIsHole());
            User user = userService.getById(remarkHole.getRemarkOwnerId());
            if(user!=null){
                remarkVO.setRemarkOwnerName(user.getUserName());
                Image image = imageService.getById(user.getAvatar());
                if(image!=null){
                    remarkVO.setRemarkOwnerAvatar(image.getImageUrl());
                }else {
                    remarkVO.setRemarkOwnerAvatar("https://img-blog.csdnimg.cn/img_convert/b573b00bed7126db2c209ed01eb35189.png");
                }

            }else {
                remarkVO.setRemarkOwnerName("用户不存在");
                remarkVO.setRemarkOwnerAvatar("https://img-blog.csdnimg.cn/img_convert/b573b00bed7126db2c209ed01eb35189.png");
            }
            remarkVO.setRemarkContent(remarkHole.getRemarkContent());
            remarkVO.setRemarkTime(remarkHole.getRemarkTime());
            remarkVO.setIsLiked(0);
            if(userId!=null){
                //判断用户是否对该话题点赞
                QueryWrapper<RemarkLike> eq = new QueryWrapper<RemarkLike>()
                        .eq("user_id", userId)
                        .eq("remark_type", TypeEnum.HOLE.ordinal())
                        .eq("remark_id", remarkHole.getId())
                        .eq("is_liked", 1);
                RemarkLike one = remarkLikeService.getOne(eq);
                if (one!=null){
                    remarkVO.setIsLiked(1);
                }else {
                    remarkVO.setIsLiked(0);
                }
            }
            return remarkVO;
        }
    }

    /**
     * @param key 搜索关键词
     * @return java.lang.String 分词词组(,拼接)
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
    public PageResponse<DetailResponse> listTopics(Integer page, Integer limit, Integer teamId, Integer type) {
        ArrayList<DetailResponse> detailResponses = new ArrayList<>();
        ArrayList<IdAndType> idAndTypes = new ArrayList<>();
        Long aLong = null;
        //判断参数合法性
        if(ObjectUtils.anyNull(teamId,type)||!TypeEnum.isTopic(type)){
            return new PageResponse<DetailResponse>(detailResponses,new Long(0));
        }
        //针对不同类型的话题进行相应查询
        if(TypeEnum.isActivity(type)){
            Page<Activity> activityPage = activityMapper.selectPage(new Page<Activity>(page, limit),
                    new QueryWrapper<Activity>().eq("team_id", teamId));
            List<Activity> records = activityPage.getRecords();
            aLong = new Long(activityPage.getTotal());
            for(Activity activity:records){
                Integer id = activity.getId();
                idAndTypes.add(new IdAndType(id,type));
            }
        }
        else if(TypeEnum.isHelp(type)){
            Page<Help> helpPage = helpMapper.selectPage(new Page<Help>(page, limit),
                    new QueryWrapper<Help>().eq("team_id", teamId));
            List<Help> records = helpPage.getRecords();
            aLong = new Long(helpPage.getTotal());
            for(Help help:records){
                Integer id = help.getId();
                idAndTypes.add(new IdAndType(id,type));
            }
        }
        else{
            Page<Hole> holePage = holeMapper.selectPage(new Page<Hole>(page, limit),
                    new QueryWrapper<Hole>().eq("team_id", teamId));
            List<Hole> records = holePage.getRecords();
            aLong = new Long(holePage.getTotal());
            for(Hole hole:records){
                Integer id = hole.getId();
                idAndTypes.add(new IdAndType(id,type));
            }
        }
        //返回查询结果
        List<DetailResponse> list = listDetailResponse(idAndTypes);
        return new PageResponse<DetailResponse>(list,aLong);
    }

    @Override
    public PageResponse<DetailResponse> searchTopics(Integer page,Integer limit,Integer teamId,Integer type, String key) {
        ArrayList<DetailResponse> detailResponses = new ArrayList<>();
        ArrayList<IdAndType> idAndTypes = new ArrayList<>();
        Long aLong = null;
        //判断参数合法性
        if(ObjectUtils.anyNull(teamId,type,key)||!TypeEnum.isTopic(type)||key.equals("")){
            return new PageResponse<DetailResponse>(detailResponses,new Long(0));
        }
        //针对不同类型的话题进行相应搜索
        if(TypeEnum.isActivity(type)){
            Page<Activity> activityPage = activityMapper.selectPage(new Page<Activity>(page, limit),
                    new QueryWrapper<Activity>().eq("team_id", teamId)
                            .and(o->o.like("activity_title",key).or().like("activity_label",key)));
            List<Activity> records = activityPage.getRecords();
            aLong = new Long(activityPage.getTotal());
            for(Activity activity:records){
                Integer id = activity.getId();
                idAndTypes.add(new IdAndType(id,type));
            }
        }
        else if(TypeEnum.isHelp(type)){
            Page<Help> helpPage = helpMapper.selectPage(new Page<Help>(page, limit),
                    new QueryWrapper<Help>().eq("team_id", teamId)
                            .and(o->o.like("help_title",key).or().like("help_label",key)));
            List<Help> records = helpPage.getRecords();
            aLong = new Long(helpPage.getTotal());
            for(Help help:records){
                Integer id = help.getId();
                idAndTypes.add(new IdAndType(id,type));
            }
        }
        else{
            Page<Hole> holePage = holeMapper.selectPage(new Page<Hole>(page, limit),
                    new QueryWrapper<Hole>().eq("team_id", teamId)
                            .and(o->o.like("hole_title",key).or().like("hole_label",key)));
            List<Hole> records = holePage.getRecords();
            aLong = new Long(holePage.getTotal());
            for(Hole hole:records){
                Integer id = hole.getId();
                idAndTypes.add(new IdAndType(id,type));
            }
        }
        //返回搜索结果
        List<DetailResponse> list = listDetailResponse(idAndTypes);
        return new PageResponse<DetailResponse>(list,aLong);
    }

    @Override
    public String changeTopic(Integer type, Activity activity, Help help, Hole hole) {
        //检验参数合法性
        if(ObjectUtils.anyNull(type)||!TypeEnum.isTopic(type)){
            return "类型不合法！";
        }
        //针对不同类型的话题进行相应更改
        if(TypeEnum.isActivity(type)){
            if(ObjectUtils.anyNull(activity)){
                return "参数数据为空无法修改！";
            }else {
                boolean b = activityService.saveOrUpdate(activity);
                if(!b){
                    return "修改失败！";
                }else {
                    return "修改成功！";
                }
            }
        }
        else if(TypeEnum.isHelp(type)){
            if(ObjectUtils.anyNull(help)){
                return "参数数据为空无法修改！";
            }else {
                boolean b = helpService.saveOrUpdate(help);
                if(!b){
                    return "修改失败！";
                }else {
                    return "修改成功！";
                }
            }
        }
        else{
            if(ObjectUtils.anyNull(hole)){
                return "参数数据为空无法修改！";
            }else {
                boolean b = holeService.saveOrUpdate(hole);
                if(!b){
                    return "修改失败！";
                }else {
                    return "修改成功！";
                }
            }
        }
    }

    @Override
    public PageResponse<DetailRemark> listRemarks(Integer page, Integer limit, Integer teamId, Integer type) {
        ArrayList<DetailRemark> detailRemarks = new ArrayList<>();
        Long aLong = new Long(0);
        //判断参数合法性
        if(ObjectUtils.anyNull(teamId,type)||!TypeEnum.isRemark(type)){
            return new PageResponse<DetailRemark>(detailRemarks,new Long(0));
        }
        //针对不同类型的评论进行相应查询
        if(TypeEnum.isRemarkActivity(type)){
            List<Activity> records = activityService.list(new QueryWrapper<Activity>().eq("team_id", teamId));
            if ((records!=null)){
                for(Activity activity:records){
                    Integer id = activity.getId();
                    Page<RemarkActivity> remarks = remarkActivityMapper.selectPage(new Page<RemarkActivity>(page, limit),
                            new QueryWrapper<RemarkActivity>().eq("remark_activity_id", id));
                    List<RemarkActivity> remarksRecords = remarks.getRecords();
                    aLong += remarks.getTotal();
                    for(RemarkActivity remarkActivity:remarksRecords){
                        DetailRemark detailRemark = new DetailRemark();
                        User user = userService.getById(remarkActivity.getRemarkOwnerId());
                        if(user!=null){
                            detailRemark.setOwnerName(user.getUserName());
                        }
                        detailRemark.setTopicTitle(activity.getActivityTitle());
                        detailRemark.setType(type);
                        detailRemark.setId(remarkActivity.getId());
                        detailRemark.setContent(remarkActivity.getRemarkContent());
                        detailRemark.setRemarkTime(remarkActivity.getRemarkTime());
                        detailRemark.setLike(remarkActivity.getRemarkLike());
                        detailRemarks.add(detailRemark);
                    }
                }
            }
        }
        else if(TypeEnum.isRemarkHelp(type)){
            List<Help> records = helpService.list(new QueryWrapper<Help>().eq("team_id", teamId));
            if ((records!=null)){
                for(Help help:records){
                    Integer id = help.getId();
                    Page<RemarkHelp> remarks = remarkHelpMapper.selectPage(new Page<RemarkHelp>(page, limit),
                            new QueryWrapper<RemarkHelp>().eq("remark_help_id", id));
                    List<RemarkHelp> remarksRecords = remarks.getRecords();
                    aLong += remarks.getTotal();
                    for(RemarkHelp remarkHelp:remarksRecords){
                        DetailRemark detailRemark = new DetailRemark();
                        User user = userService.getById(remarkHelp.getRemarkOwnerId());
                        if(user!=null){
                            detailRemark.setOwnerName(user.getUserName());
                        }
                        detailRemark.setTopicTitle(help.getHelpTitle());
                        detailRemark.setType(type);
                        detailRemark.setId(remarkHelp.getId());
                        detailRemark.setContent(remarkHelp.getRemarkContent());
                        detailRemark.setRemarkTime(remarkHelp.getRemarkTime());
                        detailRemark.setLike(remarkHelp.getRemarkLike());
                        detailRemarks.add(detailRemark);
                    }
                }
            }
        }
        else{
            List<Hole> records = holeService.list(new QueryWrapper<Hole>().eq("team_id", teamId));
            if ((records!=null)){
                for(Hole hole:records){
                    Integer id = hole.getId();
                    Page<RemarkHole> remarks = remarkHoleMapper.selectPage(new Page<RemarkHole>(page, limit),
                            new QueryWrapper<RemarkHole>().eq("remark_hole_id", id));
                    List<RemarkHole> remarksRecords = remarks.getRecords();
                    aLong += remarks.getTotal();
                    for(RemarkHole remarkHole:remarksRecords){
                        DetailRemark detailRemark = new DetailRemark();
                        User user = userService.getById(remarkHole.getRemarkOwnerId());
                        if(user!=null){
                            detailRemark.setOwnerName(user.getUserName());
                        }
                        detailRemark.setTopicTitle(hole.getHoleTitle());
                        detailRemark.setType(type);
                        detailRemark.setId(remarkHole.getId());
                        detailRemark.setContent(remarkHole.getRemarkContent());
                        detailRemark.setRemarkTime(remarkHole.getRemarkTime());
                        detailRemark.setLike(remarkHole.getRemarkLike());
                        detailRemarks.add(detailRemark);
                    }
                }
            }
        }
        //返回评论
        return new PageResponse<DetailRemark>(detailRemarks,aLong);
    }

    @Override
    public PageResponse<DetailRemark> searchRemarks(Integer page, Integer limit, Integer teamId, Integer type, String key) {
        ArrayList<DetailRemark> detailRemarks = new ArrayList<>();
        Long aLong = new Long(0);
        //判断参数合法性
        if(ObjectUtils.anyNull(teamId,type,key)||!TypeEnum.isRemark(type)||key.equals("")){
            return new PageResponse<DetailRemark>(detailRemarks,new Long(0));
        }
        //针对不同类型的评论进行相应查询
        if(TypeEnum.isRemarkActivity(type)){
            List<Activity> records = activityService.list(new QueryWrapper<Activity>().eq("team_id", teamId));
            if ((records!=null)){
                for(Activity activity:records){
                    Integer id = activity.getId();
                    Page<RemarkActivity> remarks = remarkActivityMapper.selectPage(new Page<RemarkActivity>(page, limit),
                            new QueryWrapper<RemarkActivity>().eq("remark_activity_id", id).like("remark_content",key));
                    List<RemarkActivity> remarksRecords = remarks.getRecords();
                    aLong += remarks.getTotal();
                    for(RemarkActivity remarkActivity:remarksRecords){
                        DetailRemark detailRemark = new DetailRemark();
                        User user = userService.getById(remarkActivity.getRemarkOwnerId());
                        if(user!=null){
                            detailRemark.setOwnerName(user.getUserName());
                        }
                        detailRemark.setTopicTitle(activity.getActivityTitle());
                        detailRemark.setType(type);
                        detailRemark.setId(remarkActivity.getId());
                        detailRemark.setContent(remarkActivity.getRemarkContent());
                        detailRemark.setRemarkTime(remarkActivity.getRemarkTime());
                        detailRemark.setLike(remarkActivity.getRemarkLike());
                        detailRemarks.add(detailRemark);
                    }
                }
            }
        }
        else if(TypeEnum.isRemarkHelp(type)){
            List<Help> records = helpService.list(new QueryWrapper<Help>().eq("team_id", teamId));
            if ((records!=null)){
                for(Help help:records){
                    Integer id = help.getId();
                    Page<RemarkHelp> remarks = remarkHelpMapper.selectPage(new Page<RemarkHelp>(page, limit),
                            new QueryWrapper<RemarkHelp>().eq("remark_help_id", id).like("remark_content",key));
                    List<RemarkHelp> remarksRecords = remarks.getRecords();
                    aLong += remarks.getTotal();
                    for(RemarkHelp remarkHelp:remarksRecords){
                        DetailRemark detailRemark = new DetailRemark();
                        User user = userService.getById(remarkHelp.getRemarkOwnerId());
                        if(user!=null){
                            detailRemark.setOwnerName(user.getUserName());
                        }
                        detailRemark.setTopicTitle(help.getHelpTitle());
                        detailRemark.setType(type);
                        detailRemark.setId(remarkHelp.getId());
                        detailRemark.setContent(remarkHelp.getRemarkContent());
                        detailRemark.setRemarkTime(remarkHelp.getRemarkTime());
                        detailRemark.setLike(remarkHelp.getRemarkLike());
                        detailRemarks.add(detailRemark);
                    }
                }
            }
        }
        else{
            List<Hole> records = holeService.list(new QueryWrapper<Hole>().eq("team_id", teamId));
            if ((records!=null)){
                for(Hole hole:records){
                    Integer id = hole.getId();
                    Page<RemarkHole> remarks = remarkHoleMapper.selectPage(new Page<RemarkHole>(page, limit),
                            new QueryWrapper<RemarkHole>().eq("remark_hole_id", id).like("remark_content",key));
                    List<RemarkHole> remarksRecords = remarks.getRecords();
                    aLong += remarks.getTotal();
                    for(RemarkHole remarkHole:remarksRecords){
                        DetailRemark detailRemark = new DetailRemark();
                        User user = userService.getById(remarkHole.getRemarkOwnerId());
                        if(user!=null){
                            detailRemark.setOwnerName(user.getUserName());
                        }
                        detailRemark.setTopicTitle(hole.getHoleTitle());
                        detailRemark.setType(type);
                        detailRemark.setId(remarkHole.getId());
                        detailRemark.setContent(remarkHole.getRemarkContent());
                        detailRemark.setRemarkTime(remarkHole.getRemarkTime());
                        detailRemark.setLike(remarkHole.getRemarkLike());
                        detailRemarks.add(detailRemark);
                    }
                }
            }
        }
        //返回评论
        return new PageResponse<DetailRemark>(detailRemarks,aLong);
    }

    @Override
    public String deleteRemark(Integer type, Integer id) {
        //检验参数合法性
        if(ObjectUtils.anyNull(type,id)||!TypeEnum.isRemark(type)){
            return "参数不合法！";
        }
        //针对不同类型的话题进行相应删除
        if(TypeEnum.isRemarkActivity(type)){
            RemarkActivity byId = remarkActivityService.getById(id);
            if(byId==null){
                return "删除成功！";
            }
            boolean b = remarkActivityService.removeById(id);
            if(!b){
                return "删除失败！";
            }else {
                return "删除成功！";
            }
        }
        else if(TypeEnum.isRemarkHelp(type)){
            RemarkHelp byId = remarkHelpService.getById(id);
            if(byId==null){
                return "删除成功！";
            }
            boolean b = remarkHelpService.removeById(id);
            if(!b){
                return "删除失败！";
            }else {
                return "删除成功！";
            }
        }
        else{
            RemarkHole byId = remarkHoleService.getById(id);
            if(byId==null){
                return "删除成功！";
            }
            boolean b = remarkHoleService.removeById(id);
            if(!b){
                return "删除失败！";
            }else {
                return "删除成功！";
            }
        }
    }

    @Override
    public List<Integer> getUserIdList(Integer teamId) {
        if(teamId==null) return null;

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
    public DetailRemark  getDetailRemark(IdAndType idAndType) {
        if(idAndType==null){
            return null;
        }
        Integer type = idAndType.getType();
        Integer id = idAndType.getId();
        if(!TypeEnum.isRemark(type)|| ObjectUtils.anyNull(id)){
            return null;
        }
        DetailRemark detailRemark = new DetailRemark();
        //判断请求哪种话题的评论并执行相应操作
        if(TypeEnum.isRemarkActivity(type)){
            RemarkActivity remarkActivity = remarkActivityMapper.selectById(id);
            if (remarkActivity==null){
                return null;
            }
            User user = userService.getById(remarkActivity.getRemarkOwnerId());
            if(user!=null){
                detailRemark.setOwnerName(user.getUserName());
            }
            Activity activity = activityService.getById(remarkActivity.getRemarkActivityId());
            if(activity!=null){
                detailRemark.setTopicTitle(activity.getActivityTitle());
            }
            detailRemark.setType(type);
            detailRemark.setId(remarkActivity.getId());
            detailRemark.setContent(remarkActivity.getRemarkContent());
            detailRemark.setRemarkTime(remarkActivity.getRemarkTime());
            detailRemark.setLike(remarkActivity.getRemarkLike());
        }
        else if(TypeEnum.isRemarkHelp(type)){
            RemarkHelp remarkHelp = remarkHelpMapper.selectById(id);
            if (remarkHelp==null){
                return null;
            }
            User user = userService.getById(remarkHelp.getRemarkOwnerId());
            if(user!=null){
                detailRemark.setOwnerName(user.getUserName());
            }
            Help help = helpService.getById(remarkHelp.getRemarkHelpId());
            if(help!=null){
                detailRemark.setTopicTitle(help.getHelpTitle());
            }
            detailRemark.setType(type);
            detailRemark.setId(remarkHelp.getId());
            detailRemark.setContent(remarkHelp.getRemarkContent());
            detailRemark.setRemarkTime(remarkHelp.getRemarkTime());
            detailRemark.setLike(remarkHelp.getRemarkLike());
        }
        else {
            RemarkHole remarkHole = remarkHoleMapper.selectById(id);
            if (remarkHole==null){
                return null;
            }
            User user = userService.getById(remarkHole.getRemarkOwnerId());
            if(user!=null){
                detailRemark.setOwnerName(user.getUserName());
            }
            Hole hole = holeService.getById(remarkHole.getRemarkHoleId());
            if(hole!=null){
                detailRemark.setTopicTitle(hole.getHoleTitle());
            }
            detailRemark.setType(type);
            detailRemark.setId(remarkHole.getId());
            detailRemark.setContent(remarkHole.getRemarkContent());
            detailRemark.setRemarkTime(remarkHole.getRemarkTime());
            detailRemark.setLike(remarkHole.getRemarkLike());
        }
        return detailRemark;
    }
    @Override
    public DetailResponse getDetailResponse(IdAndType idAndType) {
        if(idAndType==null){
            return null;
        }
        Integer type = idAndType.getType();
        Integer id = idAndType.getId();
        if((!TypeEnum.isTopic(type)&&!type.equals(TypeEnum.ASK.ordinal()))|| ObjectUtils.anyNull(id)){
            return null;
        }
        //判断请求哪种话题的详情并执行相应操作
        if(TypeEnum.isActivity(type)){
            Activity activity = activityService.getBaseMapper().selectById(id);
            if(!ObjectUtils.anyNull(activity)){
                return activityService.getDetailResponse(activity);
            }else{
                return null;
            }
        }else if(TypeEnum.isHelp(type)){
            Help help = helpService.getById(id);
            if(!ObjectUtils.anyNull(help)){
                return helpService.getDetailResponse(help);
            }else {
                return null;
            }
        }else if(TypeEnum.isHole(type)){
            Hole hole = holeService.getById(id);
            if(!ObjectUtils.anyNull(hole)) {
                return holeService.getDetailResponse(hole);
            }else {
                return null;
            }
        }else if(type.equals(TypeEnum.ASK.ordinal())){
            Ask ask = askService.getById(id);
            if(ask!=null){
                return askService.getDetailResponse(ask);
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

    @Override
    public List<DetailResponse>  listDetailResponse(List<IdAndType> idAndTypes) {
        if(idAndTypes==null){
            return null;
        }
        List<DetailResponse> detailResponses = new ArrayList<>();
        for(IdAndType idAndType : idAndTypes) {
            DetailResponse detailResponse = getDetailResponse(idAndType);
            if (detailResponse == null) {
                continue;
            }
            detailResponses.add(detailResponse);
        }
        return detailResponses;
    }
    @Override
    public TopicNumber getCurrentDayPublish(Integer teamId) {
        if(teamId==null){
            return null;
        }
        //获取组织成员用户Id
        List<Integer> userIdList = getUserIdList(teamId);
        //查询话题发布数
        TopicNumber topicNumber = new TopicNumber();
        long activityCurrentDayPublish = 0;
        long helpCurrentDayPublish = 0;
        long holeCurrentDayPublish = 0;
        for(Integer id : userIdList){
            //查当天活动发布数
            activityCurrentDayPublish += activityMapper.getCurrentDayPublish(id);
            //查当天互助发布数
            helpCurrentDayPublish += helpMapper.getCurrentDayPublish(id);
            //查当天树洞发布数
            holeCurrentDayPublish += holeMapper.getCurrentDayPublish(id);
        }
        //保存当天活动发布数
        ArrayList<Integer> activityNumber = new ArrayList<>();
        activityNumber.add(new Long(activityCurrentDayPublish).intValue());
        topicNumber.setActivityNumber(activityNumber);
        //保存当天互助发布数
        ArrayList<Integer> helpNumber = new ArrayList<>();
        helpNumber.add(new Long(helpCurrentDayPublish).intValue());
        topicNumber.setHelpNumber(helpNumber);
        //保存当天树洞发布数
        ArrayList<Integer> holeNumber = new ArrayList<>();
        holeNumber.add(new Long(holeCurrentDayPublish).intValue());
        topicNumber.setHoleNumber(holeNumber);
        return topicNumber;
    }

    @Override
    public TopicNumber getCurrentYearPublish(Integer teamId) {
        if(teamId==null){
            return null;
        }
        //获取组织成员用户Id
        List<Integer> userIdList = getUserIdList(teamId);
        //查询话题发布数
        TopicNumber topicNumber = new TopicNumber();
        ArrayList<Integer> activityNumber = new ArrayList<>();
        ArrayList<Integer> helpNumber = new ArrayList<>();
        ArrayList<Integer> holeNumber = new ArrayList<>();
        for(int month = 1;month <= 12;month++){
            long activityPublish = 0;
            long helpPublish = 0;
            long holePublish = 0;
            for(Integer id : userIdList){
                //查活动发布数
                activityPublish += activityMapper.getMonthPublish(id,month);
                //查互助发布数
                helpPublish += helpMapper.getMonthPublish(id,month);
                //查树洞发布数
                holePublish += holeMapper.getMonthPublish(id,month);
            }
            //依次保存本年度第month月的话题发布数
            activityNumber.add(new Long(activityPublish).intValue());
            helpNumber.add(new Long(helpPublish).intValue());
            holeNumber.add(new Long(holePublish).intValue());
        }
        topicNumber.setActivityNumber(activityNumber);
        topicNumber.setHelpNumber(helpNumber);
        topicNumber.setHoleNumber(holeNumber);
        return topicNumber;
    }
}
