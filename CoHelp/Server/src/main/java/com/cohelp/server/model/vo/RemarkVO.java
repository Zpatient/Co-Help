package com.cohelp.server.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RemarkVO {
    /**
     * ID
     */
    private Integer id;
    /**
     * 话题ID
     */
    private Integer topicId;
    /**
     * 评论目标的ID
     */
    private Integer remarkTargetId;
    /**
     * 评论目标的发布者的昵称
     */
    private String remarkTargetName;
    /**
     * 评论链顶层（根）ID
     */
    private Integer topId;
    /**
     * 评论目标是否为话题（0：否 1：是）
     */
    private Integer targetIsTopic;
    /**
     * 评论发布者的昵称
     */
    private String remarkOwnerName;
    /**
     * 评论发布者的头像
     */
    private String remarkOwnerAvatar;
    /**
     * 评论的内容
     */
    private String remarkContent;
    /**
     * 评论点赞量
     */
    private Integer remarkLike;
    /**
     * 评论的时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date remarkTime;
    /**
     * 是否已点赞
     */
    private Integer isLiked;

}
