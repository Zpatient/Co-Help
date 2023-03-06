package com.cohelp.server.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName answer
 */
@TableName(value ="answer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 内容
     */
    private String content;

    /**
     * 题目id
     */
    private Integer askId;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 发布者id
     */
    private Integer publisherId;

    /**
     * 点赞量
     */
    private Integer likeCount;

    /**
     * 回答目标id
     */
    private Integer answerTargetId;

    /**
     * 回答目标类型
     */
    private Integer answerTargetType;

    /**
     * 是否已加入答案库（0：未加入 1：已加入）
     */
    private Integer isAdded;

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
        Answer other = (Answer) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getAskId() == null ? other.getAskId() == null : this.getAskId().equals(other.getAskId()))
            && (this.getPublishTime() == null ? other.getPublishTime() == null : this.getPublishTime().equals(other.getPublishTime()))
            && (this.getPublisherId() == null ? other.getPublisherId() == null : this.getPublisherId().equals(other.getPublisherId()))
            && (this.getLikeCount() == null ? other.getLikeCount() == null : this.getLikeCount().equals(other.getLikeCount()))
            && (this.getAnswerTargetId() == null ? other.getAnswerTargetId() == null : this.getAnswerTargetId().equals(other.getAnswerTargetId()))
            && (this.getAnswerTargetType() == null ? other.getAnswerTargetType() == null : this.getAnswerTargetType().equals(other.getAnswerTargetType()))
            && (this.getIsAdded() == null ? other.getIsAdded() == null : this.getIsAdded().equals(other.getIsAdded()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getAskId() == null) ? 0 : getAskId().hashCode());
        result = prime * result + ((getPublishTime() == null) ? 0 : getPublishTime().hashCode());
        result = prime * result + ((getPublisherId() == null) ? 0 : getPublisherId().hashCode());
        result = prime * result + ((getLikeCount() == null) ? 0 : getLikeCount().hashCode());
        result = prime * result + ((getAnswerTargetId() == null) ? 0 : getAnswerTargetId().hashCode());
        result = prime * result + ((getAnswerTargetType() == null) ? 0 : getAnswerTargetType().hashCode());
        result = prime * result + ((getIsAdded() == null) ? 0 : getIsAdded().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", content=").append(content);
        sb.append(", askId=").append(askId);
        sb.append(", publishTime=").append(publishTime);
        sb.append(", publisherId=").append(publisherId);
        sb.append(", likeCount=").append(likeCount);
        sb.append(", answerTargetId=").append(answerTargetId);
        sb.append(", answerTargetType=").append(answerTargetType);
        sb.append(", isAdded=").append(isAdded);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}