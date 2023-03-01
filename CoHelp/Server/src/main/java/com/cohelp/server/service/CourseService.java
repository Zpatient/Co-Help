package com.cohelp.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cohelp.server.model.vo.AskVO;
import com.cohelp.server.model.vo.CourseVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

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
     * @param condition
     * @return
     */
    Result<List<AskVO>> listAsk(Integer page, Integer limit, Integer courseId, Integer condition);

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
}
