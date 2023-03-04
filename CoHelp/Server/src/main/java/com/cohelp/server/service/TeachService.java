package com.cohelp.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cohelp.server.model.entity.Teach;
import com.cohelp.server.model.vo.*;

import java.util.List;

/**
* @author jianping5
* @description 针对表【teach】的数据库操作Service
* @createDate 2023-03-01 14:31:07
*/
public interface TeachService extends IService<Teach> {

    /**
     * 获取老师当前学期的授课列表
     * @return java.util.List<com.cohelp.server.model.vo.CourseVO>
     */
    List<CourseVO> listCourse();
    /**
     * 查询指定提问的回答列表
     * @param page 当前页码
     * @param limit 每页最大数量
     * @param askId 提问的id
     * @return java.util.List<com.cohelp.server.model.vo.AnswerVO>
     */
    List<AnswerVO> listAnswer(Integer page,Integer limit,Integer askId);
    /**
     * 从答案库移除指定答案
     * @param answerId 回答id
     * @return java.lang.String
     */
    String removeAnswerFromBank(Integer answerId);
    /**
     * 从题库移除指定题目
     * @param quetionId 题目id
     * @return java.lang.String
     */
    String removeQuestionFromBank(Integer quetionId);
    /**
     * 获取指定课程的题库题目列表
     * @param page 当前页码
     * @param limit 每页最大数量
     * @param courseId 课程id
     * @return java.util.List<com.cohelp.server.model.vo.QuestionBankVO>
     */
    List<QuestionBankVO> listQuestionFromBank(Integer page, Integer limit, Integer courseId);
    /**
     * 获取指定课程指定难度的的题库题目列表
     * @param page 当前页码
     * @param limit 每页最大数量
     * @param courseId 课程id
     * @param level 难度
     * @return java.util.List<com.cohelp.server.model.vo.QuestionBankVO>
     */
    List<QuestionBankVO> listQuestionByLevel(Integer page, Integer limit, Integer courseId, Integer level);
    /**
     * 将指定的提问加入题库
     * @param askId 提问id
     * @param level 难度
     * @return java.lang.String
     */
    String addQuestionToBank(Integer askId,Integer level);
    /**
     * 将指定的回答加入答案库
     * @param answerId 提问id
     * @param recommendedDegree 推荐度
     * @return java.lang.String
     */
    String addAnswerToBank(Integer answerId,Integer recommendedDegree);
    /**
     * 给指定学生的指定课程加分
     * @param userId 用户id
     * @param score 待加的分数
     * @return java.lang.String
     */
    String addScore(Integer userId,Integer score,Integer courseId);

    /**
     * 根据题库题目id发布提问
     * @param questionIds 题目id
     * @return java.lang.String
     */
    String publishQuestionFromBank(List<Integer> questionIds);

    /**
     * 根据回答目标，回答类型，以及答案库答案id发布答案
     * @param targetId 回答目标id
     * @param targetType 回答类型
     * @param answerBankIds 答案库答案id
     * @return java.lang.String
     */
    String publishAnswerFromBank(Integer targetId,Integer targetType,List<Integer> answerBankIds);
    /**
     * 查询指定提问的推荐答案
     * @param askId 提问id
     * @return java.lang.String
     */
    List<AnswerBankVO> listAnswerFromBank(Integer askId);
    /**
     * 列出题库指定题目的答案
     * @param questionBankId 题库题目id
     * @return java.util.List<com.cohelp.server.model.vo.AnswerBankVO>
     */
    List<AnswerBankVO> listAnswerBankByQuestionBankId(Integer questionBankId);
    /**
     * 获取指定课程当前学期学生的积分数据
     * @param courseId 课程id
     * @return java.util.List<com.cohelp.server.model.vo.ScoreVO>
     */
    List<ScoreVO> listScore(Integer courseId);
}
