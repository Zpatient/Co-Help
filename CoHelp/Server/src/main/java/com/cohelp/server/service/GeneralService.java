package com.cohelp.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cohelp.server.model.domain.DetailRequest;
import com.cohelp.server.model.domain.Result;
import org.springframework.stereotype.Service;

/**
 * @author zgy
 * @create 2022-10-23 15:43
 */
@Service
public interface GeneralService{
    public Result getDetail(DetailRequest detailRequest);
}
