package com.cohelp.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cohelp.server.model.entity.Ask;
import com.cohelp.server.model.vo.DetailResponse;

/**
* @author jianping5
* @description 针对表【ask】的数据库操作Service
* @createDate 2023-03-01 14:31:07
*/
public interface AskService extends IService<Ask> {

    DetailResponse getDetailResponse(Ask ask);

}
