package com.cohelp.server.service.impl;

import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.model.domain.*;
import com.cohelp.server.model.entity.Activity;
import com.cohelp.server.model.entity.Help;
import com.cohelp.server.model.entity.Hole;
import com.cohelp.server.model.entity.Image;
import com.cohelp.server.service.*;
import com.cohelp.server.utils.ResultUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static com.cohelp.server.constant.StatusCode.*;

/**
 * @author zgy
 * @create 2022-10-23 15:48
 */
@Service
public class GeneralServiceImpl implements GeneralService {

    @Resource
    ActivityService activityService;
    @Resource
    HelpService helpService;
    @Resource
    HoleService holeService;
    @Resource
    ImageService imageService;
    @Override
    public Result getDetail(DetailRequest detailRequest) {
        //判断参数合法性
        if(ObjectUtils.anyNull(detailRequest)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Integer type = detailRequest.getType();
        Integer id = detailRequest.getId();
        if(!TypeEnum.isTopic(type)|| ObjectUtils.anyNull(id)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        //获取该话题对应的的图片URL列表
        Result images = imageService.getImageList(detailRequest);
        ArrayList<String>  imagesUrl = (ArrayList<String>)images.getData();
        String imageMessage = "";
        if(images.getCode().equals(ERROR_GET_DATA)){
            imageMessage = "图片获取失败,";
        }
        else{
            imageMessage = "图片获取成功,";
        }
        //判断请求哪种话题的详情并执行相应操作
        if(TypeEnum.isActivity(type)){
            Activity activity = activityService.getBaseMapper().selectById(id);
            if(ObjectUtils.anyNull(activity)){
                String message = imageMessage+"基本数据获取成功！";
                ActivityResponse activityResponse = new ActivityResponse(activity, imagesUrl);
                return ResultUtil.returnResult(SUCCESS_GET_DATA,activityResponse,message);
            }
            else{
                return ResultUtil.returnResult(ERROR_GET_DATA,null,"数据获取失败");
            }
        }
        else if(TypeEnum.isHelp(type)){
            Help help = helpService.getById(id);
            if(ObjectUtils.anyNull(help)){
                String message = imageMessage+"基本数据获取成功！";
                HelpResponse helpResponse = new HelpResponse(help,imagesUrl);
                return ResultUtil.returnResult(SUCCESS_GET_DATA,helpResponse,message);
            }
            else{
                return ResultUtil.returnResult(ERROR_GET_DATA,null,"数据获取失败");
            }
        }
        else {
            Hole hole = holeService.getById(id);
            if(ObjectUtils.anyNull(hole)) {
                String message = imageMessage + "基本数据获取成功！";
                HoleResponse holeResponse = new HoleResponse(hole, imagesUrl);
                return ResultUtil.returnResult(SUCCESS_GET_DATA, holeResponse, message);
            }
            else{
                return ResultUtil.returnResult(ERROR_GET_DATA,null,"数据获取失败");
            }
        }
    }
}
