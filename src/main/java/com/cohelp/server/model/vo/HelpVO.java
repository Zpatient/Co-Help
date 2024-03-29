package com.cohelp.server.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 互助视图体
 *
 * @author jianping5
 * @createDate 2022/11/2 18:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelpVO implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 互助发布者昵称
     */
    private String userName;

    /**
     * 互助发布者头像
     */
    private Integer avatar;

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
    private LocalDateTime helpCreateTime;

    /**
     * 组织id
     */
    private Integer teamId;
    /**
     * 阅读量
     */
    private Integer readNum;
}
