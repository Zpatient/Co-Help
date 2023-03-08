package com.cohelp.server.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 评论点赞记录表
 * @TableName remark_like
 */
@TableName(value ="remark_like")
@Data
public class RemarkLike implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}