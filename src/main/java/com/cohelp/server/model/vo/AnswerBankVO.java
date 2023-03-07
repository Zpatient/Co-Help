package com.cohelp.server.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author zgy
 * @create 2023-03-04 20:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerBankVO implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 答案
     */
    private String content;

    /**
     * 推荐度
     */
    private Integer recommendedDegree;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 问题id
     */
    private Integer questionId;

    /**
     * 对应回答id
     */
    private Integer answerId;

    /**
     * 答案相关图片
     */
    private List<String> imageUrl;


    private static final long serialVersionUID = 1L;
}
