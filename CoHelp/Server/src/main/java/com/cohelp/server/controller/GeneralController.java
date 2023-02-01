package com.cohelp.server.controller;

import com.cohelp.server.model.domain.*;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.service.GeneralService;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.UserHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.cohelp.server.constant.StatusCode.SUCCESS_GET_DATA;

/**
 * @author zgy
 * @create 2022-10-23 20:30
 */
@RestController
@RequestMapping("/general")
public class GeneralController {
    @Resource
    GeneralService generalService;
    @RequestMapping("/getdetail")
    public Result getDetail(@RequestBody IdAndType idAndType){
        return generalService.getDetail(idAndType);
    }
    @RequestMapping("/search")
    public Result search(@RequestBody SearchRequest searchRequest){
        return generalService.search(searchRequest);
    }
    @RequestMapping("/insertremark")
    public Result insertRemark(@RequestBody RemarkRequest remarkRequest){
        return generalService.insertRemark(remarkRequest);
    }
    @RequestMapping("/deleteremark")
    public Result deleteRemark(@RequestBody IdAndType idAndType){
        return generalService.deleteRemark(idAndType);
    }
    @RequestMapping("/getremarklist")
    public Result listRemark(@RequestBody IdAndType idAndType){
        return generalService.listRemark(idAndType);
    }

    @RequestMapping("/getcurrentdaypublish")
    public Result<TopicNumber> getCurrentDayPublish(){
        User user = UserHolder.getUser();
        if(user==null){
            return ResultUtil.fail("未登录！");
        }
        Integer teamId = user.getTeamId();
        TopicNumber currentDayPublish = generalService.getCurrentDayPublish(teamId);
        return ResultUtil.ok(SUCCESS_GET_DATA,currentDayPublish,"查询成功！");
    }

    @RequestMapping("/getcurrentyearpublish")
    public Result<TopicNumber> getCurrentYearPublish(){
        User user = UserHolder.getUser();
        if(user==null){
            return ResultUtil.fail("未登录！");
        }
        Integer teamId = user.getTeamId();
        TopicNumber currentYearPublish = generalService.getCurrentYearPublish(teamId);
        return ResultUtil.ok(SUCCESS_GET_DATA,currentYearPublish,"查询成功！");
    }
}
