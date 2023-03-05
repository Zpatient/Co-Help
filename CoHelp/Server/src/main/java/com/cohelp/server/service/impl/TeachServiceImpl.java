package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.mapper.TeachMapper;
import com.cohelp.server.model.entity.*;
import com.cohelp.server.model.vo.*;
import com.cohelp.server.service.*;
import com.cohelp.server.utils.PageUtil;
import com.cohelp.server.utils.UserHolder;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.xm.Similarity;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cohelp.server.constant.TypeEnum.*;

/**
* @author jianping5
* @description 针对表【teach】的数据库操作Service实现
* @createDate 2023-03-01 14:31:07
*/
@Service
public class TeachServiceImpl extends ServiceImpl<TeachMapper, Teach>
    implements TeachService{

    @Resource
    private CourseService courseService;

    @Resource
    private AnswerService answerService;

    @Resource
    private HistoryService historyService;

    @Resource
    private AskService askService;

    @Resource
    private QuestionBankService questionBankService;

    @Resource
    private AnswerBankService answerBankService;

    @Resource
    private DiscussionLikeService discussionLikeService;

    @Resource
    private UserService userService;

    @Resource
    private ImageService imageService;

    @Resource
    private SelectionService selectionService;

    @Override
    public List<CourseVO> listCourse() {

        List<CourseVO> courseVOList = new ArrayList<>();
        // 获取当前登录用户的 id
        User user = UserHolder.getUser();
        int userId = user.getId();

        // 获取教师的授课数据
        QueryWrapper<Teach> teachQueryWrapper = new QueryWrapper<>();
        teachQueryWrapper.eq("teacher_id", userId);
        List<Teach> list = list(teachQueryWrapper);
        // 若系统内无授课数据,返回空数据
        if (list.isEmpty()||list==null) {
            return courseVOList;
        }
        Set<String> semesterSet = list.stream().map(item -> item.getSemester()).collect(Collectors.toSet());
        // 将 Set 转换为 List，便于获取元素
        List<String> semesterList = semesterSet.stream().collect(Collectors.toList());
        String currentSemester = semesterList.get(semesterList.size()-1);

        // 获取教师当前学期的授课课程
        QueryWrapper<Teach> teachQueryWrapper1 = new QueryWrapper<>();
        teachQueryWrapper1.eq("semester", currentSemester);
        List<Teach> teachList = list(teachQueryWrapper1).stream().collect(Collectors.toList());

        // 遍历选课集合，渲染课程 VO
        for (Teach teach : teachList) {
            CourseVO courseVO = new CourseVO();
            Course course = courseService.getById(teach.getCourseId());
            BeanUtils.copyProperties(course, courseVO);
            courseVO.setSemester(teach.getSemester());
            courseVOList.add(courseVO);
        }
        return courseVOList;
    }

    @Override
    public List<AnswerVO> listAnswer(Integer page,Integer limit,Integer askId) {
        //判断参数合法性
        if(ObjectUtils.anyNull(askId,page,limit)){
            return null;
        }
        ArrayList<AnswerVO> answerVOS = new ArrayList<>();
        //获取当前登录的用户
        User user = UserHolder.getUser();
        //获取对应话题的评论
        QueryWrapper<Answer> answerQueryWrapper = new QueryWrapper<Answer>().eq("ask_id", askId);
        Page<Answer> answerPage = answerService.page(new Page<Answer>(page,limit),answerQueryWrapper);
        List<Answer> answerList = answerPage.getRecords();
        for(Answer answer : answerList){
            AnswerVO answerVO = traverseAnswer(answer);
            //查询点赞状况并注入到answerVO中
            QueryWrapper<DiscussionLike> discussionLikeQueryWrapper = new QueryWrapper<DiscussionLike>()
                    .eq("target_type", TypeEnum.ANSWER.ordinal())
                    .eq("target_id", answer.getId())
                    .eq("user_id",user.getId());
            DiscussionLike one = discussionLikeService.getOne(discussionLikeQueryWrapper, false);
            if(one!=null&&one.getIsLiked().equals(1)){
                answerVO.setIsLiked(1);
            }
            answerVOS.add(answerVO);
        }
        return answerVOS;
    }

    @Override
    public String removeAnswerFromBank(Integer answerId) {
        //判断参数合法性
        if(ObjectUtils.anyNull(answerId)){
            return "参数不能为空！";
        }
        //删除对应题目
        Boolean remove = answerBankService.removeById(answerId);
        if (!remove) {
            return "答案删除失败";
        }
        // 删除与之相关的图片
        QueryWrapper<Image> imageQueryWrapper = new QueryWrapper<>();
        imageQueryWrapper.eq("image_type", ANSWERBANK.ordinal()).eq("image_src_id", answerId);
        boolean remove1 = imageService.remove(imageQueryWrapper);
        if (!remove1) {
            return "答案相关图片删除失败";
        }
        return "删除成功";
    }

    @Override
    public String removeQuestionFromBank(Integer questionId) {
        //判断参数合法性
        if(ObjectUtils.anyNull(questionId)){
            return "参数不能为空！";
        }
        // 删除该题目
        boolean remove = questionBankService.removeById(questionId);
        if (!remove) {
            return "题目删除失败";
        }
        // 删除与之相关的图片
        QueryWrapper<Image> imageQueryWrapper = new QueryWrapper<>();
        imageQueryWrapper.eq("image_type", QUESTIONBANK.ordinal()).eq("image_src_id", questionId);
        boolean remove1 = imageService.remove(imageQueryWrapper);
        if (!remove1) {
            return "题目相关图片删除失败";
        }
        // 删除它的回答
        QueryWrapper<AnswerBank> answerQueryWrapper = new QueryWrapper<>();
        answerQueryWrapper.eq("question_id", questionId);
        List<Integer> answerBanks = answerBankService.list(answerQueryWrapper).stream().map(i -> i.getId()).collect(Collectors.toList());
        boolean match = answerBanks.stream().map(i -> removeAnswerFromBank(i)).noneMatch(k -> k.equals("删除成功"));
        if (match) {
            return "题目相关答案删除失败";
        }
        return "删除成功";
    }

    @Override
    public List<QuestionBankVO> listQuestionFromBank(Integer page, Integer limit, Integer courseId) {
        //判断参数合法性
        if(ObjectUtils.anyNull(courseId,page,limit)){
            return null;
        }
        List<QuestionBankVO> questionBankVOS = null;
        //获取相关题目
        QueryWrapper<QuestionBank> queryWrapper = new QueryWrapper<QuestionBank>().eq("course_id", courseId);
        Page<QuestionBank> questionBankPage = questionBankService.page(new Page<QuestionBank>(page,limit),queryWrapper);
        List<QuestionBank> questionBankList = questionBankPage.getRecords();
        questionBankVOS = questionBankList.stream().map(i -> traverseQuestionBank(i)).filter(k->k!=null).collect(Collectors.toList());
        if(questionBankVOS !=null){
            return questionBankVOS;
        }else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<QuestionBankVO> listQuestionByLevel(Integer page, Integer limit, Integer courseId, Integer level) {
        //判断参数合法性
        if(ObjectUtils.anyNull(courseId,page,limit,level)||level<1||level>5){
            return null;
        }
        List<QuestionBankVO> questionBankVOS = null;
        //获取相关题目
        QueryWrapper<QuestionBank> queryWrapper = new QueryWrapper<QuestionBank>().eq("course_id", courseId).eq("level",level);
        Page<QuestionBank> questionBankPage = questionBankService.page(new Page<QuestionBank>(page,limit),queryWrapper);
        List<QuestionBank> questionBankList = questionBankPage.getRecords();
        questionBankVOS = questionBankList.stream().map(i -> traverseQuestionBank(i)).filter(k->k!=null).collect(Collectors.toList());
        if(questionBankVOS !=null){
            return questionBankVOS;
        }else {
            return new ArrayList<>();
        }
    }

    @Override
    public String addQuestionToBank(Integer askId, Integer level) {
        //判断参数合法性
        if(ObjectUtils.anyNull(askId,level)||level<1||level>5){
            return "参数不合法";
        }
        //查询相关提问注入题库
        Ask ask = askService.getById(askId);
        QuestionBank questionBank = new QuestionBank();
        if(ask==null){
            return "题目不存在";
        }else {
            QueryWrapper<QuestionBank> queryWrapper = new QueryWrapper<QuestionBank>().eq("ask_id", askId);
            QuestionBank questionBankOne = questionBankService.getOne(queryWrapper, false);
            if(questionBankOne==null){
                //题目尚未加入题库，将题目信息保存到题库
                questionBank.setContent(ask.getQuestion());
                questionBank.setAskId(askId);
                questionBank.setCourseId(ask.getCourseId());
                questionBank.setLevel(level);
                boolean save = questionBankService.save(questionBank);
                if(!save){
                    return "题目加入题库失败";
                }
                //将题目对应图片也加入题库
                Integer questionBankId = questionBank.getId();
                List<Image> list = imageService.list(new QueryWrapper<Image>()
                        .eq("image_type", ASK)
                        .eq("image_src_id", askId)
                        .eq("image_state", 0));
                boolean noneMatch = list.stream().map(i -> i.getImageUrl()).noneMatch(k -> {
                    Image image = new Image();
                    image.setImageType(QUESTIONBANK.ordinal());
                    image.setImageSrcId(questionBankId);
                    image.setImageUrl(k);
                    return imageService.saveOrUpdate(image);
                });
                if(noneMatch){
                    return "题目图片加入题库失败";
                }
                //修改ask的is_added字段
                ask.setIsAdded(1);
                askService.saveOrUpdate(ask);
                //给发布提问的学生加分
                addScore(ask.getPublisherId(), 5,ask.getCourseId());
                return "加入题库成功";
            }else {
                //题库已加入相关题目,更新题库相关信息
                questionBankOne.setContent(ask.getQuestion());
                questionBankOne.setAskId(askId);
                questionBankOne.setCourseId(ask.getCourseId());
                questionBankOne.setLevel(level);
                boolean save = questionBankService.saveOrUpdate(questionBankOne);
                if(!save){
                    return "题目加入题库失败";
                }
                //更新题库图片
                boolean remove = imageService.remove(new QueryWrapper<Image>()
                        .eq("image_type", QUESTIONBANK)
                        .eq("image_src_id", questionBankOne.getId()));
                if(!remove){
                    return "题目图片加入题库失败";
                }
                List<Image> list = imageService.list(new QueryWrapper<Image>()
                        .eq("image_type", ASK)
                        .eq("image_src_id", askId)
                        .eq("image_state", 0));
                boolean noneMatch = list.stream().map(i -> i.getImageUrl()).noneMatch(k -> {
                    Image image = new Image();
                    image.setImageType(QUESTIONBANK.ordinal());
                    image.setImageSrcId(questionBankOne.getId());
                    image.setImageUrl(k);
                    return imageService.saveOrUpdate(image);
                });
                if(noneMatch){
                    return "题目相关图片加入题库失败";
                }
                //修改ask的is_added字段
                ask.setIsAdded(1);
                askService.saveOrUpdate(ask);
                return "加入题库成功";
            }
        }
    }

    @Override
    public String addAnswerToBank(Integer answerId, Integer recommendedDegree) {
        //判断参数合法性
        if(ObjectUtils.anyNull(answerId,recommendedDegree)){
            return "参数不合法";
        }
        Answer answer = answerService.getById(answerId);
        AnswerBank answerBank = new AnswerBank();
        if(answer==null){
            return "回答不存在";
        }else {
            //将题目加入题库
            Integer askId = answer.getAskId();
            if(!addQuestionToBank(askId,3).equals("加入题库成功")){
                return "加入答案库失败(题目)";
            }else {
                //将回答加入答案库
                QueryWrapper<AnswerBank> queryWrapper = new QueryWrapper<AnswerBank>().eq("answer_id", answerId);
                AnswerBank answerBankOne = answerBankService.getOne(queryWrapper, false);
                if(answerBankOne==null){
                    //回答尚未加入答案库，将回答信息保存到答案库
                    answerBank.setContent(answer.getContent());
                    answerBank.setRecommendedDegree(recommendedDegree);
                    answerBank.setAnswerId(answerId);
                    //查询题库对应题目id注入到answerBank中
                    QuestionBank questionBank = questionBankService.getOne(new QueryWrapper<QuestionBank>().eq("ask_id", askId));
                    if(questionBank!=null){
                        answerBank.setQuestionId(questionBank.getId());
                    }else {
                        return "加入答案库失败(题目)";
                    }

                    boolean save = answerBankService.save(answerBank);
                    if(!save){
                        return "回答加入答案库失败";
                    }
                    //将答案对应图片也加入题库
                    Integer answerBankId = answerBank.getId();
                    List<Image> list = imageService.list(new QueryWrapper<Image>()
                            .eq("image_type", ANSWER)
                            .eq("image_src_id", answerId)
                            .eq("image_state", 0));
                    boolean noneMatch = list.stream().map(i -> i.getImageUrl()).noneMatch(k -> {
                        Image image = new Image();
                        image.setImageType(ANSWERBANK.ordinal());
                        image.setImageSrcId(answerBankId);
                        image.setImageUrl(k);
                        return imageService.saveOrUpdate(image);
                    });
                    if(noneMatch){
                        return "回答图片加入答案库失败";
                    }
                    //修改answer的is_added字段
                    answer.setIsAdded(1);
                    answerService.saveOrUpdate(answer);
                    //给发布回答的学生加分
                    Ask ask = askService.getById(askId);
                    if(ask!=null){
                        Integer courseId = ask.getCourseId();
                        addScore(answer.getPublisherId(), 5,courseId);
                    }
                    return "加入答案库成功";
                }else {

                    //题库已加入相关题目,更新题库相关信息
                    answerBankOne.setContent(answer.getContent());
                    answerBankOne.setAnswerId(answerId);
                    answerBankOne.setRecommendedDegree(recommendedDegree);
                    //查询题库对应题目id注入到answerBank中
                    QuestionBank questionBank = questionBankService.getOne(new QueryWrapper<QuestionBank>().eq("ask_id", askId));
                    if(questionBank!=null){
                        answerBank.setQuestionId(questionBank.getId());
                    }else {
                        return "加入答案库失败(题目)";
                    }
                    boolean save = answerBankService.saveOrUpdate(answerBankOne);
                    if(!save){
                        return "回答加入答案库失败";
                    }
                    //更新题库图片
                    boolean remove = imageService.remove(new QueryWrapper<Image>()
                            .eq("image_type", ANSWERBANK)
                            .eq("image_src_id", answerBankOne.getId()));
                    if(!remove){
                        return "回答图片加入答案库失败";
                    }
                    List<Image> list = imageService.list(new QueryWrapper<Image>()
                            .eq("image_type", ANSWER)
                            .eq("image_src_id", answerId)
                            .eq("image_state", 0));
                    boolean noneMatch = list.stream().map(i -> i.getImageUrl()).noneMatch(k -> {
                        Image image = new Image();
                        image.setImageType(ANSWERBANK.ordinal());
                        image.setImageSrcId(answerBankOne.getId());
                        image.setImageUrl(k);
                        return imageService.saveOrUpdate(image);
                    });
                    if(noneMatch){
                        return "回答图片加入答案库失败";
                    }
                    //修改answer的is_added字段
                    answer.setIsAdded(1);
                    answerService.saveOrUpdate(answer);
                    return "加入答案库成功";
                }
            }
        }
    }

    @Override
    public String addScore(Integer userId, Integer score,Integer courseId) {
        //判断参数合法性
        if(ObjectUtils.anyNull(userId,score,courseId)){
            return "参数不合法";
        }
        User user = userService.getById(userId);
        if(user==null){
            return "加分失败";
        }else {
            if(user.getType().equals(1)){
                return "教师账号不可加分";
            }else {
                QueryWrapper<Selection> selectionQueryWrapper = new QueryWrapper<>();
                selectionQueryWrapper.eq("student_id",userId).eq("course_id",courseId).orderByDesc("semester");
                List<Selection> selectionList = selectionService.list(selectionQueryWrapper);
                if(selectionList!=null&&!selectionList.isEmpty()){
                    Selection selection = selectionList.get(0);
                    selection.setScore(selection.getScore()+score);
                    boolean b = selectionService.saveOrUpdate(selection);
                    if(!b){
                        return "加分失败";
                    }else {
                        return "加分成功";
                    }
                }else {
                    return "查询不到学生的选课记录";
                }
            }
        }
    }

    @Override
    public String publishQuestionFromBank(List<Integer> questionIds) {
        if(ObjectUtils.anyNull(questionIds)||questionIds.isEmpty()){
            return "参数不能为空";
        }
        //获取当前登录对象
        User user = UserHolder.getUser();
        int userId = user.getId();
        //设置标志位
        Boolean flag = false;
        for(Integer id:questionIds){
            Ask ask = new Ask();
            //查询id对应题目
            QuestionBank questionBank = questionBankService.getById(id);
            if(questionBank!=null) {
                ask.setQuestion(questionBank.getContent());
                ask.setPublisherId(userId);
                ask.setCourseId(questionBank.getCourseId());
                ask.setIsAdded(1);
                //查询当前学期
                QueryWrapper<Teach> teachQueryWrapper = new QueryWrapper<Teach>()
                        .eq("teacher_id", userId)
                        .eq("course_id", questionBank.getId())
                        .orderByDesc("semester");
                List<Teach> list = list(teachQueryWrapper);
                list.stream().findFirst().ifPresent(k->ask.setSemester(k.getSemester()));
                if(ask.getSemester()==null){
                    flag=true;
                    continue;
                }
                //发布题目以及对应图片
                boolean save = askService.save(ask);
                if(save){
                    List<Image> images = imageService.list(new QueryWrapper<Image>()
                            .eq("image_type", QUESTIONBANK)
                            .eq("image_src_id", questionBank.getId())
                            .eq("image_state", 0));
                    List<String> collect = images.stream().map(o -> o.getImageUrl()).collect(Collectors.toList());
                    collect.stream().forEach(k->{
                        Image image = new Image();
                        image.setImageUrl(k);
                        image.setImageType(ASK.ordinal());
                        image.setImageSrcId(ask.getId());
                        imageService.save(image);
                    });
                }else {
                    flag = true;
                }
            }else {
                flag = true;
            }
        }
        if (flag){
            return "部分题目发布失败";
        }else {
            return "题目发布成功";
        }
    }

    @Override
    public String publishAnswerFromBank(Integer targetId, Integer targetType, List<Integer> answerBankIds) {
        if(ObjectUtils.anyNull(targetId,targetType,answerBankIds)||answerBankIds.isEmpty()){
            return "参数不能为空";
        }
        //获取当前登录对象
        User user = UserHolder.getUser();
        int userId = user.getId();
        //设置标志位
        Boolean flag = false;
        for(Integer id:answerBankIds){
            Answer answer = new Answer();
            //查询id对应答案
            AnswerBank answerBank = answerBankService.getById(id);
            if(answerBank!=null) {
                //向回答中注入相应属性
                answer.setPublisherId(userId);
                answer.setContent(answerBank.getContent());
                answer.setAnswerTargetId(targetId);
                answer.setAnswerTargetType(targetType);
                answer.setIsAdded(1);
                if(targetType.equals(0)){
                    answer.setAskId(targetId);
                }else if(targetType.equals(1)){
                    Answer byId = answerService.getById(targetId);
                    if(byId!=null){
                        answer.setAskId(byId.getAskId());
                    }else {
                        flag = true;
                        continue;
                    }
                }else {
                    flag = true;
                    continue;
                }
                //发布回答以及对应图片
                boolean save = answerService.save(answer);
                if(save){
                    List<Image> images = imageService.list(new QueryWrapper<Image>()
                            .eq("image_type", ANSWERBANK)
                            .eq("image_src_id", answerBank.getId())
                            .eq("image_state", 0));
                    List<String> collect = images.stream().map(o -> o.getImageUrl()).collect(Collectors.toList());
                    collect.stream().forEach(k->{
                        Image image = new Image();
                        image.setImageUrl(k);
                        image.setImageType(ANSWER.ordinal());
                        image.setImageSrcId(answer.getId());
                        imageService.save(image);
                    });
                    //更改历史记录参与字段
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
                }else {
                    flag = true;
                }
            }else {
                flag = true;
            }
        }
        if (flag){
            return "部分回答发布失败";
        }else {
            return "回答发布成功";
        }
    }

    @Override
    public List<AnswerBankVO> listAnswerFromBank(Integer askId,Integer page,Integer limit) {
        if(ObjectUtils.anyNull(askId,page,limit)){
            return null;
        }
        //查询与该题目类似的推荐答案
        ArrayList<AnswerBankVO> answerBankVOS = new ArrayList<>();
        Ask ask = askService.getById(askId);
        if(ask!=null){
            String question = ask.getQuestion();
            QueryWrapper<QuestionBank> queryWrapper = new QueryWrapper<QuestionBank>().eq("course_id", ask.getCourseId());
            List<QuestionBank> list = questionBankService.list(queryWrapper);
            list.stream().forEach(k->{
                //判断相似度，将相似度大于0.9的问题答案加入到推荐答案中
                double similarity = Similarity.gregorEditDistanceSimilarity(k.getContent(), question);
                if(similarity>0.9){
                    List<AnswerBankVO> voList = listAnswerBankByQuestionBankId(k.getId());
                    voList.stream().forEach(i->i.setRecommendedDegree(new Double(similarity*i.getRecommendedDegree()).intValue()));
                    answerBankVOS.addAll(voList);
                }
            });
            answerBankVOS.sort(new Comparator<AnswerBankVO>() {
                @Override
                public int compare(AnswerBankVO o1, AnswerBankVO o2) {
                    return o2.getRecommendedDegree().compareTo(o1.getRecommendedDegree());
                }
            });
        }
        List<AnswerBankVO> bankVOList = PageUtil.pageByList(answerBankVOS, page, limit);
        return bankVOList;
    }

    @Override
    public List<AnswerBankVO> listAnswerBankByQuestionBankId(Integer questionBankId){
        ArrayList<AnswerBankVO> answerBankVOS = new ArrayList<>();
        if(questionBankId==null){
            return answerBankVOS;
        }else {
            QueryWrapper<AnswerBank> queryWrapper = new QueryWrapper<AnswerBank>().eq("question_id", questionBankId);
            List<AnswerBank> list = answerBankService.list(queryWrapper);
            list.stream().forEach(k->{
                AnswerBankVO answerBankVO = traverseAnswerBank(k);
                if(answerBankVO!=null){
                    answerBankVOS.add(answerBankVO);
                }
            });
        }
        return answerBankVOS;
    }

    @Override
    public List<ScoreVO> listScore(Integer courseId) {
        ArrayList<ScoreVO> scoreVOS = new ArrayList<>();
        if(courseId==null){
            return scoreVOS;
        }else {
            //获取当前登录用户
            User user = UserHolder.getUser();
            Integer userId = user.getId();
            String semester = "";
            //查询当前学期
            QueryWrapper<Teach> teachQueryWrapper = new QueryWrapper<Teach>()
                    .eq("teacher_id", userId)
                    .eq("course_id", courseId)
                    .orderByDesc("semester");
            List<Teach> list = list(teachQueryWrapper);
            list.stream().findFirst().ifPresent(((k->semester.concat(k.getSemester()))));
            if(semester.equals("")){
                return scoreVOS;
            }else {
                //查询选课数据
                QueryWrapper<Selection> queryWrapper = new QueryWrapper<Selection>()
                        .eq("course_id", courseId)
                        .eq("semester", semester);
                List<Selection> selectionList = selectionService.list(queryWrapper);
                selectionList.stream().forEach(m->{
                    ScoreVO scoreVO = new ScoreVO();
                    User student = userService.getById(m.getStudentId());
                    scoreVO.setUserId(m.getStudentId());
                    if(student==null){
                        scoreVO.setUserName("用户不存在");
                    }else {
                        scoreVO.setUserName(student.getUserName());
                    }
                    scoreVO.setScore(m.getScore());
                    scoreVO.setSemester(semester);
                    Course course = courseService.getById(courseId);
                    if(course==null){
                        scoreVO.setCourseName("课程不存在");
                    }else {
                        scoreVO.setCourseName(course.getName());
                    }
                    scoreVOS.add(scoreVO);
                });
            }
        }
        return scoreVOS;
    }

    private AnswerBankVO traverseAnswerBank(AnswerBank answerBank){
        if(answerBank==null){
            return null;
        }
        AnswerBankVO answerBankVO = new AnswerBankVO();
        BeanUtils.copyProperties(answerBank, answerBankVO);
        //获取题目归属课程名注入视图体
        Integer questionId = answerBank.getQuestionId();
        QuestionBank questionBank = questionBankService.getById(questionId);
        if(questionBank==null){
            return null;
        }
        Course course = courseService.getById(questionBank.getCourseId());
        if(course==null){
            answerBankVO.setCourseName("课程不存在");
        }else {
            answerBankVO.setCourseName(course.getName());
        }
        //获取答案相关图片注入视图体
        List<Image> list = imageService.list(new QueryWrapper<Image>()
                .eq("image_type", ANSWERBANK)
                .eq("image_src_id", answerBank.getId())
                .eq("image_state", 0));
        List<String> imageUrl = list.stream().map(k -> k.getImageUrl()).collect(Collectors.toList());
        answerBankVO.setImageUrl(imageUrl);
        return answerBankVO;
    }

    private QuestionBankVO traverseQuestionBank(QuestionBank questionBank){
        if(questionBank==null){
            return null;
        }
        QuestionBankVO questionBankVO = new QuestionBankVO();
        BeanUtils.copyProperties(questionBank, questionBankVO);
        //获取题目归属课程名注入视图体
        Course course = courseService.getById(questionBank.getCourseId());
        if(course!=null){
            questionBankVO.setCourseName(course.getName());
        }else {
            questionBankVO.setCourseName("课程不存在");
        }
        //获取题目难度注入视图体
        switch (questionBank.getLevel()){
            case 1:
                questionBankVO.setDifficulty("很容易");break;
            case 2:
                questionBankVO.setDifficulty("较容易");break;
            case 3:
                questionBankVO.setDifficulty("适中");break;
            case 4:
                questionBankVO.setDifficulty("较困难");break;
            case 5:
                questionBankVO.setDifficulty("很困难");break;
            default:
                questionBankVO.setDifficulty("适中");
        }
        //获取题目相关图片注入视图体
        List<Image> list = imageService.list(new QueryWrapper<Image>()
                .eq("image_type", QUESTIONBANK)
                .eq("image_src_id", questionBank.getId())
                .eq("image_state", 0));
        List<String> imageUrl = list.stream().map(k -> k.getImageUrl()).collect(Collectors.toList());
        questionBankVO.setImageUrl(imageUrl);
        return questionBankVO;
    }

    private AnswerVO traverseAnswer(Answer answer){
        if(answer==null){
            return null;
        }
        AnswerVO answerVO = new AnswerVO();
        BeanUtils.copyProperties(answer,answerVO);
        String publisherName = null;
        String publisherAvatar = null;
        String answerTargetName = null;
        //查询回答发布者头像及昵称
        User user = userService.getById(answer.getPublisherId());
        if(user==null){
            publisherName = "用户不存在";
            Image image = imageService.getById(1);
            publisherAvatar = image.getImageUrl();
        }else {
            publisherName = user.getUserName();
            Image image = imageService.getById(user.getAvatar());
            if(image!=null){
                publisherAvatar = image.getImageUrl();
            }else {
                Image image0 = imageService.getById(1);
                publisherAvatar = image0.getImageUrl();
            }
        }
        //查询回答目标发布者昵称
        if(answer.getAnswerTargetType().equals(0)){
            Ask targetAsk = askService.getById(answer.getAnswerTargetId());
            User targetUser = userService.getById(targetAsk.getPublisherId());
            if(targetUser==null){
                answerTargetName = "用户不存在";
            }else {
                 answerTargetName = targetUser.getUserName();
            }
        }else {
            Answer targetAnswer = answerService.getById(answer.getAnswerTargetId());
            User targetUser = userService.getById(targetAnswer.getPublisherId());
            if(targetUser==null){
                answerTargetName = "用户不存在";
            }else {
                answerTargetName = targetUser.getUserName();
            }
        }
        //查询回答内容中的图片
        QueryWrapper<Image> imageQueryWrapper = new QueryWrapper<Image>()
                .eq("image_type", 8)
                .eq("image_src_id", answer.getId())
                .eq("image_state", 0);
        List<Image> list = imageService.list(imageQueryWrapper);
        List<String> answerImageUrl = list.stream().map(element -> element.getImageUrl()).collect(Collectors.toList());
        //将查询到的属性注入到answerVO中
        answerVO.setPublisherName(publisherName);
        answerVO.setPublisherAvatar(publisherAvatar);
        answerVO.setAnswerTargetName(answerTargetName);
        answerVO.setAnswerImageUrl(answerImageUrl);
        answerVO.setIsLiked(0);
        return answerVO;
    }

}




