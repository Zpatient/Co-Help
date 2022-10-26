package com.cohelp.server.controller;

import com.cohelp.server.model.domain.ActivityResponse;
import com.cohelp.server.model.domain.HelpResponse;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Activity;
import com.cohelp.server.service.ActivityService;
import com.cohelp.server.service.HelpService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 活动控制器
 *
 * @author jianping5
 * @createDate 2022/10/23 23:24
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @PostMapping("/publish")
    public Result<Boolean> publishActivity(@RequestParam(name="activity") String activityJson,
                                        @RequestParam(name="file",required = false) MultipartFile[] files) {
        return activityService.publishActivity(activityJson, files);
    }

    @PostMapping("/update")
    public Result updateActivity(@RequestParam(name="activity") String activityJson,
                                                    @RequestParam(name="file",required = false) MultipartFile[] files) {
        return activityService.updateActivity(activityJson, files);
    }

}
