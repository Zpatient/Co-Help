package com.cohelp.server.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 举报表
 * @TableName inform
 */
@TableName(value ="inform")
@Data
public class Inform implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 举报类型
     */
    private String informType;

    /**
     * 举报人id
     */
    private Integer informerId;

    /**
     * 举报内容
     */
    private String informContent;

    /**
     * 被举报对象的id
     */
    private Integer informedInstanceId;

    /**
     * 举报对象的类型
     */
    private Integer informedInstanceType;

    /**
     * 举报时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}