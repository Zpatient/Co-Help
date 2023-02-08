package com.cohelp.server.controller;

import com.cohelp.server.constant.StatusCode;
import com.cohelp.server.model.domain.IdAndType;
import com.cohelp.server.model.domain.PageResponse;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.domain.UserOrTopicOrRemark;
import com.cohelp.server.model.entity.Inform;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.model.vo.InformVO;
import com.cohelp.server.service.InformService;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.UserHolder;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 投诉信息控制器
 * @author zgy
 * @create 2022-10-20 19:37
 */
@RestController
@RequestMapping("/inform")
public class InformController {
    @Autowired
    InformService informService;
    @PostMapping("/submitinform")
    public Result submitInform(@RequestBody Inform inform)
    {
        User user = UserHolder.getUser();
        inform.setInformerId(user.getId());
        inform.setTeamId(user.getTeamId());
        return informService.submitInform(inform);
    }
    @GetMapping("/listinforms")
    public Result<PageResponse<InformVO>> listInforms(@RequestParam Integer page, @RequestParam Integer limit){
        User user = UserHolder.getUser();
        PageResponse<InformVO> informVOS = informService.listInforms(page, limit, user.getTeamId());
        return ResultUtil.ok(informVOS);
    }
    @RequestMapping("/deleteinform")
    public Result deleteInform(@RequestParam Integer id){
        String s = informService.deleteInform(id);
        return ResultUtil.ok(s);
    }
    @RequestMapping("/getinformtarget")
    public Result<UserOrTopicOrRemark> getInformTarget(@RequestParam Integer id, @RequestParam Integer type){
        IdAndType idAndType = new IdAndType(id, type);
        UserOrTopicOrRemark informTarget = informService.getInformTarget(idAndType);
        if(informTarget==null|| ObjectUtils.allNull(informTarget.getUser(),informTarget.getDetailResponse(),informTarget.getDetailRemark())){
            return ResultUtil.fail(StatusCode.ERROR_GET_DATA,null,"查询不到符合条件的数据！");
        }else {
            return ResultUtil.ok(StatusCode.SUCCESS_GET_DATA,informTarget,"数据获取成功！");
        }
    }
}