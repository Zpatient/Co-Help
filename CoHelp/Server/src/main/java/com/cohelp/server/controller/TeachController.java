package com.cohelp.server.controller;

import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.model.vo.*;
import com.cohelp.server.service.TeachService;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.UserHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zgy
 * @create 2023-03-01 18:46
 */
@RestController
@RequestMapping("/teach")
public class TeachController {
    @Resource
    private TeachService teachService;

    @RequestMapping("/listcourse")
    public Result<List<CourseVO>> listCourse(){
        List<CourseVO> courseVOS = teachService.listCourse();
        return ResultUtil.ok(courseVOS);
    }

    @RequestMapping("/listanswer/{page}/{limit}/{askId}")
    public Result<List<AnswerVO>> listAnswer(@PathVariable Integer page, @PathVariable Integer limit,@PathVariable Integer askId){
        List<AnswerVO> answerVOS = teachService.listAnswer(page,limit,askId);
        return ResultUtil.ok(answerVOS);
    }

    @RequestMapping("/removequestion/{questionId}")
    public Result<List<AnswerVO>> removeQuestionFromBank(@PathVariable Integer questionId){
        //获取当前登录的用户并判断是否是老师
        User user = UserHolder.getUser();
        Integer type = user.getType();
        if(!type.equals(1)){
            return ResultUtil.fail("权限不足，删除失败！");
        }
        String removeQuestionFromBank = teachService.removeQuestionFromBank(questionId);
        return ResultUtil.ok(removeQuestionFromBank);
    }

    @RequestMapping("/removeanswer/{answerId}")
    public Result<Boolean> removeAnswerFromBank(@PathVariable Integer answerId){
        //获取当前登录的用户并判断是否是老师
        User user = UserHolder.getUser();
        Integer type = user.getType();
        if(!type.equals(1)){
            return ResultUtil.fail("权限不足，删除失败！");
        }
        //其余状况
        String removeAnswerFromBank = teachService.removeAnswerFromBank(answerId);
        return ResultUtil.ok(removeAnswerFromBank);

    }

    @RequestMapping("/listquestion/{page}/{limit}/{courseId}")
     public Result<List<QuestionBankVO>> listQuestionFromBank(@PathVariable Integer page, @PathVariable Integer limit, @PathVariable Integer courseId){
        List<QuestionBankVO> questionBankVOS = teachService.listQuestionFromBank(page,limit,courseId);
        return ResultUtil.ok(questionBankVOS);
    }

    @RequestMapping("/listquestionbylevel/{page}/{limit}/{courseId}/{level}")
    public Result<List<QuestionBankVO>> listQuestionByLevel(@PathVariable Integer page, @PathVariable Integer limit, @PathVariable Integer courseId, @PathVariable Integer level){
        List<QuestionBankVO> questionBankVOS = teachService.listQuestionByLevel(page,limit,courseId,level);
        return ResultUtil.ok(questionBankVOS);
    }

    @RequestMapping("/addquestion/{askId}/{level}")
    public Result<Boolean> addQuestionToBank(@PathVariable Integer askId,@PathVariable Integer level){
        //获取当前登录的用户并判断是否是老师
        User user = UserHolder.getUser();
        Integer type = user.getType();
        if(!type.equals(1)){
            return ResultUtil.fail("权限不足，添加失败！");
        }
        //其余状况
        String addQuestionToBank = teachService.addQuestionToBank(askId, level);
        return ResultUtil.ok(addQuestionToBank);
    }

    @RequestMapping("/addanswer/{answerId}/{recommendedDegree}")
    public Result<Boolean> addAnswerToBank(@PathVariable Integer answerId,@PathVariable Integer recommendedDegree){
        //获取当前登录的用户并判断是否是老师
        User user = UserHolder.getUser();
        Integer type = user.getType();
        if(!type.equals(1)){
            return ResultUtil.fail("权限不足，添加失败！");
        }
        //其余状况
        String addQuestionToBank = teachService.addAnswerToBank(answerId, recommendedDegree);
        return ResultUtil.ok(addQuestionToBank);
    }

    @RequestMapping("/addscore/{userId}/{score}/{courseId}")
    public Result<Boolean> addScore(@PathVariable Integer userId,@PathVariable Integer score,@PathVariable Integer courseId){
        //获取当前登录的用户并判断是否是老师
        User user = UserHolder.getUser();
        Integer type = user.getType();
        if(!type.equals(1)){
            return ResultUtil.fail("权限不足，加分失败！");
        }
        //其余状况
        String addScore = teachService.addScore(userId,score,courseId);
        return ResultUtil.ok(addScore);
    }

    @RequestMapping("/publishquestion/{questionBankIds}")
    public Result<Boolean> publishQuestionFromBank(@PathVariable List<Integer> questionBankIds){
        //获取当前登录的用户并判断是否是老师
        User user = UserHolder.getUser();
        Integer type = user.getType();
        if(!type.equals(1)){
            return ResultUtil.fail("权限不足，无权发布！");
        }
        //其余状况
        String publishQuestionFromBank = teachService.publishQuestionFromBank(questionBankIds);
        return ResultUtil.ok(publishQuestionFromBank);
    }

    @RequestMapping("/publishanswer/{targetId}/{targetType}/{answerBankIds}")
    public Result<Boolean> publishAnswerFromBank(@PathVariable Integer targetId,@PathVariable Integer targetType,@PathVariable List<Integer> answerBankIds){
        //获取当前登录的用户并判断是否是老师
        User user = UserHolder.getUser();
        Integer type = user.getType();
        if(!type.equals(1)){
            return ResultUtil.fail("权限不足，无权发布！");
        }
        //其余状况
        String publishAnswerFromBank = teachService.publishAnswerFromBank(targetId, targetType, answerBankIds);
        return ResultUtil.ok(publishAnswerFromBank);
    }

    @RequestMapping("/listanswerfrombank/{askId}")
    public Result<List<AnswerBankVO>> listAnswerFromBank(@PathVariable Integer askId){
        //获取当前登录的用户并判断是否是老师
        User user = UserHolder.getUser();
        Integer type = user.getType();
        if(!type.equals(1)){
            return ResultUtil.fail("权限不足，无权查询！");
        }
        //其余状况
        List<AnswerBankVO> answerBankVOS = teachService.listAnswerFromBank(askId);
        return ResultUtil.ok(answerBankVOS);
    }
    @RequestMapping("/listanswerbankbyquestionbankid/{questionBankId}")
    public Result<List<AnswerBankVO>> listAnswerBankByQuestionBankId(@PathVariable Integer questionBankId){
        //获取当前登录的用户并判断是否是老师
        User user = UserHolder.getUser();
        Integer type = user.getType();
        if(!type.equals(1)){
            return ResultUtil.fail("权限不足，无权查询！");
        }
        //其余状况
        List<AnswerBankVO> answerBankVOS = teachService.listAnswerBankByQuestionBankId(questionBankId);
        return ResultUtil.ok(answerBankVOS);
    }

    @RequestMapping("/listscore/{courseId}")
    public Result<List<ScoreVO>> listScore(@PathVariable Integer courseId){
        //获取当前登录的用户并判断是否是老师
        User user = UserHolder.getUser();
        Integer type = user.getType();
        if(!type.equals(1)){
            return ResultUtil.fail("权限不足，无权查询！");
        }
        //其余状况
        List<ScoreVO> scoreVOS = teachService.listScore(courseId);
        return ResultUtil.ok(scoreVOS);
    }

}
