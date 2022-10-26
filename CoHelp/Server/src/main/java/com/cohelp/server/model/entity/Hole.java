package com.cohelp.server.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import static com.cohelp.server.constant.TypeConstant.ACTIVITY_TYPE;
import static com.cohelp.server.constant.TypeConstant.HOLE_TYPE;

/**
 * 树洞表
 * @TableName hole
 */
@TableName(value ="hole")
@Data
public class Hole implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 树洞发布者id
     */
    private Integer holeOwnerId;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date holeCreateTime;

    /**
     * 分类数
     */
    @TableField(exist = false)
    private static final int typeNumber = HOLE_TYPE;

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