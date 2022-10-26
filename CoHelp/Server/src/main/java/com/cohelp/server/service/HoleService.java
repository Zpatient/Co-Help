package com.cohelp.server.service;

import com.cohelp.server.model.domain.HoleResponse;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Hole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
* @author jianping5
* @description 针对表【hole(树洞表)】的数据库操作Service
* @createDate 2022-10-21 21:26:01
*/
public interface HoleService extends IService<Hole> {

    /**
     * 发布树洞
     * @param holeJson
     * @param files
     * @return
     */
    Result<Boolean> publishHole(String holeJson, MultipartFile[] files);

    /**
     * 修改树洞内容
     * @param holeJson
     * @param files
     * @return
     */
    Result updateHole(String holeJson, MultipartFile[] files);

}
