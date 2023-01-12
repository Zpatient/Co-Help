package com.cohelp.task_for_stu.net.model.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;


/**
 * 树洞表
 * @TableName hole
 */
@Data
public class Hole implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 树洞发布者id
     */
    private Integer holeOwnerId;

    public Hole() {
    }

    public Hole(Integer id, Integer holeOwnerId, String holeTitle, String holeDetail, Integer holeLike, Integer holeCollect, Integer holeComment, String holeLabel, Integer holeState, Date holeCreateTime) {
        this.id = id;
        this.holeOwnerId = holeOwnerId;
        this.holeTitle = holeTitle;
        this.holeDetail = holeDetail;
        this.holeLike = holeLike;
        this.holeCollect = holeCollect;
        this.holeComment = holeComment;
        this.holeLabel = holeLabel;
        this.holeState = holeState;
        this.holeCreateTime = holeCreateTime;
    }

    /**
     * 树洞主题
     */
    private String holeTitle;

    /**
     * 树洞内容
     */
    private String holeDetail;

    /**
     * 树洞点赞量
     */
    private Integer holeLike;

    /**
     * 树洞收藏量
     */
    private Integer holeCollect;

    /**
     * 树洞评论量
     */
    private Integer holeComment;

    /**
     * 树洞标签
     */
    private String holeLabel;

    /**
     * 树洞状态（0：正常 1：异常）
     */
    private Integer holeState;

    /**
     * 树洞发布时间
     */
    private Date holeCreateTime;

    /**
     * 分类数
     */
    private static final int typeNumber = 3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHoleOwnerId() {
        return holeOwnerId;
    }

    public void setHoleOwnerId(Integer holeOwnerId) {
        this.holeOwnerId = holeOwnerId;
    }

    public String getHoleTitle() {
        return holeTitle;
    }

    public void setHoleTitle(String holeTitle) {
        this.holeTitle = holeTitle;
    }

    public String getHoleDetail() {
        return holeDetail;
    }

    public void setHoleDetail(String holeDetail) {
        this.holeDetail = holeDetail;
    }

    public Integer getHoleLike() {
        return holeLike;
    }

    public void setHoleLike(Integer holeLike) {
        this.holeLike = holeLike;
    }

    public Integer getHoleCollect() {
        return holeCollect;
    }

    public void setHoleCollect(Integer holeCollect) {
        this.holeCollect = holeCollect;
    }

    public Integer getHoleComment() {
        return holeComment;
    }

    public void setHoleComment(Integer holeComment) {
        this.holeComment = holeComment;
    }

    public String getHoleLabel() {
        return holeLabel;
    }

    public void setHoleLabel(String holeLabel) {
        this.holeLabel = holeLabel;
    }

    public Integer getHoleState() {
        return holeState;
    }

    public void setHoleState(Integer holeState) {
        this.holeState = holeState;
    }

    public Date getHoleCreateTime() {
        return holeCreateTime;
    }

    public void setHoleCreateTime(Date holeCreateTime) {
        this.holeCreateTime = holeCreateTime;
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
        Hole other = (Hole) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getHoleOwnerId() == null ? other.getHoleOwnerId() == null : this.getHoleOwnerId().equals(other.getHoleOwnerId()))
            && (this.getHoleTitle() == null ? other.getHoleTitle() == null : this.getHoleTitle().equals(other.getHoleTitle()))
            && (this.getHoleDetail() == null ? other.getHoleDetail() == null : this.getHoleDetail().equals(other.getHoleDetail()))
            && (this.getHoleLike() == null ? other.getHoleLike() == null : this.getHoleLike().equals(other.getHoleLike()))
            && (this.getHoleCollect() == null ? other.getHoleCollect() == null : this.getHoleCollect().equals(other.getHoleCollect()))
            && (this.getHoleComment() == null ? other.getHoleComment() == null : this.getHoleComment().equals(other.getHoleComment()))
            && (this.getHoleLabel() == null ? other.getHoleLabel() == null : this.getHoleLabel().equals(other.getHoleLabel()))
            && (this.getHoleState() == null ? other.getHoleState() == null : this.getHoleState().equals(other.getHoleState()))
            && (this.getHoleCreateTime() == null ? other.getHoleCreateTime() == null : this.getHoleCreateTime().equals(other.getHoleCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getHoleOwnerId() == null) ? 0 : getHoleOwnerId().hashCode());
        result = prime * result + ((getHoleTitle() == null) ? 0 : getHoleTitle().hashCode());
        result = prime * result + ((getHoleDetail() == null) ? 0 : getHoleDetail().hashCode());
        result = prime * result + ((getHoleLike() == null) ? 0 : getHoleLike().hashCode());
        result = prime * result + ((getHoleCollect() == null) ? 0 : getHoleCollect().hashCode());
        result = prime * result + ((getHoleComment() == null) ? 0 : getHoleComment().hashCode());
        result = prime * result + ((getHoleLabel() == null) ? 0 : getHoleLabel().hashCode());
        result = prime * result + ((getHoleState() == null) ? 0 : getHoleState().hashCode());
        result = prime * result + ((getHoleCreateTime() == null) ? 0 : getHoleCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", holeOwnerId=").append(holeOwnerId);
        sb.append(", holeTitle=").append(holeTitle);
        sb.append(", holeDetail=").append(holeDetail);
        sb.append(", holeLike=").append(holeLike);
        sb.append(", holeCollect=").append(holeCollect);
        sb.append(", holeComment=").append(holeComment);
        sb.append(", holeLabel=").append(holeLabel);
        sb.append(", holeState=").append(holeState);
        sb.append(", holeCreateTime=").append(holeCreateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}