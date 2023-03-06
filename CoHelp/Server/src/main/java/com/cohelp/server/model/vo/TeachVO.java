package com.cohelp.server.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jianping5
 * @createDate 3/3/2023 下午 8:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeachVO implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 教师 id
     */
    private Integer teacherId;

    /**
     * 教师姓名
     */
    private String userName;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 学年
     */
    private String semester;


}
