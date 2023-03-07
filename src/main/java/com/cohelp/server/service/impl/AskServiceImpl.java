package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.mapper.AskMapper;
import com.cohelp.server.model.entity.*;
import com.cohelp.server.model.vo.AskVO;
import com.cohelp.server.model.vo.DetailResponse;
import com.cohelp.server.service.*;
import com.cohelp.server.utils.UserHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.cohelp.server.constant.TypeEnum.ASK;

/**
* @author jianping5
* @description 针对表【ask】的数据库操作Service实现
* @createDate 2023-03-01 14:31:07
*/
@Service
public class AskServiceImpl extends ServiceImpl<AskMapper, Ask>
    implements AskService{

    @Resource
    private UserService userService;

    @Resource
    private ImageService imageService;

    @Resource
    private DiscussionLikeService discussionLikeService;

    @Resource
    private CollectService collectService;

    @Override
    public DetailResponse getDetailResponse(Ask ask) {
        DetailResponse detailResponse = new DetailResponse();
        // 注入 askVO
        AskVO askVO = traverseAsk(ask);
        detailResponse.setAskVO(askVO);

        // 注入收藏判定值
        detailResponse.setIsCollected(askVO.getIsCollected());

        // 注入点赞判定值
        detailResponse.setIsLiked(askVO.getIsLiked());

        // 注入发布者图片
        String publisherAvatarUrl = askVO.getAvatarUrl();
        detailResponse.setPublisherAvatarUrl(publisherAvatarUrl);

        //获取该话题对应的的图片URL列表
        detailResponse.setImagesUrl((ArrayList<String>) askVO.getImageUrl());
        detailResponse.setType(ASK.ordinal());
        return detailResponse;
    }

    /**
     * 遍历，将提问信息和对应发布者部分信息注入到提问视图体中
     * @param ask
     * @return
     */
    public AskVO traverseAsk(Ask ask) {
        AskVO askVO = new AskVO();
        BeanUtils.copyProperties(ask, askVO);
        // 获取用户昵称
        User user = userService.getById(ask.getPublisherId());
        askVO.setUserName(user.getUserName());
        // 获取用户头像
        Image image = imageService.getById(user.getAvatar());
        askVO.setAvatarUrl(image.getImageUrl());

        // 设置图片
        QueryWrapper<Image> imageQueryWrapper = new QueryWrapper<>();
        imageQueryWrapper.eq("image_src_id", ask.getId());
        imageQueryWrapper.eq("image_type", ASK.ordinal());
        List<String> imageUrlList = imageService.list(imageQueryWrapper).stream().map(image1 -> image1.getImageUrl()).collect(Collectors.toList());
        askVO.setImageUrl(imageUrlList);

        // 注入点赞判定值
        QueryWrapper<DiscussionLike> discussionLikeQueryWrapper = new QueryWrapper<>();
        discussionLikeQueryWrapper.eq("user_id", askVO.getPublisherId())
                .eq("target_type", 1)
                .eq("target_id", askVO.getId());
        DiscussionLike discussionLike = discussionLikeService.getOne(discussionLikeQueryWrapper);
        if (discussionLike == null) {
            askVO.setIsLiked(0);
        } else {
            askVO.setIsLiked(discussionLike.getIsLiked());
        }

        // 注入收藏判定值
        QueryWrapper<Collect> collectQueryWrapper = new QueryWrapper<>();
        collectQueryWrapper.eq("user_id", UserHolder.getUser().getId())
                .eq("topic_type", 1)
                .eq("topic_id", askVO.getId());
        Collect one = collectService.getOne(collectQueryWrapper);
        if (one == null) {
            askVO.setIsCollected(0);
        } else {
            askVO.setIsCollected(1);
        }
        return askVO;
    }
}




