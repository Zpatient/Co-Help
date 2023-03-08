package com.cohelp.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.RemarkLike;

/**
* @author zgy
* @description 针对表【remark_like(评论点赞记录表)】的数据库操作Service
*/
public interface RemarkLikeService extends IService<RemarkLike> {


    /**
     * 点赞主题
     * @param type
     * @param id
     * @return
     */
    Result likeRemark(Integer type, Integer id);
}
