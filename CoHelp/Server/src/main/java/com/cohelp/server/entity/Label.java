package com.cohelp.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 标签表
 * @TableName label
 */
@TableName(value ="label")
@Data
public class Label implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 标签类型：1：活动 2：互助 3：树洞
     */
    private Integer labelType;

    /**
     * 标签来源id
     */
    private Integer labelSrcId;

    /**
     * 标签
     */
    private String labelContent;

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
        Label other = (Label) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getLabelType() == null ? other.getLabelType() == null : this.getLabelType().equals(other.getLabelType()))
            && (this.getLabelSrcId() == null ? other.getLabelSrcId() == null : this.getLabelSrcId().equals(other.getLabelSrcId()))
            && (this.getLabelContent() == null ? other.getLabelContent() == null : this.getLabelContent().equals(other.getLabelContent()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLabelType() == null) ? 0 : getLabelType().hashCode());
        result = prime * result + ((getLabelSrcId() == null) ? 0 : getLabelSrcId().hashCode());
        result = prime * result + ((getLabelContent() == null) ? 0 : getLabelContent().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", labelType=").append(labelType);
        sb.append(", labelSrcId=").append(labelSrcId);
        sb.append(", labelContent=").append(labelContent);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}