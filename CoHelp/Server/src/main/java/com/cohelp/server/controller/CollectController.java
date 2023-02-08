package com.cohelp.server.controller;

import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Collect;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.service.CollectService;
import com.cohelp.server.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zgy
 * @create 2022-10-26 21:00
 */
@RestController
@RequestMapping("/collect")
public class CollectController {
    @Autowired
    CollectService collectService;
    @RequestMapping("/getcollectlist")
    public Result listCollect(){
        User user = UserHolder.getUser();
        return collectService.listCollect(user);
    }
    @RequestMapping("/insertcollectrecord")
    public Result insertCollectRecord(@RequestBody Collect collect){
        return collectService.collectTopic(collect);
    }
    @RequestMapping("/deletecollectrecord")
    public Result deleteCollectRecord(@RequestParam Integer id){
        return collectService.deleteCollectRecord(id);
    }
}
