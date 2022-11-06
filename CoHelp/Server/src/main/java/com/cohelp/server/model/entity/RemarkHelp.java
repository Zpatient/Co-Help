package com.cohelp.server.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 互助评论表
 * @TableName remark_help
 */
@TableName(value ="remark_help")
@Data
public class RemarkHelp implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 评论内容
     */
    private String remarkContent;
    /**
     * 评论互助id
     */
    private Integer remarkHelpId;
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
     * 评论对象是否为互助（0：否 1：是）
     */
    private Integer targetIsHelp;
    /**
     * 评论拥有者id
     */
    private Integer remarkOwnerId;
    /**
     * 评论发布时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date remarkTime;
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
        RemarkHelp other = (RemarkHelp) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRemarkContent() == null ? other.getRemarkContent() == null : this.getRemarkContent().equals(other.getRemarkContent()))
            && (this.getRemarkHelpId() == null ? other.getRemarkHelpId() == null : this.getRemarkHelpId().equals(other.getRemarkHelpId()))
            && (this.getRemarkLike() == null ? other.getRemarkLike() == null : this.getRemarkLike().equals(other.getRemarkLike()))
            && (this.getRemarkTargetId() == null ? other.getRemarkTargetId() == null : this.getRemarkTargetId().equals(other.getRemarkTargetId()))
            && (this.getTopId() == null ? other.getTopId() == null : this.getTopId().equals(other.getTopId()))
            && (this.getTargetIsHelp() == null ? other.getTargetIsHelp() == null : this.getTargetIsHelp().equals(other.getTargetIsHelp()))
            && (this.getRemarkOwnerId() == null ? other.getRemarkOwnerId() == null : this.getRemarkOwnerId().equals(other.getRemarkOwnerId()))
            && (this.getRemarkTime() == null ? other.getRemarkTime() == null : this.getRemarkTime().equals(other.getRemarkTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRemarkContent() == null) ? 0 : getRemarkContent().hashCode());
        result = prime * result + ((getRemarkHelpId() == null) ? 0 : getRemarkHelpId().hashCode());
        result = prime * result + ((getRemarkLike() == null) ? 0 : getRemarkLike().hashCode());
        result = prime * result + ((getRemarkTargetId() == null) ? 0 : getRemarkTargetId().hashCode());
        result = prime * result + ((getTopId() == null) ? 0 : getTopId().hashCode());
        result = prime * result + ((getTargetIsHelp() == null) ? 0 : getTargetIsHelp().hashCode());
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
        sb.append(", remarkHelpId=").append(remarkHelpId);
        sb.append(", remarkLike=").append(remarkLike);
        sb.append(", remarkTargetId=").append(remarkTargetId);
        sb.append(", topId=").append(topId);
        sb.append(", targetIsHelp=").append(targetIsHelp);
        sb.append(", remarkOwnerId=").append(remarkOwnerId);
        sb.append(", remarkTime=").append(remarkTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}