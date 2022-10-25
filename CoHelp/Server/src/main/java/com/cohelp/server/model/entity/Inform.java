package com.cohelp.server.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
     * 举报对象的类型（0：用户 1：活动 2：互助 3：树洞）
     */
    private Integer informedInstanceType;

    /**
     * 举报时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}