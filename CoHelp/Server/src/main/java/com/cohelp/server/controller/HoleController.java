package com.cohelp.server.controller;

import com.cohelp.server.model.domain.*;
import com.cohelp.server.model.entity.Hole;
import com.cohelp.server.model.vo.HelpVO;
import com.cohelp.server.model.vo.HoleVO;
import com.cohelp.server.service.HelpService;
import com.cohelp.server.service.HoleService;
import com.cohelp.server.utils.ResultUtil;
import com.sun.org.apache.xpath.internal.operations.Mult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.peer.ChoicePeer;
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

    @PostMapping("/list")
    public Result<List<HoleVO>> listByCondition(@RequestBody HoleListRequest holeListRequest) {
        if (holeListRequest == null) {
            return ResultUtil.fail(ERROR_PARAMS);
        }
        Integer conditionType = holeListRequest.getConditionType();
        return holeService.listByCondition(conditionType);
    }

}
