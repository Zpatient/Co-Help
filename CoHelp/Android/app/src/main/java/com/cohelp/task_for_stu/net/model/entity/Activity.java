package com.cohelp.task_for_stu.net.model.entity;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;

/**
 * 活动表
 * @TableName activity
 */
@RequiresApi(api = Build.VERSION_CODES.O)
@Data
public class Activity implements Serializable {
    /**
     * 主键
     */
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityTime;

    /**
     * 活动点赞量
     */
    private Integer activityLike;

    /**
     * 活动评论量
     */
    private Integer activityComment;

    /**
     * 活动标签
     */
    private String activityLabel;

    /**
     * 活动收藏量
     */
    private Integer activityCollect;

    /**
     * 活动状态（0：正常 1：异常）
     */
    private Integer activityState;

    /**
     * 活动发布时间
     */
    private LocalDateTime activityCreateTime;

    /**
     * 分类数
     */
    private static final int typeNumber = 1;

    public Activity() {
    }

    public Activity(Integer id, Integer activityOwnerId, String activityTitle, String activityDetail, LocalDateTime activityTime, Integer activityLike, Integer activityComment, String activityLabel, Integer activityCollect, Integer activityState, LocalDateTime activityCreateTime) {
        this.id = id;
        this.activityOwnerId = activityOwnerId;
        this.activityTitle = activityTitle;
        this.activityDetail = activityDetail;
        this.activityTime = activityTime;
        this.activityLike = activityLike;
        this.activityComment = activityComment;
        this.activityLabel = activityLabel;
        this.activityCollect = activityCollect;
        this.activityState = activityState;
        this.activityCreateTime = activityCreateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActivityOwnerId() {
        return activityOwnerId;
    }

    public void setActivityOwnerId(Integer activityOwnerId) {
        this.activityOwnerId = activityOwnerId;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getActivityDetail() {
        return activityDetail;
    }

    public void setActivityDetail(String activityDetail) {
        this.activityDetail = activityDetail;
    }

    public LocalDateTime getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(LocalDateTime activityTime) {
        this.activityTime = activityTime;
    }

    public Integer getActivityLike() {
        return activityLike;
    }

    public void setActivityLike(Integer activityLike) {
        this.activityLike = activityLike;
    }

    public Integer getActivityComment() {
        return activityComment;
    }

    public void setActivityComment(Integer activityComment) {
        this.activityComment = activityComment;
    }

    public String getActivityLabel() {
        return activityLabel;
    }

    public void setActivityLabel(String activityLabel) {
        this.activityLabel = activityLabel;
    }

    public Integer getActivityCollect() {
        return activityCollect;
    }

    public void setActivityCollect(Integer activityCollect) {
        this.activityCollect = activityCollect;
    }

    public Integer getActivityState() {
        return activityState;
    }

    public void setActivityState(Integer activityState) {
        this.activityState = activityState;
    }

    public LocalDateTime getActivityCreateTime() {
        return activityCreateTime;
    }

    public void setActivityCreateTime(LocalDateTime activityCreateTime) {
        this.activityCreateTime = activityCreateTime;
    }

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
            && (this.getActivityComment() == null ? other.getActivityComment() == null : this.getActivityComment().equals(other.getActivityComment()))
            && (this.getActivityLabel() == null ? other.getActivityLabel() == null : this.getActivityLabel().equals(other.getActivityLabel()))
            && (this.getActivityCollect() == null ? other.getActivityCollect() == null : this.getActivityCollect().equals(other.getActivityCollect()))
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
        result = prime * result + ((getActivityComment() == null) ? 0 : getActivityComment().hashCode());
        result = prime * result + ((getActivityLabel() == null) ? 0 : getActivityLabel().hashCode());
        result = prime * result + ((getActivityCollect() == null) ? 0 : getActivityCollect().hashCode());
        result = prime * result + ((getActivityState() == null) ? 0 : getActivityState().hashCode());
        result = prime * result + ((getActivityCreateTime() == null) ? 0 : getActivityCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {

        return "Activity{" +
                "id=" + id +
                ", activityOwnerId=" + activityOwnerId +
                ", activityTitle='" + activityTitle + '\'' +
                ", activityDetail='" + activityDetail + '\'' +
                ", activityTime=" + activityTime +
                ", activityLike=" + activityLike +
                ", activityComment=" + activityComment +
                ", activityLabel='" + activityLabel + '\'' +
                ", activityCollect=" + activityCollect +
                ", activityState=" + activityState +
                ", activityCreateTime=" + activityCreateTime +
                '}';
    }
//    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}