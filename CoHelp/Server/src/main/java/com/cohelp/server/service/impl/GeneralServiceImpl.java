package com.cohelp.server.service.impl;

import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.model.domain.DetailRequest;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Activity;
import com.cohelp.server.model.entity.Help;
import com.cohelp.server.model.entity.Hole;
import com.cohelp.server.service.ActivityService;
import com.cohelp.server.service.GeneralService;
import com.cohelp.server.service.HelpService;
import com.cohelp.server.service.HoleService;
import com.cohelp.server.utils.ResultUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import static com.cohelp.server.constant.StatusCode.*;

/**
 * @author zgy
 * @create 2022-10-23 15:48
 */
public class GeneralServiceImpl implements GeneralService {

    @Autowired
    ActivityService activityService;
    @Autowired
    HelpService helpService;
    @Autowired
    HoleService holeService;
    @Override
    public Result getDetail(DetailRequest detailRequest) {
        //判断参数合法性
        if(ObjectUtils.anyNull(detailRequest)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Integer type = detailRequest.getType();
        String id = detailRequest.getId();
        if(!TypeEnum.isTopic(type)|| StringUtils.isAnyBlank(id)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        //判断请求哪种话题的详情并执行相应操作
        if(TypeEnum.isActivity(type)){
            Activity activity = activityService.getById(id);
            return ResultUtil.returnResult(SUCCESS_GET_DATA,activity,"Activity获取成功！");
        }
        else if(TypeEnum.isHelp(type)){
            Help help = helpService.getById(id);
            return ResultUtil.returnResult(SUCCESS_GET_DATA,help,"Help获取成功！");
        }
        else {
            Hole hole = holeService.getById(id);
            return ResultUtil.returnResult(SUCCESS_GET_DATA, hole, "Hole获取成功！");
        }
    }
}
