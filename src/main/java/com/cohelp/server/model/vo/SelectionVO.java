package com.cohelp.server.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class SelectionVO implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 学生昵称
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

    /**
     * 积分
     */
    private Integer score;

}
