package com.cohelp.server.service;

import com.cohelp.server.model.domain.HelpResponse;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Help;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author jianping5
* @description 针对表【help(互助表)】的数据库操作Service
* @createDate 2022-10-21 21:25:55
*/
public interface HelpService extends IService<Help> {


    /**
     * 发布互助
     * @param helpJson
     * @param files
     * @return
     */
    Result<Boolean> publishHelp(String helpJson, MultipartFile[] files);

    /**
     * 修改互助
     * @param helpJson
     * @param files
     * @return
     */
    Result updateHelp(String helpJson, MultipartFile[] files);
}
