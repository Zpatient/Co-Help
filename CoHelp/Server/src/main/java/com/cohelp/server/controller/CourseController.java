package com.cohelp.server.controller;

import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.vo.AskVO;
import com.cohelp.server.model.vo.CourseVO;
import com.cohelp.server.service.CourseService;
import com.cohelp.server.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

import static com.cohelp.server.constant.StatusCode.ERROR_PARAMS;

/**
 * @author jianping5
 * @createDate 1/3/2023 下午 3:42
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    @GetMapping("/list/{semester}")
    public Result<List<CourseVO>> listCourse(@PathVariable String semester) {
        return courseService.listCourse(semester);
    }

    @GetMapping("list/ask/{page}/{limit}/{courseId}/{condition}")
    public Result<List<AskVO>> listAsk(@PathVariable Integer page, @PathVariable Integer limit, @PathVariable Integer courseId, @PathVariable Integer condition) {
        return courseService.listAsk(page, limit, courseId, condition);
    }

    @PostMapping("/ask")
    public Result<Boolean> publishAsk(@RequestParam(name="ask") String askJson,
                                           @RequestParam(name="file",required = false) MultipartFile[] files) {
        return courseService.publishAsk(askJson, files);
    }

    @PostMapping("/answer")
    public Result<Boolean> publishAnswer(@RequestParam(name="answer") String answerJson,
                                      @RequestParam(name="file",required = false) MultipartFile[] files) {
        return courseService.publishAnswer(answerJson, files);
    }

    @PostMapping("/like/{type}/{id}")
    public Result<Boolean> likeTopic(@PathVariable Integer type, @PathVariable Integer id) {
        if (type == null || type <= 0 || id == null || id <= 0) {
            return ResultUtil.fail(ERROR_PARAMS);
        }
        return courseService.likeQA(type, id);
    }

    @PostMapping("/collect/{id}")
    public Result<Boolean> collectAsk(@PathVariable Integer askId) {
        return courseService.collectAsk(askId);
    }


    @PostMapping("/deleteAsk/{id}")
    public Result<Boolean> deleteAsk(@PathVariable Integer askId) {
        return courseService.deleteAsk(askId);
    }
}
