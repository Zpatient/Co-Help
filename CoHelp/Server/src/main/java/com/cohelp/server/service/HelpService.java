package com.cohelp.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Help;
import com.cohelp.server.model.vo.DetailResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    /**
     * 按条件展示互助
     * @param conditionType
     * @return
     */
    Result<List<DetailResponse>> listByCondition(Integer conditionType,Integer page,Integer limit);

    /**
     * 根据标签展示互助
     * @param tag
     */
    Result<List<DetailResponse>> listByTag(String tag,Integer page,Integer limit);

    DetailResponse getDetailResponse(Help help);
}
