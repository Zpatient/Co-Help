package com.cohelp.server.controller;

import com.cohelp.server.model.domain.HelpResponse;
import com.cohelp.server.model.domain.HoleResponse;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Hole;
import com.cohelp.server.service.HelpService;
import com.cohelp.server.service.HoleService;
import com.sun.org.apache.xpath.internal.operations.Mult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.peer.ChoicePeer;

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

}
