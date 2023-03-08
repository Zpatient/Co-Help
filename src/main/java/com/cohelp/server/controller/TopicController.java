package com.cohelp.server.controller;

import com.cohelp.server.model.domain.Result;
import com.cohelp.server.service.TopicLikeService;
import com.cohelp.server.utils.ResultUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.cohelp.server.constant.StatusCode.ERROR_PARAMS;

/**
 * 针对话题的通用控制器
 *
 * @author jianping5
 * @createDate 2022/11/2 17:13
 */
@RestController
@RequestMapping("/topic")
public class TopicController {

    @Resource
    private TopicLikeService topicLikeService;

    @PostMapping("/like/{type}/{id}")
    public Result<Boolean> likeTopic(@PathVariable Integer type, @PathVariable Integer id) {
        if (type == null || type <= 0 || id == null || id <= 0) {
            return ResultUtil.fail(ERROR_PARAMS);
        }
        return topicLikeService.likeTopic(type, id);
    }
}
