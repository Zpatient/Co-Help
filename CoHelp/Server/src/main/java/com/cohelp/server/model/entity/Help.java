package com.cohelp.server.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 互助表
 * @TableName help
 */
@TableName(value ="help")
@Data
public class Help implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 互助发布者id
     */
    private Integer helpOwnerId;

    /**
     * 互助标题
     */
    private String helpTitle;

    /**
     * 互助内容
     */
    private String helpDetail;

    /**
     * 互助有/无偿
     */
    private Integer helpPaid;

    /**
     * 互助点赞量
     */
    private Integer helpLike;

    /**
     * 互助收藏量
     */
    private Integer helpCollect;

    /**
     * 互助评论量
     */
    private Integer helpComment;

    /**
     * 互助标签
     */
    private String helpLabel;

    /**
     * 互助状态（0：正常 1：异常）
     */
    private Integer helpState;

    /**
     * 互助发布时间
     */
    private Date helpCreateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}