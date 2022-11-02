package com.cohelp.server.controller;

import com.cohelp.server.model.domain.HistoryAndCollectRequest;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Collect;
import com.cohelp.server.service.CollectService;
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
    public Result getCollectList(@RequestBody HistoryAndCollectRequest collectRequest){
        return collectService.getCollectList(collectRequest);
    }
    @RequestMapping("/insertcollectrecord")
    public Result insertCollectRecord(@RequestBody Collect collect){
        return collectService.insertCollectRecord(collect);
    }
    @RequestMapping("/deletecollectrecord")
    public Result deleteCollectRecord(@RequestParam Integer id){
        return collectService.deleteCollectRecord(id);
    }
}
