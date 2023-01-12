package com.cohelp.task_for_stu.net.model.entity;

import java.io.Serializable;
import java.util.Date;


import lombok.Data;

/**
 * 活动评论表
 * @TableName remark_activity
 */
@Data
public class RemarkActivity implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 评论内容
     */
    private String remarkContent;
    /**
     * 评论活动id
     */
    private Integer remarkActivityId;
    /**
     * 评论对象id
     */
    private Integer remarkTargetId;
    /**
     * 评论点赞量
     */
    private Integer remarkLike;
    /**
     * 顶层id（评论链的根id）
     */
    private Integer topId;
    /**
     * 评论对象是否为活动（0：否 1：是）
     */
    private Integer targetIsActivity;
    /**
     * 评论拥有者id
     */
    private Integer remarkOwnerId;
    /**
     * 评论发布时间
     */
    private Date remarkTime;

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
        RemarkActivity other = (RemarkActivity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRemarkContent() == null ? other.getRemarkContent() == null : this.getRemarkContent().equals(other.getRemarkContent()))
            && (this.getRemarkTargetId() == null ? other.getRemarkTargetId() == null : this.getRemarkTargetId().equals(other.getRemarkTargetId()))
            && (this.getRemarkLike() == null ? other.getRemarkLike() == null : this.getRemarkLike().equals(other.getRemarkLike()))
            && (this.getRemarkActivityId() == null ? other.getRemarkActivityId() == null : this.getRemarkActivityId().equals(other.getRemarkActivityId()))
            && (this.getTopId() == null ? other.getTopId() == null : this.getTopId().equals(other.getTopId()))
            && (this.getTargetIsActivity() == null ? other.getTargetIsActivity() == null : this.getTargetIsActivity().equals(other.getTargetIsActivity()))
            && (this.getRemarkOwnerId() == null ? other.getRemarkOwnerId() == null : this.getRemarkOwnerId().equals(other.getRemarkOwnerId()))
            && (this.getRemarkTime() == null ? other.getRemarkTime() == null : this.getRemarkTime().equals(other.getRemarkTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRemarkContent() == null) ? 0 : getRemarkContent().hashCode());
        result = prime * result + ((getRemarkTargetId() == null) ? 0 : getRemarkTargetId().hashCode());
        result = prime * result + ((getRemarkLike() == null) ? 0 : getRemarkLike().hashCode());
        result = prime * result + ((getRemarkActivityId() == null) ? 0 : getRemarkActivityId().hashCode());
        result = prime * result + ((getTopId() == null) ? 0 : getTopId().hashCode());
        result = prime * result + ((getTargetIsActivity() == null) ? 0 : getTargetIsActivity().hashCode());
        result = prime * result + ((getRemarkOwnerId() == null) ? 0 : getRemarkOwnerId().hashCode());
        result = prime * result + ((getRemarkTime() == null) ? 0 : getRemarkTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", remarkContent=").append(remarkContent);
        sb.append(", remarkTargetId=").append(remarkTargetId);
        sb.append(", remarkLike=").append(remarkLike);
        sb.append(", remarkActivityId=").append(remarkActivityId);
        sb.append(", topId=").append(topId);
        sb.append(", targetIsActivity=").append(targetIsActivity);
        sb.append(", remarkOwnerId=").append(remarkOwnerId);
        sb.append(", remarkTime=").append(remarkTime.getYear()+"-"+remarkTime.getSeconds()+"-"+remarkTime.getDay()+" "+remarkTime.getHours()+":"+remarkTime.getMinutes()+":"+remarkTime.getSeconds());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemarkContent() {
        return remarkContent;
    }

    public void setRemarkContent(String remarkContent) {
        this.remarkContent = remarkContent;
    }

    public Integer getRemarkActivityId() {
        return remarkActivityId;
    }

    public void setRemarkActivityId(Integer remarkActivityId) {
        this.remarkActivityId = remarkActivityId;
    }

    public Integer getRemarkTargetId() {
        return remarkTargetId;
    }

    public void setRemarkTargetId(Integer remarkTargetId) {
        this.remarkTargetId = remarkTargetId;
    }

    public Integer getRemarkLike() {
        return remarkLike;
    }

    public void setRemarkLike(Integer remarkLike) {
        this.remarkLike = remarkLike;
    }

    public Integer getTopId() {
        return topId;
    }

    public void setTopId(Integer topId) {
        this.topId = topId;
    }

    public Integer getTargetIsActivity() {
        return targetIsActivity;
    }

    public void setTargetIsActivity(Integer targetIsActivity) {
        this.targetIsActivity = targetIsActivity;
    }

    public Integer getRemarkOwnerId() {
        return remarkOwnerId;
    }

    public void setRemarkOwnerId(Integer remarkOwnerId) {
        this.remarkOwnerId = remarkOwnerId;
    }

    public Date getRemarkTime() {
        return remarkTime;
    }

    public void setRemarkTime(Date remarkTime) {
        this.remarkTime = remarkTime;
    }
}