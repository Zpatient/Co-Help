package com.cohelp.server.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 活动视图体
 *
 * @author jianping5
 * @createDate 2022/11/2 18:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动id
     */
    private Integer id;

    /**
     * 活动发布者id
     */
    private Integer activityOwnerId;

    /**
     * 活动发布者昵称
     */
    private String userName;

    /**
     * 活动发布者头像
     */
    private Integer avatar;

    /**
     * 活动标题
     */
    private String activityTitle;

    /**
     * 活动内容
     */
    private String activityDetail;

    /**
     * 活动时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityTime;

    /**
     * 活动点赞量
     */
    private Integer activityLike;

    /**
     * 活动评论量
     */
    private Integer activityComment;

    /**
     * 活动标签
     */
    private String activityLabel;

    /**
     * 活动收藏量
     */
    private Integer activityCollect;

    /**
     * 活动状态（0：正常 1：异常）
     */
    private Integer activityState;

    /**
     * 活动发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityCreateTime;

}
