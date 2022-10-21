package com.cohelp.server.service;

import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Inform;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zgy
* @description 针对表【inform(举报表)】的数据库操作Service
* @createDate 2022-10-20 18:15:55
*/
public interface InformService extends IService<Inform> {
    public Result submitInform(Inform inform);
}
