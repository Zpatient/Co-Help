package com.cohelp.server.controller;

import com.alibaba.excel.EasyExcel;
import com.cohelp.server.listener.ExcelCourseListener;
import com.cohelp.server.listener.ExcelSelectionListener;
import com.cohelp.server.listener.ExcelTeachListener;
import com.cohelp.server.listener.ExcelTeacherListener;
import com.cohelp.server.model.domain.PageResponse;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.*;
import com.cohelp.server.model.vo.AskVO;
import com.cohelp.server.model.vo.CourseVO;
import com.cohelp.server.model.vo.SelectionVO;
import com.cohelp.server.model.vo.TeachVO;
import com.cohelp.server.service.CourseService;
import com.cohelp.server.service.SelectionService;
import com.cohelp.server.service.TeachService;
import com.cohelp.server.service.UserService;
import com.cohelp.server.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
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

    @Resource
    private SelectionService selectionService;

    @Resource
    private UserService userService;

    @Resource
    private TeachService teachService;

    @GetMapping("/list/{semester}")
    public Result<List<CourseVO>> listCourse(@PathVariable String semester) {
        return courseService.listCourse(semester);
    }

    @GetMapping("list/ask/{page}/{limit}/{courseId}/{semester}/{condition}")
    public Result<List<AskVO>> listAsk(@PathVariable Integer page, @PathVariable Integer limit, @PathVariable Integer courseId,
                                       @PathVariable String semester, @PathVariable Integer condition) {
        return courseService.listAsk(page, limit, courseId, semester, condition);
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
    public Result<Boolean> likeQA(@PathVariable Integer type, @PathVariable Integer id) {
        if (type == null || type <= 0 || id == null || id <= 0) {
            return ResultUtil.fail(ERROR_PARAMS);
        }
        return courseService.likeQA(type, id);
    }

    @PostMapping("/deleteAsk/{askId}")
    public Result<Boolean> deleteAsk(@PathVariable Integer askId) {
        return courseService.deleteAsk(askId);
    }

    @PostMapping("/collect/{askId}")
    public Result<Boolean> collectAsk(@PathVariable Integer askId) {
        return courseService.collectAsk(askId);
    }

    @PostMapping("/excel/course")
    public Result<String> excelImportCourse(@RequestParam("file")MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), Course.class, new ExcelCourseListener(courseService)).sheet().doRead();
        return ResultUtil.ok("success");
    }

    @PostMapping("/excel/selection")
    public Result<String> excelImportSelection(@RequestParam("file")MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), Selection.class, new ExcelSelectionListener(selectionService)).sheet().doRead();
        return ResultUtil.ok("success");
    }

    @PostMapping("/excel/teach")
    public Result<String> excelImportTeach(@RequestParam("file")MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), Teach.class, new ExcelTeachListener(teachService)).sheet().doRead();
        return ResultUtil.ok("success");
    }

    @PostMapping("/excel/teacher")
    public Result<String> excelImportUser(@RequestParam("file")MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), User.class, new ExcelTeacherListener(userService)).sheet().doRead();
        return ResultUtil.ok("success");
    }

    @GetMapping("/list/course/{page}/{limit}/{teamId}")
    public Result<PageResponse<Course>> listCourseById(@PathVariable Integer page, @PathVariable Integer limit, @PathVariable Integer teamId) {
        if (teamId == null || teamId <= 0) {
            return ResultUtil.fail("参数错误");
        }
        return courseService.listCourseById(page, limit, teamId);
    }

    @GetMapping("/list/selection/{page}/{limit}/{teamId}")
    public Result<PageResponse<SelectionVO>> listSelection(@PathVariable Integer page, @PathVariable Integer limit, @PathVariable Integer teamId) {
        if (teamId == null || teamId <= 0) {
            return ResultUtil.fail("参数错误");
        }
        return courseService.listSelection(page, limit, teamId);
    }

    @GetMapping("/list/teach/{page}/{limit}/{teamId}")
    public Result<PageResponse<TeachVO>> listTeach(@PathVariable Integer page, @PathVariable Integer limit, @PathVariable Integer teamId) {
        if (teamId == null || teamId <= 0) {
            return ResultUtil.fail("参数错误");
        }
        return courseService.listTeach(page, limit, teamId);
    }

    @GetMapping("/list/teacher/{page}/{limit}/{teamId}")
    public Result<PageResponse<User>> listTeacher(@PathVariable Integer page, @PathVariable Integer limit, @PathVariable Integer teamId) {
        if (teamId == null || teamId <= 0) {
            return ResultUtil.fail("参数错误");
        }
        return courseService.listTeacher(page, limit, teamId);
    }

    @PostMapping("/add/teacher")
    public Result<Boolean> addTeacher(@RequestBody User user) {
        if (user == null) {
            return ResultUtil.fail("参数错误");
        }
        return courseService.addTeacher(user);
    }

}
