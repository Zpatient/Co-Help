package com.cohelp.server.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author jianping5
 * @createDate 1/3/2023 下午 3:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AskVO implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 题干
     */
    private String question;

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 学年
     */
    private String semester;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 点赞量
     */
    private Integer likeCount;

    /**
     * 发布者id
     */
    private Integer publisherId;

    /**
     * 活动发布者昵称
     */
    private String userName;

    /**
     * 活动发布者头像 Url
     */
    private String avatarUrl;

    /**
     * 图片
     */
    private List<String> imageUrl;

    /**
     * 是否被点赞
     */
    private Integer isLiked;
    /**
     * 是否被收藏
     */
    private Integer isCollected;

    /**
     * 收藏量
     */
    private Integer collectCount;

    /**
     * 答案量
     */
    private Integer answerCount;
}
