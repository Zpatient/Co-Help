package com.cohelp.server.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnswerVO implements Serializable {
    /**
     * ID
     */
    private Integer id;
    /**
     * 回答的内容
     */
    private String content;
    /**
     * 提问ID
     */
    private Integer askId;
    /**
     * 回答的时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
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
     * 回答目标的ID
     */
    private Integer answerTargetId;

    /**
     * 回答目标类型
     */
    private Integer answerTargetType;



    /**
     * 回答发布者的昵称
     */
    private String publisherName;
    /**
     * 回答发布者的头像
     */
    private String publisherAvatar;


    /**
     * 回答目标发布者的昵称
     */
    private String answerTargetName;


    /**
     * 回答内容中的图片
     */
    private List<String> answerImageUrl;


    /**
     * 是否已点赞
     */
    private Integer isLiked;

    private static final long serialVersionUID = 1L;

}
