package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.constant.HelpTypeConstant;
import com.cohelp.server.model.domain.DetailResponse;
import com.cohelp.server.model.domain.IdAndType;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Help;
import com.cohelp.server.model.entity.Image;
import com.cohelp.server.model.entity.TopicLike;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.model.vo.HelpVO;
import com.cohelp.server.service.*;
import com.cohelp.server.mapper.HelpMapper;
import com.cohelp.server.utils.FileUtils;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.SensitiveUtils;
import com.cohelp.server.utils.UserHolder;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Resource
    private GeneralService generalService;

    @Resource
    private FileUtils fileUtils;

    @Value("${spring.tengxun.url}")
    private String path;

    @Resource
    private TopicLikeService topicLikeService;

    @Override
    public Result<Boolean> publishHelp(String helpJson, MultipartFile[] files) {
        if (StringUtils.isAnyBlank(helpJson)) {
            return ResultUtil.fail(ERROR_PARAMS, "请求参数错误");
        }
        Gson gson = new Gson();
        Help help = gson.fromJson(helpJson, Help.class);

        // 判断是否包含敏感词
        String helpLabel = help.getHelpLabel();
        String helpDetail = help.getHelpDetail();
        String helpTitle = help.getHelpTitle();
        if (SensitiveUtils.contains(helpLabel, helpDetail, helpTitle)) {
            return ResultUtil.fail("文本涉及敏感词汇");
        }

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

        // 设置对应的组织id到活动中
        help.setTeamId(user.getTeamId());

        boolean save = this.save(help);
        if (!save) {
            return ResultUtil.fail(ERROR_SAVE_HELP, "互助发布失败");
        }
        // 上传图片获取url
        ArrayList<String> fileNameList = new ArrayList<>();
        if (files != null && files.length > 0 && !"".equals(files[0].getOriginalFilename())) {
            for (MultipartFile file : files) {
                String fileName = fileUtils.fileUpload(file);
                if (StringUtils.isBlank(fileName)) {
                    return ResultUtil.fail("图片上传异常");
                }
                String url = path + fileName;
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

        // 判断是否包含敏感词
        String helpLabel = help.getHelpLabel();
        String helpDetail = help.getHelpDetail();
        String helpTitle = help.getHelpTitle();
        if (SensitiveUtils.contains(helpLabel, helpDetail, helpTitle)) {
            return ResultUtil.fail("文本涉及敏感词汇");
        }

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

        // 上传图片获取url
        ArrayList<String> fileNameList = new ArrayList<>();
        if (files != null && files.length > 0 && !"".equals(files[0].getOriginalFilename())) {
            for (MultipartFile file : files) {
                String fileName = fileUtils.fileUpload(file);
                if (StringUtils.isBlank(fileName)) {
                    return ResultUtil.fail("图片上传异常");
                }
                String url = path + fileName;
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
    public Result<List<DetailResponse>> listByCondition(Integer conditionType) {

        // 获取当前登录用户的组织id
        User user = UserHolder.getUser();
        Integer teamId = user.getTeamId();

        if (conditionType == null) {
            return ResultUtil.fail(ERROR_PARAMS);
        }
        // 创建互助视图体数组
        List<DetailResponse> detailResponseList = new ArrayList<>();

        // 按热度排序（并将活动信息和对应发布者部分信息注入到活动视图体中）
        if (conditionType == 0) {
            List<Help> helpList = helpMapper.listByHot(teamId);
            if (helpList == null) {
                return ResultUtil.fail(ERROR_PARAMS, "暂无互助");
            }
            helpList.forEach(help ->
                    detailResponseList.add(getDetailResponse(help))
            );
        }

        // 按时间排序
        if (conditionType == 1) {
            //  按发布时间排序
            QueryWrapper<Help> helpQueryWrapper = new QueryWrapper<>();
            helpQueryWrapper.orderByDesc("help_create_time");
            helpQueryWrapper.eq("team_id", teamId);
            helpQueryWrapper.eq("help_state", 0);
            List<Help> helpList = helpMapper.selectList(helpQueryWrapper);
            if (helpList == null) {
                return ResultUtil.fail(ERROR_PARAMS, "暂无互助");
            }
            helpList.forEach(help ->
                    detailResponseList.add(getDetailResponse(help))
            );
        }

        // 查询有偿的互助
        if (conditionType == 2) {
            QueryWrapper<Help> helpQueryWrapper = new QueryWrapper<>();
            helpQueryWrapper.eq("help_paid", 1);
            helpQueryWrapper.eq("team_id", teamId);
            helpQueryWrapper.eq("help_state", 0);
            List<Help> helpList = helpMapper.selectList(helpQueryWrapper);
            if (helpList == null) {
                return ResultUtil.fail(ERROR_PARAMS, "暂无有偿互助");
            }
            helpList.forEach(help ->
                    detailResponseList.add(getDetailResponse(help))
            );
        }

        // 查询无偿的互助
        if (conditionType == 3) {
            QueryWrapper<Help> helpQueryWrapper = new QueryWrapper<>();
            helpQueryWrapper.eq("help_paid", 0);
            helpQueryWrapper.eq("team_id", teamId);
            helpQueryWrapper.eq("help_state", 0);
            List<Help> helpList = helpMapper.selectList(helpQueryWrapper);
            if (helpList == null) {
                return ResultUtil.fail(ERROR_PARAMS, "暂无无偿互助");
            }
            helpList.forEach(help ->
                    detailResponseList.add(getDetailResponse(help))
            );
        }
        return ResultUtil.ok(detailResponseList);
    }

    @Override
    public Result<List<DetailResponse>> listByTag(String tag) {

        // 获取当前登录用户的组织id
        User user = UserHolder.getUser();
        Integer teamId = user.getTeamId();


        if (tag == null) {
            return ResultUtil.fail(ERROR_PARAMS);
        }
        // 创建互助视图体数组
        List<DetailResponse> detailResponseList = new ArrayList<>();

        // 查询当前团体内的所有活动(按热度和时间综合排序）
        List<Help> helpList = helpMapper.listByHotAndTime(teamId);

        if (helpList == null) {
            return ResultUtil.fail(ERROR_PARAMS, "暂无互助");
        }

        // 若查询全部
        if (tag.equals(HelpTypeConstant.HELP_ALL)) {
            for (Help help : helpList) {
                detailResponseList.add(getDetailResponse(help));
            }
            return ResultUtil.ok(detailResponseList);
        }

        // 若查询其他
        if (tag.equals(HelpTypeConstant.HELP_OTHER)) {
            for (Help help : helpList) {
                String helpTag = help.getHelpLabel();
                if (helpTag != null && isTypeOther(helpTag)) {
                    detailResponseList.add(getDetailResponse(help));
                }
            }
            return ResultUtil.ok(detailResponseList);
        }

        // 若查询特定标签
        helpList.forEach(help -> {
            String helpTag = help.getHelpLabel();
            if (helpTag != null && helpTag.contains(tag)) {
                detailResponseList.add(getDetailResponse(help));
            }
        });
        return ResultUtil.ok(detailResponseList);
    }

    /**
     * 获取 DetailResponse
     * @param help
     * @return
     */
    public DetailResponse getDetailResponse(Help help) {
        DetailResponse detailResponse = new DetailResponse();
        // 注入 HelpVO
        HelpVO helpVO = traverseHelp(help);
        detailResponse.setHelpVO(helpVO);

        // 注入点赞判定值
        QueryWrapper<TopicLike> topicLikeQueryWrapper = new QueryWrapper<>();
        topicLikeQueryWrapper.eq("user_id", helpVO.getHelpOwnerId())
                .eq("topic_type", 1)
                .eq("topic_id", helpVO.getId());
        TopicLike topicLike = topicLikeService.getOne(topicLikeQueryWrapper);
        if (topicLike == null) {
            detailResponse.setIsLiked(0);
        } else {
            detailResponse.setIsLiked(topicLike.getIsLiked());
        }


        // 注入发布者图片
        String publisherAvatarUrl = imageService.getById(helpVO.getAvatar()).getImageUrl();
        detailResponse.setPublisherAvatarUrl(publisherAvatarUrl);

        //获取该话题对应的的图片URL列表
        IdAndType idAndType = new IdAndType();
        idAndType.setType(HELP.ordinal());
        idAndType.setId(help.getId());
        ArrayList<String> imagesUrl = imageService.getImageList(idAndType);
        if(ObjectUtils.anyNull(imagesUrl)){
            imagesUrl = new ArrayList<>();
        }
        detailResponse.setImagesUrl(imagesUrl);

        return detailResponse;
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

    /**
     * 判断标签是否为其他
     * @param tag
     * @return
     */
    public boolean isTypeOther(String tag) {
        if (!(tag.equals(HelpTypeConstant.HELP_TEAM) || tag.equals(HelpTypeConstant.HELP_FIND) ||
        tag.equals(HelpTypeConstant.HELP_RUN) || tag.equals(HelpTypeConstant.HELP_PROBLEM))) {
            return true;
        }
        return false;
    }

}




