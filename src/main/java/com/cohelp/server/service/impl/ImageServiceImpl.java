package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.mapper.ImageMapper;
import com.cohelp.server.model.domain.IdAndType;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Image;
import com.cohelp.server.service.ImageService;
import com.cohelp.server.utils.ResultUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

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
    public Result getImageById(Integer id) {
        //判断参数合法性
        if(ObjectUtils.anyNull(id)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Image image = getById(id);
        if(ObjectUtils.anyNull(image)){
            return ResultUtil.fail("数据为空！");
        }else {
            return ResultUtil.ok(image.getImageUrl(),"图片Url获取成功");
        }
    }

    @Override
    public ArrayList<String> getImageList(IdAndType idAndType) {
        //判断参数合法性
        if(idAndType == null){
            return null;
        }
        Integer type = idAndType.getType();
        Integer id = idAndType.getId();
        if(ObjectUtils.anyNull(id)){
            return null;
        }
        //查询数据
        QueryWrapper<Image> imageQueryWrapper = new QueryWrapper<>();
        imageQueryWrapper.eq("image_type",type)
                .eq("image_src_id",id)
                .eq("image_state",0);
        List<Image> images = list(imageQueryWrapper);
        ArrayList<String> imagesUrl = new ArrayList<>();
        if(images != null && !CollectionUtils.isEmpty(images)){
            for (Image image : images){
                imagesUrl.add(image.getImageUrl());
            }
        }
        return imagesUrl;
    }

    @Override
    public Result getAllImage(IdAndType idAndType) {
        //判断参数合法性
        if(ObjectUtils.anyNull(idAndType)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Integer type = idAndType.getType();
        Integer id = idAndType.getId();
        if(ObjectUtils.anyNull(id)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        //查询数据
        QueryWrapper<Image> imageQueryWrapper = new QueryWrapper<>();
        imageQueryWrapper.eq("image_type",type)
                .eq("image_src_id",id);
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

}




