package com.cohelp.server.controller;

import com.cohelp.server.model.domain.HelpListRequest;
import com.cohelp.server.model.domain.HelpTagRequest;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.vo.DetailResponse;
import com.cohelp.server.service.HelpService;
import com.cohelp.server.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

import static com.cohelp.server.constant.StatusCode.ERROR_PARAMS;


/**
 * 互助控制器
 *
 * @author jianping5
 * @createDate 2022/10/23 21:22
 */

@RestController
@RequestMapping("/help")
public class HelpController {

    @Resource
    private HelpService helpService;

    @PostMapping("/publish")
    public Result<Boolean> publishHelp(@RequestParam(name="help") String helpJson,
                         @RequestParam(name="file",required = false) MultipartFile[] files) {
        return helpService.publishHelp(helpJson, files);
    }

    @PostMapping("/update")
    public Result updateHelp(@RequestParam(name="help") String helpJson,
                                            @RequestParam(name="file",required = false) MultipartFile[] files) {
        return helpService.updateHelp(helpJson, files);
    }

    @PostMapping("/list/{page}/{limit}")
    public Result<List<DetailResponse>> listByCondition(@RequestBody HelpListRequest helpListRequest,@PathVariable Integer page,@PathVariable Integer limit) {
        if (helpListRequest == null) {
            return ResultUtil.fail(ERROR_PARAMS);
        }
        Integer conditionType = helpListRequest.getConditionType();
        return helpService.listByCondition(conditionType,page,limit);
    }

    @PostMapping("/list/tag/{page}/{limit}")
    public Result<List<DetailResponse>> listByTag(@RequestBody HelpTagRequest helpTagRequest,@PathVariable Integer page,@PathVariable Integer limit) {
        if (helpTagRequest == null) {
            return ResultUtil.fail(ERROR_PARAMS);
        }
        String tag = helpTagRequest.getTag();
        return helpService.listByTag(tag,page,limit);
    }

}
