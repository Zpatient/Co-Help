package com.cohelp.server.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
     * 举报人id
     */
    private Integer informerId;

    /**
     * 举报类型
     */
    private String informType;

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

    /**
     * 组织id
     */
    private Integer teamId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Inform other = (Inform) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInformerId() == null ? other.getInformerId() == null : this.getInformerId().equals(other.getInformerId()))
            && (this.getInformType() == null ? other.getInformType() == null : this.getInformType().equals(other.getInformType()))
            && (this.getInformContent() == null ? other.getInformContent() == null : this.getInformContent().equals(other.getInformContent()))
            && (this.getInformedInstanceId() == null ? other.getInformedInstanceId() == null : this.getInformedInstanceId().equals(other.getInformedInstanceId()))
            && (this.getInformedInstanceType() == null ? other.getInformedInstanceType() == null : this.getInformedInstanceType().equals(other.getInformedInstanceType()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getTeamId() == null ? other.getTeamId() == null : this.getTeamId().equals(other.getTeamId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInformerId() == null) ? 0 : getInformerId().hashCode());
        result = prime * result + ((getInformType() == null) ? 0 : getInformType().hashCode());
        result = prime * result + ((getInformContent() == null) ? 0 : getInformContent().hashCode());
        result = prime * result + ((getInformedInstanceId() == null) ? 0 : getInformedInstanceId().hashCode());
        result = prime * result + ((getInformedInstanceType() == null) ? 0 : getInformedInstanceType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getTeamId() == null) ? 0 : getTeamId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", informerId=").append(informerId);
        sb.append(", informType=").append(informType);
        sb.append(", informContent=").append(informContent);
        sb.append(", informedInstanceId=").append(informedInstanceId);
        sb.append(", informedInstanceType=").append(informedInstanceType);
        sb.append(", createTime=").append(createTime);
        sb.append(", teamId=").append(teamId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}