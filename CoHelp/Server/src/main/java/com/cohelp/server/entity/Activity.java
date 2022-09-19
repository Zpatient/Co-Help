package com.cohelp.server.entity;

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
        Activity other = (Activity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getActivityOwnerId() == null ? other.getActivityOwnerId() == null : this.getActivityOwnerId().equals(other.getActivityOwnerId()))
            && (this.getActivityTitle() == null ? other.getActivityTitle() == null : this.getActivityTitle().equals(other.getActivityTitle()))
            && (this.getActivityDetail() == null ? other.getActivityDetail() == null : this.getActivityDetail().equals(other.getActivityDetail()))
            && (this.getActivityTime() == null ? other.getActivityTime() == null : this.getActivityTime().equals(other.getActivityTime()))
            && (this.getActivityLike() == null ? other.getActivityLike() == null : this.getActivityLike().equals(other.getActivityLike()))
            && (this.getActivityState() == null ? other.getActivityState() == null : this.getActivityState().equals(other.getActivityState()))
            && (this.getActivityCreateTime() == null ? other.getActivityCreateTime() == null : this.getActivityCreateTime().equals(other.getActivityCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getActivityOwnerId() == null) ? 0 : getActivityOwnerId().hashCode());
        result = prime * result + ((getActivityTitle() == null) ? 0 : getActivityTitle().hashCode());
        result = prime * result + ((getActivityDetail() == null) ? 0 : getActivityDetail().hashCode());
        result = prime * result + ((getActivityTime() == null) ? 0 : getActivityTime().hashCode());
        result = prime * result + ((getActivityLike() == null) ? 0 : getActivityLike().hashCode());
        result = prime * result + ((getActivityState() == null) ? 0 : getActivityState().hashCode());
        result = prime * result + ((getActivityCreateTime() == null) ? 0 : getActivityCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", activityOwnerId=").append(activityOwnerId);
        sb.append(", activityTitle=").append(activityTitle);
        sb.append(", activityDetail=").append(activityDetail);
        sb.append(", activityTime=").append(activityTime);
        sb.append(", activityLike=").append(activityLike);
        sb.append(", activityState=").append(activityState);
        sb.append(", activityCreateTime=").append(activityCreateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}