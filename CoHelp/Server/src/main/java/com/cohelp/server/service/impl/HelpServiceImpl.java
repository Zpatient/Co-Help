package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.model.domain.HelpResponse;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Activity;
import com.cohelp.server.model.entity.Help;
import com.cohelp.server.model.entity.Image;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.model.vo.ActivityVO;
import com.cohelp.server.model.vo.HelpVO;
import com.cohelp.server.service.HelpService;
import com.cohelp.server.mapper.HelpMapper;
import com.cohelp.server.service.ImageService;
import com.cohelp.server.service.UserService;
import com.cohelp.server.utils.FileUtils;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.UserHolder;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.cohelp.server.constant.NumberConstant.ONE_DAY_MILLI;
import static com.cohelp.server.constant.StatusCode.*;
import static com.cohelp.server.constant.TypeEnum.*;

/**
* @author jianping5
* @description 针对表【help(互助表)】的数据库操作Service实现
* @createDate 2022-10-21 21:25:55
*/
@Service("helpService")
@Slf4j
public class HelpServiceImpl extends ServiceImpl<HelpMapper, Help>
    implements HelpService{


    @Resource
    private ImageService imageService;

    @Resource
    private UserService userService;

    @Resource
    private HelpMapper helpMapper;

    @Override
    public Result<Boolean> publishHelp(String helpJson, MultipartFile[] files) {
        if (StringUtils.isAnyBlank(helpJson)) {
            return ResultUtil.fail(ERROR_PARAMS, "请求参数错误");
        }
        Gson gson = new Gson();
        Help help = gson.fromJson(helpJson, Help.class);
        if (StringUtils.isBlank(help.getHelpTitle())) {
            return ResultUtil.fail("互助标题未填写");
        }
        if (StringUtils.isBlank(help.getHelpDetail())) {
            return ResultUtil.fail("互助内容未填写");
        }
        // 获取登录id，并设置当前用户id到help中
        User user = UserHolder.getUser();
        int userId = user.getId();
        help.setHelpOwnerId(userId);
        boolean save = this.save(help);
        if (!save) {
            return ResultUtil.fail(ERROR_SAVE_HELP, "互助发布失败");
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
                image.setImageType(HELP.ordinal());
                image.setImageSrcId(help.getId());
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
    public Result updateHelp(String helpJson, MultipartFile[] files) {
        if (StringUtils.isAnyBlank(helpJson)) {
            return ResultUtil.fail(ERROR_PARAMS, "请求参数错误");
        }
        Gson gson = new Gson();
        Help help = gson.fromJson(helpJson, Help.class);
        if (StringUtils.isBlank(help.getHelpTitle())) {
            return ResultUtil.fail("互助标题未填写");
        }
        if (StringUtils.isBlank(help.getHelpDetail())) {
            return ResultUtil.fail("互助内容未填写");
        }
        // 判断是否有权限（id）
        User currentUser = UserHolder.getUser();
        int userId = currentUser.getId();
        if (userId != help.getHelpOwnerId()) {
            return ResultUtil.fail("没有权限修改");
        }
        // 删除之前该树洞相关的图片
        QueryWrapper<Image> queryWrapper = new QueryWrapper();
        queryWrapper.eq("image_type", HELP.ordinal()).eq("image_src_id", help.getId());
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
                image.setImageType(HELP.ordinal());
                image.setImageSrcId(help.getId());
                image.setImageUrl(url);
                boolean save1 = imageService.save(image);
                if (!save1) {
                    return ResultUtil.fail(ERROR_SAVE_IMAGE, "图片保存失败");
                }
            }
        }

        boolean b = this.updateById(help);
        if (!b) {
            return ResultUtil.fail("修改失败");
        }
        return ResultUtil.ok("修改成功");
    }

    @Override
    public Result<List<HelpVO>> listByCondition(Integer conditionType) {
        if (conditionType == null) {
            return ResultUtil.fail(ERROR_PARAMS);
        }
        // 创建互助视图体数组
        List<HelpVO> helpVOList = new ArrayList<>();

        // 按热度排序（并将活动信息和对应发布者部分信息注入到活动视图体中）
        if (conditionType == 0) {
            List<Help> helpList = helpMapper.listByHot();
            if (helpList == null) {
                return ResultUtil.fail(ERROR_PARAMS, "暂无互助");
            }
            helpList.forEach(help ->
                    helpVOList.add(traverseHelp(help))
            );
        }

        // 按时间排序
        if (conditionType == 1) {
            //  按发布时间排序
            QueryWrapper<Help> helpQueryWrapper = new QueryWrapper<>();
            helpQueryWrapper.orderByDesc("help_create_time");
            List<Help> helpList = helpMapper.selectList(helpQueryWrapper);
            if (helpList == null) {
                return ResultUtil.fail(ERROR_PARAMS, "暂无互助");
            }
            helpList.forEach(help ->
                    helpVOList.add(traverseHelp(help))
            );
        }

        // 查询有偿的互助
        if (conditionType == 2) {
            QueryWrapper<Help> helpQueryWrapper = new QueryWrapper<>();
            helpQueryWrapper.eq("help_paid", 1);
            List<Help> helpList = helpMapper.selectList(helpQueryWrapper);
            if (helpList == null) {
                return ResultUtil.fail(ERROR_PARAMS, "暂无有偿互助");
            }
            helpList.forEach(help ->
                    helpVOList.add(traverseHelp(help))
            );
        }

        // 查询无偿的互助
        if (conditionType == 3) {
            QueryWrapper<Help> helpQueryWrapper = new QueryWrapper<>();
            helpQueryWrapper.eq("help_paid", 0);
            List<Help> helpList = helpMapper.selectList(helpQueryWrapper);
            if (helpList == null) {
                return ResultUtil.fail(ERROR_PARAMS, "暂无无偿互助");
            }
            helpList.forEach(help ->
                    helpVOList.add(traverseHelp(help))
            );
        }
        return ResultUtil.ok(helpVOList);
    }

    @Override
    public Result<List<HelpVO>> listByTag(String tag) {
        if (tag == null) {
            return ResultUtil.fail(ERROR_PARAMS);
        }
        // 创建互助视图体数组
        List<HelpVO> helpVOList = new ArrayList<>();

        List<Help> helpList = this.list();
        if (helpList == null) {
            return ResultUtil.fail(ERROR_PARAMS, "暂无互助");
        }
        helpList.forEach(help -> {
            String helpTag = help.getHelpLabel();
            if (helpTag != null && helpTag.contains(tag)) {
                helpVOList.add(traverseHelp(help));
            }
        });
        return ResultUtil.ok(helpVOList);
    }


    /**
     * 遍历互助，将互助信息和对应发布者部分信息注入到互助视图体中
     * @param help
     * @return
     */
    public HelpVO traverseHelp(Help help) {
        HelpVO helpVO = new HelpVO();
        BeanUtils.copyProperties(help, helpVO);
        User user = userService.getById(help.getHelpOwnerId());
        helpVO.setAvatar(user.getAvatar());
        helpVO.setUserName(user.getUserName());
        return helpVO;
    }
}




