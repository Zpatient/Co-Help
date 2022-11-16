package com.cohelp.task_for_stu.net.model.entity;


import java.io.Serializable;
import lombok.Data;

/**
 * 图片表
 * @TableName image
 */
@Data
public class Image implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 图片（0：用户 1：活动 2：互助 3：树洞）
     */
    private Integer imageType;

    /**
     * 来源id
     */
    private Integer imageSrcId;

    /**
     * 图片url
     */
    private String imageUrl;

    /**
     * 图片状态（0：使用中 1：待使用）
用户：更换图像时需要修改该字段（历史头像）
     */
    private Integer imageState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImageType() {
        return imageType;
    }

    public void setImageType(Integer imageType) {
        this.imageType = imageType;
    }

    public Integer getImageSrcId() {
        return imageSrcId;
    }

    public void setImageSrcId(Integer imageSrcId) {
        this.imageSrcId = imageSrcId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getImageState() {
        return imageState;
    }

    public void setImageState(Integer imageState) {
        this.imageState = imageState;
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
        Image other = (Image) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getImageType() == null ? other.getImageType() == null : this.getImageType().equals(other.getImageType()))
            && (this.getImageSrcId() == null ? other.getImageSrcId() == null : this.getImageSrcId().equals(other.getImageSrcId()))
            && (this.getImageUrl() == null ? other.getImageUrl() == null : this.getImageUrl().equals(other.getImageUrl()))
            && (this.getImageState() == null ? other.getImageState() == null : this.getImageState().equals(other.getImageState()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getImageType() == null) ? 0 : getImageType().hashCode());
        result = prime * result + ((getImageSrcId() == null) ? 0 : getImageSrcId().hashCode());
        result = prime * result + ((getImageUrl() == null) ? 0 : getImageUrl().hashCode());
        result = prime * result + ((getImageState() == null) ? 0 : getImageState().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", imageType=").append(imageType);
        sb.append(", imageSrcId=").append(imageSrcId);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", imageState=").append(imageState);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}