package com.cohelp.task_for_stu.net.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 活动视图体
 *
 * @author jianping5
 * @createDate 2022/11/2 18:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动id
     */
    private Integer id;

    /**
     * 活动发布者id
     */
    private Integer activityOwnerId;

    /**
     * 活动发布者昵称
     */
    private String userName;

    /**
     * 活动发布者头像
     */
    private Integer avatar;

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

    @Override
    public String toString() {
        return "ActivityVO{" +
                "id=" + id +
                ", activityOwnerId=" + activityOwnerId +
                ", userName='" + userName + '\'' +
                ", avatar=" + avatar +
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
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

    public ActivityVO(Integer id, Integer activityOwnerId, String userName, Integer avatar, String activityTitle, String activityDetail, LocalDateTime activityTime, Integer activityLike, Integer activityComment, String activityLabel, Integer activityCollect, Integer activityState, LocalDateTime activityCreateTime) {
        this.id = id;
        this.activityOwnerId = activityOwnerId;
        this.userName = userName;
        this.avatar = avatar;
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
}
