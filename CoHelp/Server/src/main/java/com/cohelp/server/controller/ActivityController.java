package com.cohelp.server.controller;

import com.cohelp.server.model.domain.ActivityListRequest;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.vo.ActivityVO;
import com.cohelp.server.service.ActivityService;
import com.cohelp.server.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

import static com.cohelp.server.constant.StatusCode.ERROR_PARAMS;

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

    @PostMapping("/list")
    public Result<List<ActivityVO>> listByCondition(@RequestBody ActivityListRequest activityListRequest) {
        if (activityListRequest == null) {
            return ResultUtil.fail(ERROR_PARAMS);
        }
        Integer conditionType = activityListRequest.getConditionType();
        Integer dayNum = activityListRequest.getDayNum();
        return activityService.listByCondition(conditionType, dayNum);
    }

}
