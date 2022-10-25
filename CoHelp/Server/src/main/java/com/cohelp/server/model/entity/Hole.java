package com.cohelp.server.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 树洞表
 * @TableName hole
 */
@TableName(value ="hole")
@Data
public class Hole implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 树洞发布者id
     */
    private Integer holeOwnerId;

    /**
     * 树洞主题
     */
    private String holeTitle;

    /**
     * 树洞内容
     */
    private String holeDetail;

    /**
     * 树洞点赞量
     */
    private Integer holeLike;

    /**
     * 树洞收藏量
     */
    private Integer holeCollect;

    /**
     * 树洞评论量
     */
    private Integer holeComment;

    /**
     * 树洞标签
     */
    private String holeLabel;

    /**
     * 树洞状态（0：正常 1：异常）
     */
    private Integer holeState;

    /**
     * 树洞发布时间
     */
    private Date holeCreateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}