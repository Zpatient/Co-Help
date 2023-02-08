package com.cohelp.server.controller;

import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.model.domain.*;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.model.vo.DetailRemark;
import com.cohelp.server.model.vo.DetailResponse;
import com.cohelp.server.model.vo.RemarkVO;
import com.cohelp.server.service.GeneralService;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.UserHolder;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
    public Result<List<RemarkVO>> listRemark(@RequestBody IdAndType idAndType){
        User user = UserHolder.getUser();
        return generalService.listRemark(idAndType,user.getId());
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

    @RequestMapping("/listtopics")
    public Result<PageResponse<DetailResponse>> listTopics(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam Integer type){
        User user = UserHolder.getUser();
        PageResponse<DetailResponse> detailResponses = generalService.listTopics(page, limit, user.getTeamId(), type);
        return ResultUtil.ok(detailResponses);
    }
    @RequestMapping("/searchtopics")
    public Result<PageResponse<DetailResponse>> searchTopics(@RequestParam Integer page,@RequestParam Integer limit,@RequestParam Integer type,@RequestParam String key){
        User user = UserHolder.getUser();
        PageResponse<DetailResponse> detailResponses = generalService.searchTopics(page, limit, user.getTeamId(), type,key);
        return ResultUtil.ok(detailResponses);
    }
    @RequestMapping("/changetopic")
    public Result changeTopic(@RequestBody ChangeTopic changeTopic){
        String s = generalService.changeTopic(changeTopic.getType(), changeTopic.getActivity(), changeTopic.getHelp(), changeTopic.getHole());
        return ResultUtil.ok(s);
    }
    @RequestMapping("/listremarks")
    public Result<PageResponse<DetailRemark>> listRemarks(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam Integer type){
        User user = UserHolder.getUser();
        //判断参数合法性
        if(ObjectUtils.anyNull(type)||!TypeEnum.isRemark(type)){
            return ResultUtil.fail("类型不合法！");
        }
        PageResponse<DetailRemark> detailRemarks = generalService.listRemarks(page, limit, user.getTeamId(), type);
        return ResultUtil.ok(detailRemarks);
    }
    @RequestMapping("/searchremarks")
    public Result<PageResponse<DetailRemark>> searchRemarks(@RequestParam Integer page,@RequestParam Integer limit,@RequestParam Integer type,@RequestParam String key){
        User user = UserHolder.getUser();
        //判断参数合法性
        if(ObjectUtils.anyNull(type)||!TypeEnum.isRemark(type)){
            return ResultUtil.fail("类型不合法！");
        }
        PageResponse<DetailRemark> detailRemarks = generalService.searchRemarks(page, limit, user.getTeamId(), type,key);
        return ResultUtil.ok(detailRemarks);
    }
    @RequestMapping("/removeremark")
    public Result removeRemark(@RequestParam Integer id,@RequestParam Integer type){
        String s = generalService.deleteRemark(type, id);
        return ResultUtil.ok(s);
    }
}
