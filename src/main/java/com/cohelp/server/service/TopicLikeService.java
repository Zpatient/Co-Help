package com.cohelp.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.TopicLike;

/**
* @author jianping5
* @description 针对表【topic_like(点赞记录表)】的数据库操作Service
* @createDate 2022-11-02 17:12:25
*/
public interface TopicLikeService extends IService<TopicLike> {

    /**
     * 点赞主题
     * @param type
     * @param id
     * @return
     */
    Result likeTopic(Integer type, Integer id);
}
