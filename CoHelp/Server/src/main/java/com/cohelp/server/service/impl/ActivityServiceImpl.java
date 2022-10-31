package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.model.domain.ActivityResponse;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Activity;
import com.cohelp.server.model.entity.Image;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.service.ActivityService;
import com.cohelp.server.mapper.ActivityMapper;
import com.cohelp.server.service.ImageService;
import com.cohelp.server.utils.FileUtils;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.UserHolder;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;

import static com.cohelp.server.constant.StatusCode.*;
import static com.cohelp.server.constant.TypeEnum.*;

/**
* @author jianping5
* @description 针对表【activity(活动表)】的数据库操作Service实现
* @createDate 2022-10-21 21:22:49
*/
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity>
    implements ActivityService{

    @Resource
    private Gson gson;

    @Resource
    private ImageService imageService;

    @Override
    public Result<Boolean> publishActivity(String activityJson, MultipartFile[] files) {
        if (StringUtils.isBlank(activityJson)) {
            return ResultUtil.fail(ERROR_PARAMS, "请求参数错误");
        }
        Activity activity = gson.fromJson(activityJson, Activity.class);
        if (StringUtils.isBlank(activity.getActivityTitle())) {
            return ResultUtil.fail("活动标题未填写");
        }
        if (StringUtils.isBlank(activity.getActivityDetail())) {
            return ResultUtil.fail("活动内容未填写");
        }
        if (activity.getActivityTime() == null) {
            return ResultUtil.fail("活动时间未填写");
        }
        // 获取登录id，并设置当前用户id到activity中
        User user = UserHolder.getUser();
        int userId = user.getId();
        activity.setActivityOwnerId(userId);
        boolean save = this.save(activity);
        if (!save) {
            return ResultUtil.fail(ERROR_SAVE_HELP, "活动发布失败");
        }
        // 上传图片获取url
        ArrayList<String> fileNameList = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                String fileName = FileUtils.fileUpload(file);
                if (StringUtils.isBlank(fileName)) {
                    return ResultUtil.fail("图片上传异常");
                }
                String url = "http://localhost:8080/image/" + fileName;
                fileNameList.add(fileName);
                Image image = new Image();
                image.setImageType(ACTIVITY.ordinal());
                image.setImageSrcId(activity.getId());
                image.setImageUrl(url);
                boolean save1 = imageService.save(image);
                if (!save1) {
                    return ResultUtil.fail(ERROR_SAVE_IMAGE, "图片保存失败");
                }
            }
        }

        //返回互助对象及文件名称
        return ResultUtil.ok(true);
    }

    @Override
    public Result updateActivity(String activityJson, MultipartFile[] files) {
        if (StringUtils.isBlank(activityJson)) {
            return ResultUtil.fail(ERROR_PARAMS, "请求参数错误");
        }
        Activity activity = gson.fromJson(activityJson, Activity.class);
        if (StringUtils.isBlank(activity.getActivityTitle())) {
            return ResultUtil.fail("活动标题未填写");
        }
        if (StringUtils.isBlank(activity.getActivityDetail())) {
            return ResultUtil.fail("活动内容未填写");
        }
        if (activity.getActivityTime() == null) {
            return ResultUtil.fail("活动时间未填写");
        }
        // 判断是否有权限（id）
        User user = UserHolder.getUser();
        int userId = user.getId();
        if (userId != activity.getActivityOwnerId()) {
            return ResultUtil.fail("没有权限修改");
        }
        QueryWrapper<Image> queryWrapper = new QueryWrapper();
        queryWrapper.eq("image_type", ACTIVITY.ordinal()).eq("image_src_id", activity.getId());
        boolean remove = imageService.remove(queryWrapper);
        if (!remove) {
            return ResultUtil.fail("删除失败");
        }
        // 上传图片获取url
        ArrayList<String> fileNameList = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                String fileName = FileUtils.fileUpload(file);
                if (StringUtils.isBlank(fileName)) {
                    return ResultUtil.fail("图片上传异常");
                }
                String url = "http://localhost:8080/image/" + fileName;
                fileNameList.add(fileName);
                Image image = new Image();
                image.setImageType(ACTIVITY.ordinal());
                image.setImageSrcId(activity.getId());
                image.setImageUrl(url);
                boolean save1 = imageService.save(image);
                if (!save1) {
                    return ResultUtil.fail(ERROR_SAVE_IMAGE, "图片保存失败");
                }
            }
        }

        boolean b = this.updateById(activity);
        if (!b) {
            return ResultUtil.fail("修改失败");
        }

        return ResultUtil.ok("修改成功");
    }

}




