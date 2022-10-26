package com.cohelp.server.controller;

import com.cohelp.server.model.domain.HelpResponse;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Help;
import com.cohelp.server.service.HelpService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


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

}
