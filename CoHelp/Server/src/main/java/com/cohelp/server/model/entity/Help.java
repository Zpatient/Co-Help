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
import static com.cohelp.server.constant.TypeConstant.HELP_TYPE;

/**
 * 互助表
 * @TableName help
 */
@TableName(value ="help")
@Data
public class Help implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 互助发布者id
     */
    private Integer helpOwnerId;

    /**
     * 互助标题
     */
    private String helpTitle;

    /**
     * 互助内容
     */
    private String helpDetail;

    /**
     * 互助有/无偿
     */
    private Integer helpPaid;

    /**
     * 互助点赞量
     */
    private Integer helpLike;

    /**
     * 互助收藏量
     */
    private Integer helpCollect;

    /**
     * 互助评论量
     */
    private Integer helpComment;

    /**
     * 互助标签
     */
    private String helpLabel;

    /**
     * 互助状态（0：正常 1：异常）
     */
    private Integer helpState;

    /**
     * 互助发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date helpCreateTime;

    /**
     * 分类数
     */
    @TableField(exist = false)
    private static final int typeNumber = HELP_TYPE;

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
        Help other = (Help) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getHelpOwnerId() == null ? other.getHelpOwnerId() == null : this.getHelpOwnerId().equals(other.getHelpOwnerId()))
            && (this.getHelpTitle() == null ? other.getHelpTitle() == null : this.getHelpTitle().equals(other.getHelpTitle()))
            && (this.getHelpDetail() == null ? other.getHelpDetail() == null : this.getHelpDetail().equals(other.getHelpDetail()))
            && (this.getHelpPaid() == null ? other.getHelpPaid() == null : this.getHelpPaid().equals(other.getHelpPaid()))
            && (this.getHelpLike() == null ? other.getHelpLike() == null : this.getHelpLike().equals(other.getHelpLike()))
            && (this.getHelpCollect() == null ? other.getHelpCollect() == null : this.getHelpCollect().equals(other.getHelpCollect()))
            && (this.getHelpComment() == null ? other.getHelpComment() == null : this.getHelpComment().equals(other.getHelpComment()))
            && (this.getHelpLabel() == null ? other.getHelpLabel() == null : this.getHelpLabel().equals(other.getHelpLabel()))
            && (this.getHelpState() == null ? other.getHelpState() == null : this.getHelpState().equals(other.getHelpState()))
            && (this.getHelpCreateTime() == null ? other.getHelpCreateTime() == null : this.getHelpCreateTime().equals(other.getHelpCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getHelpOwnerId() == null) ? 0 : getHelpOwnerId().hashCode());
        result = prime * result + ((getHelpTitle() == null) ? 0 : getHelpTitle().hashCode());
        result = prime * result + ((getHelpDetail() == null) ? 0 : getHelpDetail().hashCode());
        result = prime * result + ((getHelpPaid() == null) ? 0 : getHelpPaid().hashCode());
        result = prime * result + ((getHelpLike() == null) ? 0 : getHelpLike().hashCode());
        result = prime * result + ((getHelpCollect() == null) ? 0 : getHelpCollect().hashCode());
        result = prime * result + ((getHelpComment() == null) ? 0 : getHelpComment().hashCode());
        result = prime * result + ((getHelpLabel() == null) ? 0 : getHelpLabel().hashCode());
        result = prime * result + ((getHelpState() == null) ? 0 : getHelpState().hashCode());
        result = prime * result + ((getHelpCreateTime() == null) ? 0 : getHelpCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", helpOwnerId=").append(helpOwnerId);
        sb.append(", helpTitle=").append(helpTitle);
        sb.append(", helpDetail=").append(helpDetail);
        sb.append(", helpPaid=").append(helpPaid);
        sb.append(", helpLike=").append(helpLike);
        sb.append(", helpCollect=").append(helpCollect);
        sb.append(", helpComment=").append(helpComment);
        sb.append(", helpLabel=").append(helpLabel);
        sb.append(", helpState=").append(helpState);
        sb.append(", helpCreateTime=").append(helpCreateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}