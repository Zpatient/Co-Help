package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.mapper.CourseMapper;
import com.cohelp.server.model.domain.PageResponse;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.*;
import com.cohelp.server.model.vo.AskVO;
import com.cohelp.server.model.vo.CourseVO;
import com.cohelp.server.model.vo.SelectionVO;
import com.cohelp.server.model.vo.TeachVO;
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
import java.util.Arrays;
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

    @Autowired
    private NsfwService nsfwService;

    @Value("${spring.tengxun.url}")
    private String path;

    @Resource
    private SelectionService selectionService;

    @Resource
    private TeachService teachService;

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

    private static List<Integer> scoreList = Arrays.asList(10, 20, 30, 40, 50);

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
    public Result<List<AskVO>> listAsk(Integer page, Integer limit, Integer courseId, String semester, Integer condition) {
        // 校验参数
        if (courseId == null || courseId <= 0) {
            return ResultUtil.fail("参数错误");
        }

        // 根据当前课程去查询该课程下的所有提问（从提问表中查询）
        QueryWrapper<Ask> askQueryWrapper = new QueryWrapper<>();
        askQueryWrapper.eq("course_id", courseId);
        askQueryWrapper.eq("semester", semester);
        Page<Ask> askPage = new Page<>();

        // 默认排序
        if (condition == 0) {
            askPage = askService.page(new Page<>((page - 1) * limit, limit), askQueryWrapper);
        }

        // 按热度排序
        if (condition == 1) {
            askQueryWrapper.orderByDesc("like_count + collect_count + answer_count");
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

            // 设置图片
            QueryWrapper<Image> imageQueryWrapper = new QueryWrapper<>();
            imageQueryWrapper.eq("image_src_id", ask.getId());
            imageQueryWrapper.eq("image_type", ASK.ordinal());
            List<String> imageUrlList = imageService.list(imageQueryWrapper).stream().map(image1 -> image1.getImageUrl()).collect(Collectors.toList());
            askVO.setImageUrl(imageUrlList);

            // 注入点赞判定值
            QueryWrapper<DiscussionLike> discussionLikeQueryWrapper = new QueryWrapper<>();
            discussionLikeQueryWrapper.eq("user_id", askVO.getPublisherId())
                    .eq("target_type", 1)
                    .eq("target_id", askVO.getId());
            DiscussionLike discussionLike = discussionLikeService.getOne(discussionLikeQueryWrapper);
            if (discussionLike == null) {
                askVO.setIsLiked(0);
            } else {
                askVO.setIsLiked(discussionLike.getIsLiked());
            }

            // 注入收藏判定值
            QueryWrapper<Collect> collectQueryWrapper = new QueryWrapper<>();
            collectQueryWrapper.eq("user_id", UserHolder.getUser().getId())
                    .eq("topic_type", 1)
                    .eq("topic_id", askVO.getId());
            Collect one = collectService.getOne(collectQueryWrapper);
            if (one == null) {
                askVO.setIsCollected(0);
            } else {
                askVO.setIsCollected(1);
            }

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

                // 加积分
                Integer type1 = loginUser.getType();
                if (type1 == 1) {
                    Integer publisherId = ask.getPublisherId();
                    User user = userService.getById(publisherId);
                    if (user != null && user.getType() != 1) {
                        teachService.addScore(publisherId, 2, ask.getCourseId());
                    }
                }

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

                // 加积分
                addScoreByAsk(ask);

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
                ask.setLikeCount(ask.getLikeCount() - 1);
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

                // 加积分
                Integer type1 = loginUser.getType();
                if (type1 == 1) {
                    Integer publisherId = answer.getPublisherId();
                    User user = userService.getById(publisherId);
                    if (user != null && user.getType() != 1) {
                        Integer askId = answer.getAskId();
                        Ask ask = askService.getById(askId);
                        if (ask != null) {
                            teachService.addScore(publisherId, 2, ask.getCourseId());
                        }
                    }
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

                // 加积分
                addScoreByAnswer(answer);

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
        QueryWrapper<Image> wrapper = new QueryWrapper<>();
        wrapper.eq("image_type", ASK.ordinal()).eq("image_src_id", askId);
        List<Image> imageList = imageService.list(wrapper);
        boolean remove1 = imageService.remove(wrapper);
        if (!remove1&&!imageList.isEmpty()) {
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
        if (answerIdList != null) {
            imageQueryWrapper1.eq("image_type", ANSWER.ordinal()).in("image_src_id", answerIdList);
            imageService.remove(imageQueryWrapper1);
        }
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

    @Override
    public Result<PageResponse<Course>> listCourseById(Integer page, Integer limit, Integer teamId) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("team_id", teamId);
        Page<Course> page1 = courseService.page(new Page<>((page - 1) * limit, limit), courseQueryWrapper);

        List<Course> records = page1.getRecords();
        long total = page1.getTotal();
        PageResponse<Course> coursePageResponse = new PageResponse<>();
        coursePageResponse.setResult(records);
        coursePageResponse.setTotal(total);

        return ResultUtil.ok(coursePageResponse);
    }

    @Override
    public Result<PageResponse<SelectionVO>> listSelection(Integer page, Integer limit, Integer teamId) {
        // 分页获取该学校的课程 id 数组
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("team_id", teamId);
        Page<Course> page1 = courseService.page(new Page<>((page - 1) * limit, limit), courseQueryWrapper);
        List<Integer> courseIdList = page1.getRecords().stream().map(course -> course.getId()).collect(Collectors.toList());

        // 根据课程 id 查询对应选课记录
        QueryWrapper<Selection> selectionQueryWrapper = new QueryWrapper<>();
        selectionQueryWrapper.in("course_id", courseIdList);
        List<Selection> selectionList = selectionService.page(new Page<>((page - 1) * limit, limit), selectionQueryWrapper).getRecords();

        // 创建选课视图体
        ArrayList<SelectionVO> selectionVOList = new ArrayList<>();

        // 遍历选课表，将数据注入选课视图体
        for (Selection selection : selectionList) {
            SelectionVO selectionVO = new SelectionVO();
            // 将选课数据赋值到选课视图体
            BeanUtils.copyProperties(selection, selectionVO);

            // 设置姓名
            Integer studentId = selection.getStudentId();
            User user = userService.getById(studentId);
            if (user != null) {
                selectionVO.setUserName(user.getUserName());
            }

            // 设置课程名
            Integer courseId = selection.getCourseId();
            Course course = courseService.getById(courseId);
            if (course != null) {
                selectionVO.setCourseName(course.getName());
            }

            selectionVOList.add(selectionVO);

        }

        long total = page1.getTotal();
        PageResponse<SelectionVO> selectionVOPageResponse = new PageResponse<>();
        selectionVOPageResponse.setResult(selectionVOList);
        selectionVOPageResponse.setTotal(total);

        return ResultUtil.ok(selectionVOPageResponse);
    }

    @Override
    public Result<PageResponse<TeachVO>> listTeach(Integer page, Integer limit, Integer teamId) {
        // 分页获取该学校的课程 id 数组
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("team_id", teamId);
        Page<Course> page1 = courseService.page(new Page<>((page - 1) * limit, limit), courseQueryWrapper);
        List<Integer> courseIdList = page1.getRecords().stream().map(course -> course.getId()).collect(Collectors.toList());

        // 根据课程 id 查询对应授课记录
        QueryWrapper<Teach> teachQueryWrapper = new QueryWrapper<>();
        teachQueryWrapper.in("course_id", courseIdList);
        List<Teach> teachList = teachService.page(new Page<>((page - 1) * limit, limit), teachQueryWrapper).getRecords();

        // 创建选课视图体
        ArrayList<TeachVO> teachVOList = new ArrayList<>();

        // 遍历选课表，将数据注入选课视图体
        for (Teach teach : teachList) {
            TeachVO teachVO = new TeachVO();
            // 将选课数据赋值到选课视图体
            BeanUtils.copyProperties(teach, teachVO);

            // 设置姓名
            Integer teacherId = teach.getTeacherId();
            User user = userService.getById(teacherId);
            if (user != null) {
                teachVO.setUserName(user.getUserName());
            }

            // 设置课程名
            Integer courseId = teach.getCourseId();
            Course course = courseService.getById(courseId);
            if (course != null) {
                teachVO.setCourseName(course.getName());
            }

            teachVOList.add(teachVO);

        }

        long total = page1.getTotal();
        PageResponse<TeachVO> teachVOPageResponse = new PageResponse<>();
        teachVOPageResponse.setResult(teachVOList);
        teachVOPageResponse.setTotal(total);

        return ResultUtil.ok(teachVOPageResponse);
    }

    @Override
    public Result<PageResponse<User>> listTeacher(Integer page, Integer limit, Integer teamId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("team_id", teamId);
        queryWrapper.eq("type", 1);

        Page<User> page1 = userService.page(new Page<>((page - 1) * limit, limit), queryWrapper);

        // 封装分页视图体
        List<User> records = page1.getRecords().stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        long total = page1.getTotal();
        PageResponse<User> userPageResponse = new PageResponse<>();
        userPageResponse.setResult(records);
        userPageResponse.setTotal(total);

        return ResultUtil.ok(userPageResponse);
    }

    @Override
    public Result<Boolean> addTeacher(User user) {
        User currentUser = UserHolder.getUser();

        // 初始化 User
        user.setUserPassword(user.getUserAccount());
        user.setTeamId(currentUser.getTeamId());
        user.setType(1);
        boolean save = userService.save(user);

        return ResultUtil.ok(save);
    }

    @Override
    public Result<Boolean> deleteCourse(Integer courseId) {
        // 获取当前用户的组织 id
        User user = UserHolder.getUser();
        Integer teamId = user.getTeamId();

        // 获取当前课程的组织 id
        Course course = courseService.getById(courseId);
        if (course == null) {
            return ResultUtil.fail("抱歉，该课程不存在");
        }
        Integer courseTeamId = course.getTeamId();

        // 比较二者
        if (!teamId.equals(courseTeamId)) {
            return ResultUtil.fail("抱歉，您没有权限");
        }

        // 删除该课程
        boolean b = courseService.removeById(courseId);

        if (!b) {
            return ResultUtil.fail("删除失败");
        }


        return ResultUtil.ok(true);
    }

    @Override
    public Result<Boolean> deleteSelection(Integer selectionId) {
        // 查询对应的选课
        Selection selection = selectionService.getById(selectionId);

        // 判断是否为空
        if (selection == null) {
            return ResultUtil.fail("抱歉，该选课记录不存在");
        }

        // 查询对应的学生是否属于当前学校
        Integer studentId = selection.getStudentId();
        User user = userService.getById(studentId);
        if (user == null) {
            return ResultUtil.fail("抱歉，该用户不存在");
        }

        Integer teamId = user.getTeamId();
        User currentUser = UserHolder.getUser();
        Integer teamId1 = currentUser.getTeamId();

        if (!teamId.equals(teamId1)) {
            return ResultUtil.fail("抱歉，您无权删除");
        }

        // 删除对应的选择
        boolean result = selectionService.removeById(selectionId);

        if (!result) {
            return ResultUtil.fail("删除失败");
        }

        return ResultUtil.ok(true);
    }

    @Override
    public Result<Boolean> deleteTeach(Integer teachId) {
        // 查询对应的授课
        Teach teach = teachService.getById(teachId);

        // 判断是否为空
        if (teachId == null) {
            return ResultUtil.fail("抱歉，该授课记录不存在");
        }

        // 查询对应的教师是否属于当前学校
        Integer teacherId = teach.getTeacherId();
        User user = userService.getById(teacherId);
        if (user == null) {
            return ResultUtil.fail("抱歉，该用户不存在");
        }

        Integer teamId = user.getTeamId();
        User currentUser = UserHolder.getUser();
        Integer teamId1 = currentUser.getTeamId();

        if (!teamId.equals(teamId1)) {
            return ResultUtil.fail("抱歉，您无权删除");
        }

        // 删除对应的选择
        boolean result = teachService.removeById(teachId);

        if (!result) {
            return ResultUtil.fail("删除失败");
        }

        return ResultUtil.ok(true);
    }

    @Override
    public Result<List<String>> listSemester() {
        // 获取学生 id
        User user = UserHolder.getUser();
        Integer userId = user.getId();

        // 根据学生 id 从选课表中查询学年
        QueryWrapper<Selection> selectionQueryWrapper = new QueryWrapper<>();
        selectionQueryWrapper.eq("student_id", userId);
        List<Selection> selectionList = selectionService.list(selectionQueryWrapper);

        // 若学生未选课，则返回空数组
        if (selectionList == null) {
            return ResultUtil.ok(new ArrayList<>());
        }

        // 对学年进行去重和排序
        Set<String> semesterSet = selectionList.stream().map(selection -> selection.getSemester()).collect(Collectors.toSet());
        List<String> semesterList = semesterSet.stream().collect(Collectors.toList());

        // 返回学年数组
        return ResultUtil.ok(semesterList);
    }


    /**
     * 给问题加积分
     * @param ask
     */
    public void addScoreByAsk(Ask ask) {

        // 若点赞量达到其中之一，则增加其积分
        if (scoreList.contains(ask.getLikeCount())) {
            String semester = ask.getSemester();
            Integer courseId = ask.getCourseId();
            QueryWrapper<Selection> selectionQueryWrapper = new QueryWrapper<>();
            selectionQueryWrapper.eq("semester", semester);
            selectionQueryWrapper.eq("course_id", courseId);
            Selection selection = selectionService.getOne(selectionQueryWrapper);
            selection.setScore(selection.getScore() + 1);
            selectionService.updateById(selection);
        }
    }


    /**
     * 给答案加积分
     * @param answer
     */
    public void addScoreByAnswer(Answer answer) {

        // 若点赞量达到其中之一，则增加其积分
        if (scoreList.contains(answer.getLikeCount())) {
            // 获取对应题目
            Integer askId = answer.getAskId();
            Ask ask = askService.getById(askId);
            // 找到授课表
            String semester = ask.getSemester();
            Integer courseId = ask.getCourseId();
            QueryWrapper<Selection> selectionQueryWrapper = new QueryWrapper<>();
            selectionQueryWrapper.eq("semester", semester);
            selectionQueryWrapper.eq("course_id", courseId);
            Selection selection = selectionService.getOne(selectionQueryWrapper);
            // 加分
            selection.setScore(selection.getScore() + 1);
            selectionService.updateById(selection);
        }
    }


}




