package com.cohelp.server.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jianping5
 * @createDate 1/3/2023 下午 3:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseVO implements Serializable {

    /**
     * 课程id
     */
    private Integer id;

    /**
     * 课程名
     */
    private String name;

    /**
     * 组织id
     */
    private Integer teamId;

    /**
     * 课程学年
     */
    private String semester;
}
