package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.mapper.CourseMapper;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.*;
import com.cohelp.server.model.vo.AskVO;
import com.cohelp.server.model.vo.CourseVO;
import com.cohelp.server.service.*;
import com.cohelp.server.utils.FileUtils;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.SensitiveUtils;
import com.cohelp.server.utils.UserHolder;
import com.google.gson.Gson;
import com.ruibty.nsfw.NsfwService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cohelp.server.constant.StatusCode.*;
import static com.cohelp.server.constant.TypeEnum.ANSWER;
import static com.cohelp.server.constant.TypeEnum.ASK;

/**
* @author jianping5
* @description 针对表【course】的数据库操作Service实现
* @createDate 2023-03-01 14:31:07
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService{

    @Resource
    private Gson gson;

    @Value("${threshold}")
    private String threshold;

    @Resource
    private FileUtils fileUtils;

    @Resource
    private HistoryService historyService;

    @Autowired
    private NsfwService nsfwService;

    @Value("${spring.tengxun.url}")
    private String path;

    @Resource
    private SelectionService selectionService;

    @Resource
    private CourseService courseService;

    @Resource
    private AskService askService;

    @Resource
    private UserService userService;

    @Resource
    private ImageService imageService;

    @Resource
    private AnswerService answerService;

    @Resource
    private DiscussionLikeService discussionLikeService;

    @Resource
    private CollectService collectService;

    @Override
    public Result<List<CourseVO>> listCourse(String semester) {
        // 获取当前登录用户的 id
        User user = UserHolder.getUser();
        int userId = user.getId();

        // 学生选择学年，默认当前学年（最新学年，学年可以根据字符大小排序）
        QueryWrapper<Selection> selectionQueryWrapper = new QueryWrapper<>();
        selectionQueryWrapper.eq("student_id", userId);
        List<Selection> list = selectionService.list(selectionQueryWrapper);
        Set<String> semesterSet = list.stream().map(item -> item.getSemester()).collect(Collectors.toSet());
        // 将 Set 转换为 List，便于获取元素
        List<String> semesterList = semesterSet.stream().collect(Collectors.toList());

        // 若参数为空，则默认为当前学年
        if (StringUtils.isBlank(semester)) {
            String currentSemester = semesterList.get(semesterList.size()-1);
            semester = currentSemester;
        }

        // 根据学年去查询所选课程（从选课表中查）
        QueryWrapper<Selection> selectionQueryWrapper1 = new QueryWrapper<>();
        selectionQueryWrapper1.eq("semester", semester);
        List<Selection> selectionList = selectionService.list(selectionQueryWrapper1).stream().collect(Collectors.toList());

        // 遍历选课集合，渲染课程 VO
        List<CourseVO> courseVOList = new ArrayList<>();

        for (Selection selection : selectionList) {
            CourseVO courseVO = new CourseVO();
            Course course = courseService.getById(selection.getCourseId());
            BeanUtils.copyProperties(course, courseVO);
            courseVO.setSemester(selection.getSemester());
            courseVOList.add(courseVO);
        }


        return ResultUtil.ok(courseVOList);
    }

    @Override
    public Result<List<AskVO>> listAsk(Integer page, Integer limit, Integer courseId, Integer condition) {
        // 校验参数
        if (courseId == null || courseId <= 0) {
            return ResultUtil.fail("参数错误");
        }

        // 根据当前课程去查询该课程下的所有提问（从提问表中查询）
        QueryWrapper<Ask> askQueryWrapper = new QueryWrapper<>();
        askQueryWrapper.eq("course_id", courseId);
        Page<Ask> askPage = new Page<>();

        // 默认排序
        if (condition == 0) {
            askPage = askService.page(new Page<>((page - 1) * limit, limit), askQueryWrapper);
        }

        // 按热度排序
        if (condition == 1) {
            askQueryWrapper.orderByDesc("like_count");
            askPage = askService.page(new Page<>((page - 1) * limit, limit), askQueryWrapper);
        }
        // 按时间降序排列
        if (condition == 2) {
            askQueryWrapper.orderByDesc("publish_time");
            askPage = askService.page(new Page<>((page - 1) * limit, limit), askQueryWrapper);
        }

        List<Ask> askList = askPage.getRecords();

        // 联合用户表，将学生相关信息以及提问信息渲染到 AskVO 上
        ArrayList<AskVO> askVOList = new ArrayList<>();

        for (Ask ask : askList) {
            AskVO askVO = new AskVO();
            BeanUtils.copyProperties(ask, askVO);
            // 获取用户昵称
            User user = userService.getById(ask.getPublisherId());
            askVO.setUserName(user.getUserName());
            // 获取用户头像
            Image image = imageService.getById(user.getAvatar());
            askVO.setAvatarUrl(image.getImageUrl());

            askVOList.add(askVO);
        }

        return ResultUtil.ok(askVOList);
    }

    @Override
    public Result<Boolean> publishAsk(String askJson, MultipartFile[] files) {
        if (StringUtils.isBlank(askJson)) {
            return ResultUtil.fail(ERROR_PARAMS, "请求参数错误");
        }
        Ask ask = gson.fromJson(askJson, Ask.class);

        // 判断是否包含敏感词
        String askQuestion = ask.getQuestion();
        if (StringUtils.isBlank(askQuestion)) {
            return ResultUtil.fail("题目未填写");
        }
        if (SensitiveUtils.contains(askQuestion)) {
            return ResultUtil.fail("文本涉及敏感词汇");
        }

        // 获取登录id，并设置当前用户 id 到 ask 中
        User user = UserHolder.getUser();
        int userId = user.getId();
        ask.setPublisherId(userId);

        boolean save = askService.save(ask);
        if (!save) {
            return ResultUtil.fail(ERROR_SAVE_HELP, "题目发布失败");
        }
        // 上传图片获取url
        ArrayList<String> fileNameList = new ArrayList<>();
        if (files != null && files.length > 0 && !"".equals(files[0].getOriginalFilename())) {
            for (MultipartFile file : files) {
                //图片检测，当该图片的预测值超过阈值则忽略上传
                try {
                    byte[] bytes = file.getBytes();
                    float prediction = nsfwService.getPrediction(bytes);
                    if(prediction>new Float(threshold)){
                        continue;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String fileName = fileUtils.fileUpload(file);
                if (StringUtils.isBlank(fileName)) {
                    return ResultUtil.fail("图片上传异常");
                }
                String url = path + fileName;
                fileNameList.add(fileName);
                Image image = new Image();
                image.setImageType(ASK.ordinal());
                image.setImageSrcId(ask.getId());
                image.setImageUrl(url);
                boolean save1 = imageService.save(image);
                if (!save1) {
                    return ResultUtil.fail(ERROR_SAVE_IMAGE, "图片保存失败");
                }
            }
        }
        return ResultUtil.ok(true);
    }

    @Override
    public Result<Boolean> publishAnswer(String answerJson, MultipartFile[] files) {
        if (StringUtils.isBlank(answerJson)) {
            return ResultUtil.fail(ERROR_PARAMS, "请求参数错误");
        }
        Answer answer = gson.fromJson(answerJson, Answer.class);

        // 判断是否包含敏感词
        String content = answer.getContent();
        if (StringUtils.isBlank(content)) {
            return ResultUtil.fail("答案未填写");
        }
        if (SensitiveUtils.contains(content)) {
            return ResultUtil.fail("文本涉及敏感词汇");
        }

        // 获取登录id，并设置当前用户 id 到 ask 中
        User user = UserHolder.getUser();
        int userId = user.getId();
        answer.setPublisherId(userId);

        boolean save = answerService.save(answer);
        if (!save) {
            return ResultUtil.fail(ERROR_SAVE_HELP, "题目发布失败");
        }
        // 上传图片获取url
        ArrayList<String> fileNameList = new ArrayList<>();
        if (files != null && files.length > 0 && !"".equals(files[0].getOriginalFilename())) {
            for (MultipartFile file : files) {
                //图片检测，当该图片的预测值超过阈值则忽略上传
                try {
                    byte[] bytes = file.getBytes();
                    float prediction = nsfwService.getPrediction(bytes);
                    if(prediction>new Float(threshold)){
                        continue;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String fileName = fileUtils.fileUpload(file);
                if (StringUtils.isBlank(fileName)) {
                    return ResultUtil.fail("图片上传异常");
                }
                String url = path + fileName;
                fileNameList.add(fileName);
                Image image = new Image();
                image.setImageType(ANSWER.ordinal());
                image.setImageSrcId(answer.getId());
                image.setImageUrl(url);
                boolean save1 = imageService.save(image);
                if (!save1) {
                    return ResultUtil.fail(ERROR_SAVE_IMAGE, "图片保存失败");
                }
            }
        }
        QueryWrapper<History> historyQueryWrapper = new QueryWrapper<History>()
                .eq("user_id",UserHolder.getUser().getId())
                .eq("topic_type", ASK.ordinal())
                .eq("topic_id",answer.getAskId());
        History oldHistory = historyService.getOne(historyQueryWrapper);
        //存在浏览记录则将将参与字段置1
        if(oldHistory!=null){
            oldHistory.setIsInvolved(1);
            historyService.saveOrUpdate(oldHistory);
        }
        else{//否则插入新浏览记录
            History history = new History();
            history.setUserId(UserHolder.getUser().getId());
            history.setTopicType(ASK.ordinal());
            history.setTopicId(answer.getAskId());
            history.setIsInvolved(1);
            historyService.saveOrUpdate(history);
        }
        return ResultUtil.ok(true);
    }

    @Override
    public Result likeQA(Integer type, Integer id) {
        User loginUser = UserHolder.getUser();

        // 点赞题目
        if (type == ASK.ordinal()) {
            Ask ask = askService.getById(id);

            // 获取当前用户对该主题的点赞记录
            if (loginUser == null) {
                return ResultUtil.fail(INTERCEPTOR_LOGIN, "未登录");
            }
            Integer loginUserId = loginUser.getId();
            QueryWrapper<DiscussionLike> discussionLikeQueryWrapper = new QueryWrapper<>();
            discussionLikeQueryWrapper.eq("user_id", loginUserId);
            discussionLikeQueryWrapper.eq("target_id", ask.getId());
            discussionLikeQueryWrapper.eq("target_type", type);
            DiscussionLike discussionLike = discussionLikeService.getOne(discussionLikeQueryWrapper);
            // 之前未点赞且无记录
            if (discussionLike == null) {
                DiscussionLike newLike = new DiscussionLike();
                newLike.setUserId(loginUserId);
                newLike.setTargetId(id);
                newLike.setTargetType(ASK.ordinal());
                newLike.setIsLiked(1);
                boolean saveResult = discussionLikeService.save(newLike);
                if (!saveResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                // 增加对应主题的点赞量到
                ask.setLikeCount(ask.getLikeCount() + 1);
                boolean updateResult = askService.updateById(ask);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                return ResultUtil.ok(true, "点赞成功");
            }

            // 更新条件（对应主题对应用户id）
            UpdateWrapper<DiscussionLike> likeUpdateWrapper = new UpdateWrapper<>();
            likeUpdateWrapper.eq("user_id", loginUserId);
            likeUpdateWrapper.eq("target_id", ask.getId());
            likeUpdateWrapper.eq("target_type", ASK.ordinal());

            // 之前未点赞（但有记录）
            if (discussionLike.getIsLiked() == 0) {
                likeUpdateWrapper.set("is_liked", 1);
                boolean updateResult1 = discussionLikeService.update(likeUpdateWrapper);
                if (!updateResult1) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                // 增加对应主题的点赞量到
                ask.setLikeCount(ask.getLikeCount() + 1);
                boolean updateResult = askService.updateById(ask);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                return ResultUtil.ok(true, "点赞成功");
            }

            // 之前已点赞
            if (discussionLike.getIsLiked() == 1) {
                likeUpdateWrapper.set("is_liked", 0);
                boolean updateResult2 = discussionLikeService.update(likeUpdateWrapper);
                if (!updateResult2) {
                    return ResultUtil.fail(ERROR_SYSTEM, "取消点赞失败");
                }
                // 减少对应主题的点赞量到
                ask.setLikeCount(ask.getLikeCount() + 1);
                boolean updateResult = askService.updateById(ask);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "取消点赞失败");
                }
                return ResultUtil.ok(true, "取消点赞成功");
            }
        } else {
            Answer answer = answerService.getById(id);

            // 获取当前用户对该主题的点赞记录
            if (loginUser == null) {
                return ResultUtil.fail(INTERCEPTOR_LOGIN, "未登录");
            }
            Integer loginUserId = loginUser.getId();
            QueryWrapper<DiscussionLike> discussionLikeQueryWrapper = new QueryWrapper<>();
            discussionLikeQueryWrapper.eq("user_id", loginUserId);
            discussionLikeQueryWrapper.eq("target_id", answer.getId());
            discussionLikeQueryWrapper.eq("target_type", type);
            DiscussionLike discussionLike = discussionLikeService.getOne(discussionLikeQueryWrapper);
            // 之前未点赞且无记录
            if (discussionLike == null) {
                DiscussionLike newLike = new DiscussionLike();
                newLike.setUserId(loginUserId);
                newLike.setTargetId(id);
                newLike.setTargetType(ANSWER.ordinal());
                newLike.setIsLiked(1);
                boolean saveResult = discussionLikeService.save(newLike);
                if (!saveResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                // 增加对应主题的点赞量到
                answer.setLikeCount(answer.getLikeCount() + 1);
                boolean updateResult = answerService.updateById(answer);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                return ResultUtil.ok(true, "点赞成功");
            }

            // 更新条件（对应主题对应用户id）
            UpdateWrapper<DiscussionLike> likeUpdateWrapper = new UpdateWrapper<>();
            likeUpdateWrapper.eq("user_id", loginUserId);
            likeUpdateWrapper.eq("target_id", answer.getId());
            likeUpdateWrapper.eq("target_type", ANSWER.ordinal());

            // 之前未点赞（但有记录）
            if (discussionLike.getIsLiked() == 0) {
                likeUpdateWrapper.set("is_liked", 1);
                boolean updateResult1 = discussionLikeService.update(likeUpdateWrapper);
                if (!updateResult1) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                // 增加对应主题的点赞量到
                answer.setLikeCount(answer.getLikeCount() + 1);
                boolean updateResult = answerService.updateById(answer);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "点赞失败");
                }
                return ResultUtil.ok(true, "点赞成功");
            }

            // 之前已点赞
            if (discussionLike.getIsLiked() == 1) {
                likeUpdateWrapper.set("is_liked", 0);
                boolean updateResult2 = discussionLikeService.update(likeUpdateWrapper);
                if (!updateResult2) {
                    return ResultUtil.fail(ERROR_SYSTEM, "取消点赞失败");
                }
                // 减少对应主题的点赞量到
                answer.setLikeCount(answer.getLikeCount() + 1);
                boolean updateResult = answerService.updateById(answer);
                if (!updateResult) {
                    return ResultUtil.fail(ERROR_SYSTEM, "取消点赞失败");
                }
                return ResultUtil.ok(true, "取消点赞成功");
            }
        }
        return ResultUtil.ok(false, "操作失败");
    }

    @Override
    public Result<Boolean> deleteAsk(Integer askId) {
        // 查询对应的提问
        Ask ask = askService.getById(askId);
        if (ask == null) {
            return ResultUtil.fail("该提问不存在");
        }
        // 判断当前登录用户是否为该发布主题的所有者
        User user = UserHolder.getUser();
        int userId = user.getId();
        if (userId != ask.getPublisherId()) {
            return ResultUtil.fail("抱歉！您无权进行删除！");
        }
        // 删除该发布提问
        QueryWrapper<Ask> askQueryWrapper = new QueryWrapper<>();
        askQueryWrapper.eq("id", askId);
        boolean remove = askService.remove(askQueryWrapper);
        if (!remove) {
            return ResultUtil.fail("该提问删除失败");
        }
        // 删除与之相关的图片
        QueryWrapper<Image> imageQueryWrapper = new QueryWrapper<>();
        imageQueryWrapper.eq("image_type", ASK.ordinal()).eq("image_src_id", askId);
        boolean remove1 = imageService.remove(imageQueryWrapper);
        if (!remove1) {
            return ResultUtil.fail("该提问相关图片删除失败");
        }

        // 删除它的回答
        QueryWrapper<Answer> answerQueryWrapper = new QueryWrapper<>();
        answerQueryWrapper.eq("ask_id", askId);
        boolean remove2 = answerService.remove(answerQueryWrapper);
        if (!remove) {
            return ResultUtil.fail("该回答删除失败");
        }
        // 删除与之相关的图片
        QueryWrapper<Image> imageQueryWrapper1 = new QueryWrapper<>();
        List<Integer> answerIdList = answerService.list(answerQueryWrapper).stream().map(answer -> answer.getId()).collect(Collectors.toList());
        imageQueryWrapper.eq("image_type", ANSWER.ordinal()).in("image_src_id", answerIdList);
        boolean remove3 = imageService.remove(imageQueryWrapper);
        return ResultUtil.ok(true, "删除成功");
    }

    @Override
    public Result<Boolean> collectAsk(Integer askId) {
        if(askId == null || askId <= 0){
            return ResultUtil.fail(ERROR_PARAMS,"参数错误");
        }
        Integer userId = UserHolder.getUser().getId();

        //判断该收藏记录是否已存在
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<Collect>()
                .eq("user_id", userId)
                .eq("topic_type", ASK.ordinal())
                .eq("topic_id", askId);
        Collect oldCollect = collectService.getOne(queryWrapper);
        //已存在,则删除收藏记录并同步更新话题收藏数
        boolean bool;
        if(oldCollect!=null){
            Ask ask = askService.getById(askId);
            ask.setCollectCount(ask.getCollectCount()-1);
            askService.saveOrUpdate(ask);
            bool = removeById(oldCollect);
        }else {//不存在则添加收藏记录并同步更新话题收藏数
            Ask ask = askService.getById(askId);
            ask.setCollectCount(ask.getCollectCount()+1);
            askService.saveOrUpdate(ask);
            Collect collect = new Collect();
            collect.setUserId(userId);
            collect.setTopicId(askId);
            collect.setTopicType(ASK.ordinal());
            bool = collectService.saveOrUpdate(collect);
        }
        if(bool){
            return ResultUtil.ok(SUCCESS_REQUEST,"记录更新成功！");
        } else{
            return ResultUtil.fail(ERROR_REQUEST,"记录更新失败");
        }
    }
}




