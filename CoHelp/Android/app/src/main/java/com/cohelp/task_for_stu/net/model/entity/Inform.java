package com.cohelp.task_for_stu.net.model.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 举报表
 * @TableName inform
 */
@Data
public class Inform implements Serializable {
    /**
     * 主键
     */
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
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInformType() {
        return informType;
    }

    public void setInformType(String informType) {
        this.informType = informType;
    }

    public Integer getInformerId() {
        return informerId;
    }

    public void setInformerId(Integer informerId) {
        this.informerId = informerId;
    }

    public String getInformContent() {
        return informContent;
    }

    public void setInformContent(String informContent) {
        this.informContent = informContent;
    }

    public Integer getInformedInstanceId() {
        return informedInstanceId;
    }

    public void setInformedInstanceId(Integer informedInstanceId) {
        this.informedInstanceId = informedInstanceId;
    }

    public Integer getInformedInstanceType() {
        return informedInstanceType;
    }

    public void setInformedInstanceType(Integer informedInstanceType) {
        this.informedInstanceType = informedInstanceType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}