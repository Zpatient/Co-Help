package com.cohelp.server.controller;

import com.cohelp.server.model.domain.IdAndType;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.domain.SearchRequest;
import com.cohelp.server.service.GeneralService;
import org.springframework.web.bind.annotation.*;

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
}
