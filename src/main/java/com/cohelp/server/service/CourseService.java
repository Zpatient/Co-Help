package com.cohelp.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cohelp.server.model.domain.PageResponse;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Course;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.model.vo.AskVO;
import com.cohelp.server.model.vo.CourseVO;
import com.cohelp.server.model.vo.SelectionVO;
import com.cohelp.server.model.vo.TeachVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author jianping5
* @description 针对表【course】的数据库操作Service
* @createDate 2023-03-01 14:31:07
*/
public interface CourseService extends IService<Course> {

    /**
     * 获取学生指定学年所选的课程
     * @param semester
     * @return
     */
    Result<List<CourseVO>> listCourse(String semester);

    /**
     * 根据课程id，分页查询提问列表
     * @param page
     * @param limit
     * @param courseId
     * @param semester
     * @param condition
     * @return
     */
    Result<List<AskVO>> listAsk(Integer page, Integer limit, Integer courseId, String semester, Integer condition);

    /**
     * 发布提问
     * @param askJson
     * @param files
     * @return
     */
    Result<Boolean> publishAsk(String askJson, MultipartFile[] files);

    /**
     * 发布答案
     * @param answerJson
     * @param files
     * @return
     */
    Result<Boolean> publishAnswer(String answerJson, MultipartFile[] files);

    /**
     * 点赞
     * @param type
     * @param id
     * @return
     */
    Result<Boolean> likeQA(Integer type, Integer id);

    /**
     * 删除提问
     * @param askId
     * @return
     */
    Result<Boolean> deleteAsk(Integer askId);

    /**
     * 收藏提问
     * @param askId
     * @return
     */
    Result<Boolean> collectAsk(Integer askId);


    /**
     * 根据学校 ID，查询课程
     * @param page
     * @param limit
     * @param teamId
     * @return
     */
    Result<PageResponse<Course>> listCourseById(Integer page, Integer limit, Integer teamId);

    /**
     * 显示选课
     * @param page
     * @param limit
     * @param teamId
     * @return
     */
    Result<PageResponse<SelectionVO>> listSelection(Integer page, Integer limit, Integer teamId);

    /**
     * 显示授课
     * @param page
     * @param limit
     * @param teamId
     * @return
     */
    Result<PageResponse<TeachVO>> listTeach(Integer page, Integer limit, Integer teamId);

    /**
     * 显示教师
     * @param page
     * @param limit
     * @param teamId
     * @return
     */
    Result<PageResponse<User>> listTeacher(Integer page, Integer limit, Integer teamId);

    /**
     * 添加教师
     * @param user
     * @return
     */
    Result<Boolean> addTeacher(User user);

    /**
     * 删除课程
     * @param courseId
     * @return
     */
    Result<Boolean> deleteCourse(Integer courseId);

    /**
     * 删除选课
     * @param selectionId
     * @return
     */
    Result<Boolean> deleteSelection(Integer selectionId);

    /**
     * 删除授课
     * @param teachId
     * @return
     */
    Result<Boolean> deleteTeach(Integer teachId);

    /**
     * 获取当前学生对应的学年
     * @return
     */
    Result<List<String>> listSemester();
}
