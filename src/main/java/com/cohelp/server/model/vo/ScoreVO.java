package com.cohelp.server.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zgy
 * @create 2023-03-04 23:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreVO implements Serializable {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 积分
     */
    private Integer score;
    /**
     * 课程名
     */
    private String courseName;
    /**
     * 学期
     */
    private String semester;
}
