package com.cohelp.task_for_stu.net.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

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
     * 互助发布时间
     */
    private Date helpCreateTime;
}
