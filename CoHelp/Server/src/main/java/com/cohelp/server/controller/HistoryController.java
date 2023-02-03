package com.cohelp.server.controller;

import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.History;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.service.HistoryService;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zgy
 * @create 2022-10-24 23:00
 */
@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    HistoryService historyService;
    @RequestMapping("/gethistorylist")
    public Result listHistory(){
        User user = UserHolder.getUser();
        return historyService.listHistory(user);
    }
    @RequestMapping("/inserthistoryrecord")
    public Result insertHistoryRecord(@RequestBody History history){
        return historyService.insertHistoryRecord(history);
    }
    @RequestMapping("/deletehistoryrecord")
    public Result deleteHistoryRecord(@RequestParam Integer id){
        return historyService.deleteHistoryRecord(id);
    }
    @GetMapping("/getinvolvedlist")
    public Result listInvolved(){
        User user = UserHolder.getUser();
        if(user==null) return ResultUtil.fail("用户未登录！");
        return historyService.listInvolvedRecord(user.getId());
    }
}
