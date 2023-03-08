package com.cohelp.server.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author zgy
 * @create 2023-03-03 23:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionBankVO implements Serializable {

    private Integer id;
    /**
     * 题干
     */
    private String content;

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 难度（1-5）
     */
    private Integer level;

    /**
     * 难度（很容易，较容易，适中，较困难，很困难）
     */
    private String difficulty;

    /**
     * 题目相关图片
     */
    private List<String> imageUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
