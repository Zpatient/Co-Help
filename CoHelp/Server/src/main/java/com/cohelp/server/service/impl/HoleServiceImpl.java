package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.*;
import com.cohelp.server.model.vo.HoleVO;
import com.cohelp.server.service.HoleService;
import com.cohelp.server.mapper.HoleMapper;
import com.cohelp.server.service.ImageService;
import com.cohelp.server.service.UserService;
import com.cohelp.server.utils.FileUtils;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.UserHolder;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.cohelp.server.constant.StatusCode.*;
import static com.cohelp.server.constant.TypeEnum.HOLE;

/**
* @author jianping5
* @description 针对表【hole(树洞表)】的数据库操作Service实现
* @createDate 2022-10-21 21:26:01
*/
@Service("holeService")
public class HoleServiceImpl
        extends ServiceImpl<HoleMapper, Hole>
    implements HoleService{

    @Resource
    private ImageService imageService;

    @Resource
    private UserService userService;

    @Resource
    private HoleMapper holeMapper;

    @Override
    public Result<Boolean> publishHole(String holeJson, MultipartFile[] files) {
        if (StringUtils.isBlank(holeJson)) {
            return ResultUtil.fail(ERROR_PARAMS, "请求参数错误");
        }
        Gson gson = new Gson();
        Hole hole = gson.fromJson(holeJson, Hole.class);

        // 获取登录id，不需要判断是否已经登录，因为在controller中会进行登录拦截
        User user = UserHolder.getUser();
        int userId = user.getId();
        if (StringUtils.isBlank(hole.getHoleTitle())) {
            return ResultUtil.fail("树洞标题未填写");
        }
        if (StringUtils.isBlank(hole.getHoleDetail())) {
            return ResultUtil.fail("树洞内容未填写");
        }
        // 设置当前用户id到hole中
        hole.setHoleOwnerId(userId);
        boolean save = this.save(hole);
        if (!save) {
            return ResultUtil.fail(ERROR_SAVE_HOLE, "树洞发布失败");
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
                image.setImageType(HOLE.ordinal());
                image.setImageSrcId(hole.getId());
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
    public Result updateHole(String holeJson, MultipartFile[] files) {
        if (StringUtils.isBlank(holeJson)) {
            return ResultUtil.fail(ERROR_PARAMS, "请求参数错误");
        }
        // 校验树洞信息
        Gson gson = new Gson();
        Hole hole = gson.fromJson(holeJson, Hole.class);
        if (StringUtils.isBlank(hole.getHoleTitle())) {
            return ResultUtil.fail("树洞标题未填写");
        }
        if (StringUtils.isBlank(hole.getHoleDetail())) {
            return ResultUtil.fail("树洞内容未填写");
        }
        // 判断是否有权限（id）
        User currentUser = UserHolder.getUser();
        int userId = currentUser.getId();
        if (userId != hole.getHoleOwnerId()) {
            return ResultUtil.fail("没有权限修改");
        }
        // 删除之前该树洞相关的图片
        QueryWrapper<Image> queryWrapper = new QueryWrapper();
        queryWrapper.eq("image_type", HOLE.ordinal()).eq("image_src_id", hole.getId());
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
                image.setImageType(HOLE.ordinal());
                image.setImageSrcId(hole.getId());
                image.setImageUrl(url);
                boolean save1 = imageService.save(image);
                if (!save1) {
                    return ResultUtil.fail(ERROR_SAVE_IMAGE, "图片保存失败");
                }
            }
        }
        boolean b = this.updateById(hole);
        if (!b) {
            return ResultUtil.fail("修改失败");
        }
        return ResultUtil.ok("修改成功");
    }

    @Override
    public Result<List<HoleVO>> listByCondition(Integer conditionType) {
        if (conditionType == null) {
            return ResultUtil.fail(ERROR_PARAMS);
        }
        // 创建活动视图体数组
        List<HoleVO> holeVOList = new ArrayList<>();

        // 按热度排序（并将活动信息和对应发布者部分信息注入到活动视图体中）
        if (conditionType == 0) {
            List<Hole> holeList = holeMapper.listByHot();
            if (holeList == null) {
                return ResultUtil.fail(ERROR_PARAMS, "暂无互助");
            }
            holeList.forEach(hole ->
                    holeVOList.add(traverseHole(hole))
            );
        }

        // 按时间排序
        if (conditionType == 1) {
            //  按发布时间排序
            QueryWrapper<Hole> holeQueryWrapper = new QueryWrapper<>();
            holeQueryWrapper.orderByDesc("hole_create_time");
            List<Hole> holeList = holeMapper.selectList(holeQueryWrapper);
            if (holeList == null) {
                return ResultUtil.fail(ERROR_PARAMS, "暂无互助");
            }
            holeList.forEach(hole ->
                    holeVOList.add(traverseHole(hole))
            );
        }
        return ResultUtil.ok(holeVOList);
    }

    /**
     * 遍历树洞，将树洞信息和对应发布者部分信息注入到树洞视图体中
     * @param hole
     * @return
     */
    public HoleVO traverseHole(Hole hole) {
        HoleVO holeVO = new HoleVO();
        BeanUtils.copyProperties(hole, holeVO);
        User user = userService.getById(hole.getHoleOwnerId());
        holeVO.setAvatar(user.getAvatar());
        holeVO.setUserName(user.getUserName());
        return holeVO;
    }

}




