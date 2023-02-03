package com.cohelp.server.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jianping5
 * @createDate 2022/11/2 18:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 树洞发布者id
     */
    private Integer holeOwnerId;

    /**
     * 树洞发布者昵称
     */
    private String userName;

    /**
     * 树洞发布者头像
     */
    private Integer avatar;

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
     * 树洞发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date holeCreateTime;

    /**
     * 组织id
     */
    private Integer teamId;
}
