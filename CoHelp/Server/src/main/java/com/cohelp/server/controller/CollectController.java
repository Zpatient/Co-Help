package com.cohelp.server.controller;

import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Collect;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.model.vo.DetailResponse;
import com.cohelp.server.service.CollectService;
import com.cohelp.server.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zgy
 * @create 2022-10-26 21:00
 */
@RestController
@RequestMapping("/collect")
public class CollectController {
    @Autowired
    CollectService collectService;

    @RequestMapping("/getcollectlist/{page}/{limit}")
    public Result<List<DetailResponse>> listCollect(@PathVariable Integer page, @PathVariable Integer limit){
        User user = UserHolder.getUser();
        return collectService.listCollect(user,page,limit);
    }
    @RequestMapping("/insertcollectrecord")
    public Result insertCollectRecord(@RequestBody Collect collect){
        return collectService.collectTopic(collect);
    }



    @RequestMapping("/deletecollectrecord")
    public Result deleteCollectRecord(@RequestParam Integer id){
        return collectService.deleteCollectRecord(id);
    }

    @RequestMapping("/deletecollectrecords")
    public Result deleteCollectRecord(@RequestParam List<Integer> ids){
        return collectService.deleteCollectRecord(ids);
    }


}
