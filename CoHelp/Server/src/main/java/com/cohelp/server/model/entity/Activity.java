package com.cohelp.server.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 活动表
 * @TableName activity
 */
@TableName(value ="activity")
@Data
public class Activity implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 活动发布者id
     */
    private Integer activityOwnerId;

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
    private Date activityTime;

    /**
     * 活动点赞量
     */
    private Integer activityLike;

    /**
     * 活动状态（0：正常 1：异常）
     */
    private Integer activityState;

    /**
     * 活动发布时间
     */
    private Date activityCreateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}