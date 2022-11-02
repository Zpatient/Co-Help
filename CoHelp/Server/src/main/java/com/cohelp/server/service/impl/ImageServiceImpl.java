package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.model.domain.DetailRequest;
import com.cohelp.server.model.domain.ImageChangeRequest;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.*;
import com.cohelp.server.service.ImageService;
import com.cohelp.server.mapper.ImageMapper;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.UserHolder;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.cohelp.server.constant.StatusCode.*;


/**
* @author jianping5
* @description 针对表【image(图片表)】的数据库操作Service实现
* @createDate 2022-09-19 21:36:20
*/
@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image>
    implements ImageService {

    @Override
    public Result getImageList(DetailRequest detailRequest) {
        //判断参数合法性
        if(ObjectUtils.anyNull(detailRequest)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Integer type = detailRequest.getType();
        Integer id = detailRequest.getId();
        if(!TypeEnum.isTopic(type)|| ObjectUtils.anyNull(id)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        //查询数据
        QueryWrapper<Image> imageQueryWrapper = new QueryWrapper<>();
        imageQueryWrapper.eq("image_type",type)
                .eq("image_src_id",id)
                .eq("image_state",0);
        List<Image> images = getBaseMapper().selectList(imageQueryWrapper);
        ArrayList<String> imagesUrl = new ArrayList<>();
        for (Image image : images){
            imagesUrl.add(image.getImageUrl());
        }
        //返回图片URL列表
        if(ObjectUtils.anyNull(images)){
            return ResultUtil.fail(ERROR_GET_DATA,imagesUrl,"数据查询失败！");
        }
        else{
            return ResultUtil.returnResult(SUCCESS_GET_DATA,imagesUrl,"数据获取成功！");
        }
    }

    @Override
    public Result setImageState(ImageChangeRequest imageChangeRequest) {
        if(ObjectUtils.anyNull(imageChangeRequest)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Map<Image, Integer> imageStateMap = imageChangeRequest.getImageStateMap();
        Integer userId = imageChangeRequest.getUserId();
        if(ObjectUtils.anyNull(imageStateMap,userId)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        Set<Image> images = imageStateMap.keySet();
        //判断当前用户权限
        User user = UserHolder.getUser();
        if(userId != user.getId())
            return ResultUtil.fail(ERROR_GET_DATA,"用户不一致！");
        //对于操作失败的图片加入到failMap中返回给前端
        Map<Image, Integer> failMap = new HashMap<>();
        for(Image image : images ){
            Integer imageType = image.getImageType();
            Integer imageSrcId = image.getImageSrcId();
            String imageUrl = image.getImageUrl();
            Integer state = imageStateMap.get(image);
            //创建查询条件
            QueryWrapper<Image> eq = new QueryWrapper<Image>().eq("image_type", imageType)
                    .eq("image_src_id", imageSrcId)
                    .eq("image_url", imageUrl);
            //查找符合条件的记录，若记录为空则将该参数写入failMap返回前端
            Image one = getOne(eq);
            if(ObjectUtils.anyNull(one)){
                failMap.put(image,state);
            }else{
                one.setImageState(state);
                saveOrUpdate(one);
            }
        }
        //返回修改结果
        if(failMap.isEmpty()){
            return ResultUtil.ok(SUCCESS_REQUEST,"图片状态修改成功！");
        }else {
            return ResultUtil.ok(ERROR_REQUEST,failMap,"图片状态修改异常！");
        }
    }
}




