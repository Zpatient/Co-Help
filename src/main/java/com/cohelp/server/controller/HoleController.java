package com.cohelp.server.controller;

import com.cohelp.server.model.domain.HoleListRequest;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.vo.DetailResponse;
import com.cohelp.server.service.HoleService;
import com.cohelp.server.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

import static com.cohelp.server.constant.StatusCode.ERROR_PARAMS;

/**
 * 树洞发布控制器
 *
 * @author jianping5
 * @createDate 2022/10/23 23:15
 */
@RestController
@RequestMapping("/hole")
public class HoleController {

    @Resource
    private HoleService holeService;

    @PostMapping("/publish")
    public Result<Boolean> publishHole(@RequestParam(name="hole") String holeJson,
                                            @RequestParam(name="file", required = false) MultipartFile[] files) {
        return holeService.publishHole(holeJson, files);
    }

    @PostMapping("/update")
    public Result updateHole(@RequestParam(name="hole") String holeJson,
                                           @RequestParam(name="file", required = false) MultipartFile[] files) {
        return holeService.updateHole(holeJson, files);
    }

    @PostMapping("/list/{page}/{limit}")
    public Result<List<DetailResponse>> listByCondition(@RequestBody HoleListRequest holeListRequest,@PathVariable Integer page,@PathVariable Integer limit) {
        if (holeListRequest == null) {
            return ResultUtil.fail(ERROR_PARAMS);
        }
        Integer conditionType = holeListRequest.getConditionType();
        return holeService.listByCondition(conditionType,page,limit);
    }

}
