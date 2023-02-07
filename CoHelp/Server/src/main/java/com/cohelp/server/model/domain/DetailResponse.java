package com.cohelp.server.model.domain;

import com.cohelp.server.model.vo.ActivityVO;
import com.cohelp.server.model.vo.HelpVO;
import com.cohelp.server.model.vo.HoleVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author zgy
 * @create 2022-11-03 15:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailResponse implements Serializable {
    /**
     * 活动视图体，封装活动基本信息(不包括发布者头像及话题相关图片)
     */
    private ActivityVO activityVO;
    /**
     * 互助视图体，封装互助基本信息(不包括发布者头像及话题相关图片)
     */
    private HelpVO helpVO;
    /**
     * 树洞视图体，封装树洞基本信息(不包括发布者头像及话题相关图片)
     */
    private HoleVO holeVO;
    /**
     * 发布者头像Url
     */
    private String publisherAvatarUrl;
    /**
     * 话题相关图片Url
     */
    ArrayList<String> imagesUrl;

    /**
     * 是否被点赞
     */
    private Integer isLiked;
}
