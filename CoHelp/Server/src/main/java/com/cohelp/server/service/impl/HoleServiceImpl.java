package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.mapper.HoleMapper;
import com.cohelp.server.model.domain.IdAndType;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.*;
import com.cohelp.server.model.vo.DetailResponse;
import com.cohelp.server.model.vo.HoleVO;
import com.cohelp.server.service.*;
import com.cohelp.server.utils.FileUtils;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.SensitiveUtils;
import com.cohelp.server.utils.UserHolder;
import com.google.gson.Gson;
import com.ruibty.nsfw.NsfwService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
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

    @Resource
    private GeneralService generalService;

    @Resource
    private FileUtils fileUtils;

    @Resource
    private CollectService collectService;

    @Value("${spring.tengxun.url}")
    private String path;

    @Resource
    private TopicLikeService topicLikeService;

    @Autowired
    private NsfwService nsfwService;

    @Value("${threshold}")
    private String threshold;

    @Override
    public Result<Boolean> publishHole(String holeJson, MultipartFile[] files) {
        if (StringUtils.isBlank(holeJson)) {
            return ResultUtil.fail(ERROR_PARAMS, "请求参数错误");
        }
        Gson gson = new Gson();
        Hole hole = gson.fromJson(holeJson, Hole.class);

        // 判断是否包含敏感词
        String holeLabel = hole.getHoleLabel();
        String holeTitle = hole.getHoleTitle();
        String holeDetail = hole.getHoleDetail();
        if (SensitiveUtils.contains(holeLabel, holeTitle, holeDetail)) {
            return ResultUtil.fail("文本涉及敏感词汇");
        }

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

        // 设置对应的组织id到活动中
        hole.setTeamId(user.getTeamId());

        boolean save = this.save(hole);
        if (!save) {
            return ResultUtil.fail(ERROR_SAVE_HOLE, "树洞发布失败");
        }
        // 上传图片获取url
        ArrayList<String> fileNameList = new ArrayList<>();
        if (files != null && files.length > 0 && !"".equals(files[0].getOriginalFilename())) {
            for (MultipartFile file : files) {
                //图片检测，当该图片的预测值超过阈值则忽略上传
                try {
                    byte[] bytes = file.getBytes();
                    float prediction = nsfwService.getPrediction(bytes);
                    if(prediction>new Float(threshold)){
                        continue;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String fileName = fileUtils.fileUpload(file);
                if (StringUtils.isBlank(fileName)) {
                    return ResultUtil.fail("图片上传异常");
                }
                String url = path + fileName;
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

        // 判断是否包含敏感词
        String holeLabel = hole.getHoleLabel();
        String holeTitle = hole.getHoleTitle();
        String holeDetail = hole.getHoleDetail();
        if (SensitiveUtils.contains(holeLabel, holeTitle, holeDetail)) {
            return ResultUtil.fail("文本涉及敏感词汇");
        }

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

        // 上传图片获取url
        ArrayList<String> fileNameList = new ArrayList<>();
        if (files != null && files.length > 0 && !"".equals(files[0].getOriginalFilename())) {
            for (MultipartFile file : files) {
                //图片检测，当该图片的预测值超过阈值则忽略上传
                try {
                    byte[] bytes = file.getBytes();
                    float prediction = nsfwService.getPrediction(bytes);
                    if(prediction>new Float(threshold)){
                        continue;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String fileName = fileUtils.fileUpload(file);
                if (StringUtils.isBlank(fileName)) {
                    return ResultUtil.fail("图片上传异常");
                }
                String url = path + fileName;
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
    public Result<List<DetailResponse>> listByCondition(Integer conditionType) {

        // 获取当前登录用户的组织id
        User user = UserHolder.getUser();
        Integer teamId = user.getTeamId();

        if (conditionType == null) {
            return ResultUtil.fail(ERROR_PARAMS);
        }
        // 创建活动视图体数组
        List<DetailResponse> detailResponseList = new ArrayList<>();

        // 按热度排序（并将活动信息和对应发布者部分信息注入到活动视图体中）
        if (conditionType == 0) {
            List<Hole> holeList = holeMapper.listByHot(teamId);
            if (holeList == null) {
                return ResultUtil.fail(ERROR_PARAMS, "暂无互助");
            }
            holeList.forEach(hole ->
                    detailResponseList.add(getDetailResponse(hole))
            );
        }

        // 按时间排序
        if (conditionType == 1) {
            //  按发布时间排序
            QueryWrapper<Hole> holeQueryWrapper = new QueryWrapper<>();
            holeQueryWrapper.orderByDesc("hole_create_time");
            holeQueryWrapper.eq("team_id", teamId);
            holeQueryWrapper.eq("hole_state", 0);
            List<Hole> holeList = holeMapper.selectList(holeQueryWrapper);
            if (holeList == null) {
                return ResultUtil.fail(ERROR_PARAMS, "暂无互助");
            }
            holeList.forEach(hole ->
                    detailResponseList.add(getDetailResponse(hole))
            );
        }
        return ResultUtil.ok(detailResponseList);
    }


    /**
     * 获取 DetailResponse
     * @param hole
     * @return
     */
    @Override
    public DetailResponse getDetailResponse(Hole hole) {
        DetailResponse detailResponse = new DetailResponse();
        // 注入 ActivityVO
        HoleVO holeVO = traverseHole(hole);
        detailResponse.setHoleVO(holeVO);

        // 注入点赞判定值
        QueryWrapper<TopicLike> topicLikeQueryWrapper = new QueryWrapper<>();
        topicLikeQueryWrapper.eq("user_id", holeVO.getHoleOwnerId())
                .eq("topic_type", 1)
                .eq("topic_id", holeVO.getId());
        TopicLike topicLike = topicLikeService.getOne(topicLikeQueryWrapper);
        if (topicLike == null) {
            detailResponse.setIsLiked(0);
        } else {
            detailResponse.setIsLiked(topicLike.getIsLiked());
        }

        // 注入收藏判定值
        QueryWrapper<Collect> collectQueryWrapper = new QueryWrapper<>();
        collectQueryWrapper.eq("user_id", UserHolder.getUser().getId())
                .eq("topic_type", 1)
                .eq("topic_id", holeVO.getId());
        Collect one = collectService.getOne(collectQueryWrapper);
        if (one == null) {
            detailResponse.setIsCollected(0);
        } else {
            detailResponse.setIsCollected(1);
        }

        // 注入发布者图片
        String publisherAvatarUrl = imageService.getById(holeVO.getAvatar()).getImageUrl();
        detailResponse.setPublisherAvatarUrl(publisherAvatarUrl);

        //获取该话题对应的的图片URL列表
        IdAndType idAndType = new IdAndType();
        idAndType.setType(HOLE.ordinal());
        idAndType.setId(hole.getId());
        ArrayList<String> imagesUrl = imageService.getImageList(idAndType);
        if(ObjectUtils.anyNull(imagesUrl)){
            imagesUrl = new ArrayList<>();
        }
        detailResponse.setImagesUrl(imagesUrl);

        return detailResponse;
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




