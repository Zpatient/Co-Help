package com.cohelp.task_for_stu.net.model.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

/**
 * 浏览记录表
 * @TableName history
 */
@Data
public class History implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 话题类型（1：活动 2：互助 3：树洞）
     */
    private Integer topicType;

    /**
     * 话题id
     */
    private Integer topicId;

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

    public Integer getTopicType() {
        return topicType;
    }

    public void setTopicType(Integer topicType) {
        this.topicType = topicType;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public LocalDateTime getViewTime() {
        return viewTime;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public void setViewTime(LocalDateTime viewTime) {
        this.viewTime = viewTime;
    }

    /**
     * 查看/浏览时间
     */
    private LocalDateTime viewTime;

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
        History other = (History) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getTopicType() == null ? other.getTopicType() == null : this.getTopicType().equals(other.getTopicType()))
            && (this.getTopicId() == null ? other.getTopicId() == null : this.getTopicId().equals(other.getTopicId()))
            && (this.getViewTime() == null ? other.getViewTime() == null : this.getViewTime().equals(other.getViewTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getTopicType() == null) ? 0 : getTopicType().hashCode());
        result = prime * result + ((getTopicId() == null) ? 0 : getTopicId().hashCode());
        result = prime * result + ((getViewTime() == null) ? 0 : getViewTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", topicType=").append(topicType);
        sb.append(", topicId=").append(topicId);
        sb.append(", viewTime=").append(viewTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}