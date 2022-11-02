package com.cohelp.server.controller;

import com.cohelp.server.model.domain.HistoryAndCollectRequest;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.History;
import com.cohelp.server.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Result getCollectList(@RequestBody HistoryAndCollectRequest historyRequest){
        return historyService.getHistoryList(historyRequest);
    }
    @RequestMapping("/inserthistoryrecord")
    public Result insertCollectRecord(@RequestBody History history){
        return historyService.insertHistoryRecord(history);
    }
    @RequestMapping("/deletehistoryrecord")
    public Result deleteCollectRecord(@RequestParam String id){
        return historyService.deleteHistoryRecord(id);
    }
}
