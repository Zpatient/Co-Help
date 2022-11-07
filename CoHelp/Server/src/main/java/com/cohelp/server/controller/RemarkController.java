package com.cohelp.server.controller;

import com.cohelp.server.model.domain.Result;
import com.cohelp.server.service.RemarkLikeService;
import com.cohelp.server.utils.ResultUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.cohelp.server.constant.StatusCode.ERROR_PARAMS;

/**
 * 针对评论的通用控制器
 * @author zgy
 * @createDate 2022/11/4 17:13
 */
@RestController
@RequestMapping("/remark")
public class RemarkController {
    @Resource
    private RemarkLikeService remarkLikeService;
    @PostMapping("/like/{type}/{id}")
    public Result<Boolean> likeRemark(@PathVariable Integer type, @PathVariable Integer id) {
        if (type == null || type <= 0 || id == null || id <= 0) {
            return ResultUtil.fail(ERROR_PARAMS);
        }
        return remarkLikeService.likeRemark(type, id);
    }
}
