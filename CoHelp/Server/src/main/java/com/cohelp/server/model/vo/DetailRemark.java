package com.cohelp.server.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zgy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailRemark {
    /**
     * 类型
     */
    private Integer type;
    /**
     * ID
     */
    private Integer id;
    /**
     * 发布者昵称
     */
    private String ownerName;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论所属话题的标题
     */
    private String topicTitle;
    /**
     * 评论时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date remarkTime;
    /**
     * 点赞量
     */
    private Integer like;

}
