package com.cohelp.task_for_stu.net.model.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 评论点赞记录表
 * @TableName remark_like
 */
@Data
public class RemarkLike implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 评论类型
     */
    private Integer remarkType;

    /**
     * 是否点赞
     */
    private Integer isLiked;

    /**
     * 评论id
     */
    private Integer remarkId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRemarkType() {
        return remarkType;
    }

    public void setRemarkType(Integer remarkType) {
        this.remarkType = remarkType;
    }

    public Integer getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Integer isLiked) {
        this.isLiked = isLiked;
    }

    public Integer getRemarkId() {
        return remarkId;
    }

    public void setRemarkId(Integer remarkId) {
        this.remarkId = remarkId;
    }
}