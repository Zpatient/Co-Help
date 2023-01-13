package com.cohelp.task_for_stu.bean;

import java.io.Serializable;
import java.util.Date;

public class BaseTask implements Serializable {
    Long id;
    Long puid;
    Long duid;
    Long rid;
    Date postDate;
    Date startDate;
    Date endDate;
    Byte jugFlag;
    Integer reward;
    String title;
    String contentDesc;
    String currentState;
    String stateMsg;
    @Override
    public String toString() {
        return "BaseTask{" +
                "id=" + id +
                ", pUid=" + puid +
                ", dUid=" + duid +
                ", postDate=" + postDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", jugFlag=" + jugFlag +
                ", reward=" + reward +
                ", title='" + title + '\'' +
                ", desc='" + contentDesc + '\'' +
                ", state='" + currentState + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPuid() {
        return puid;
    }

    public void setPuid(Long puid) {
        this.puid = puid;
    }

    public Long getDuid() {
        return duid;
    }

    public void setDuid(Long duid) {
        this.duid = duid;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Byte getJugFlag() {
        return jugFlag;
    }

    public void setJugFlag(Byte jugFlag) {
        this.jugFlag = jugFlag;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getStateMsg() {
        return stateMsg;
    }

    public void setStateMsg(String stateMsg) {
        this.stateMsg = stateMsg;
    }
}
