package com.cohelp.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Hole;
import com.cohelp.server.model.vo.DetailResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    /**
     * 根据条件筛选树洞
     * @param conditionType
     * @return
     */
    Result<List<DetailResponse>> listByCondition(Integer conditionType);

    DetailResponse getDetailResponse(Hole hole);
}
