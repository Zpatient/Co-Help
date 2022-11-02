package com.cohelp.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cohelp.server.model.domain.DetailRequest;
import com.cohelp.server.model.domain.Result;
import org.springframework.stereotype.Service;

/**
 * @author zgy
 * @create 2022-10-23 15:43
 */
public interface GeneralService{
    /**
     * 根据传入的参数获取话题详情
     * @author: ZGY
     * @date: 2022-10-22 18:15
     * @param detailRequest 话题详情请求参数
     * @return com.cohelp.server.model.domain.Result
     */
    public Result getDetail(DetailRequest detailRequest);
}