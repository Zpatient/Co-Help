package com.cohelp.task_for_stu.net.model.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;


/**
 * 树洞评论表
 * @TableName remark_hole
 */
@Data
public class RemarkHole implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 评论内容
     */
    private String remarkContent;
    /**
     * 评论树洞id
     */
    private Integer remarkHoleId;
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
     * 评论对象是否为树洞（0：否 1：是）
     */
    private Integer targetIsHole;
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
        RemarkHole other = (RemarkHole) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRemarkContent() == null ? other.getRemarkContent() == null : this.getRemarkContent().equals(other.getRemarkContent()))
            && (this.getRemarkHoleId() == null ? other.getRemarkHoleId() == null : this.getRemarkHoleId().equals(other.getRemarkHoleId()))
            && (this.getRemarkLike() == null ? other.getRemarkLike() == null : this.getRemarkLike().equals(other.getRemarkLike()))
            && (this.getRemarkTargetId() == null ? other.getRemarkTargetId() == null : this.getRemarkTargetId().equals(other.getRemarkTargetId()))
            && (this.getTopId() == null ? other.getTopId() == null : this.getTopId().equals(other.getTopId()))
            && (this.getTargetIsHole() == null ? other.getTargetIsHole() == null : this.getTargetIsHole().equals(other.getTargetIsHole()))
            && (this.getRemarkOwnerId() == null ? other.getRemarkOwnerId() == null : this.getRemarkOwnerId().equals(other.getRemarkOwnerId()))
            && (this.getRemarkTime() == null ? other.getRemarkTime() == null : this.getRemarkTime().equals(other.getRemarkTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRemarkContent() == null) ? 0 : getRemarkContent().hashCode());
        result = prime * result + ((getRemarkHoleId() == null) ? 0 : getRemarkHoleId().hashCode());
        result = prime * result + ((getRemarkLike() == null) ? 0 : getRemarkLike().hashCode());
        result = prime * result + ((getRemarkTargetId() == null) ? 0 : getRemarkTargetId().hashCode());
        result = prime * result + ((getTopId() == null) ? 0 : getTopId().hashCode());
        result = prime * result + ((getTargetIsHole() == null) ? 0 : getTargetIsHole().hashCode());
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
        sb.append(", remarkHoleId=").append(remarkHoleId);
        sb.append(", remarkLike=").append(remarkLike);
        sb.append(", remarkTargetId=").append(remarkTargetId);
        sb.append(", topId=").append(topId);
        sb.append(", targetIsHole=").append(targetIsHole);
        sb.append(", remarkOwnerId=").append(remarkOwnerId);
        sb.append(", remarkTime=").append(remarkTime);
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

    public Integer getRemarkHoleId() {
        return remarkHoleId;
    }

    public void setRemarkHoleId(Integer remarkHoleId) {
        this.remarkHoleId = remarkHoleId;
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

    public Integer getTargetIsHole() {
        return targetIsHole;
    }

    public void setTargetIsHole(Integer targetIsHole) {
        this.targetIsHole = targetIsHole;
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