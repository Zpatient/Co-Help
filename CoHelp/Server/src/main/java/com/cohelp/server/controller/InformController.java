package com.cohelp.server.controller;

import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Inform;
import com.cohelp.server.service.InformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return informService.submitInform(inform);
    }
}
