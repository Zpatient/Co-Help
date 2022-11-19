package com.cohelp.server.controller;

import com.cohelp.server.model.domain.IdAndType;
import com.cohelp.server.model.domain.RemarkRequest;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.domain.SearchRequest;
import com.cohelp.server.service.GeneralService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
