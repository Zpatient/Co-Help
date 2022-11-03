package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.model.domain.*;
import com.cohelp.server.model.entity.*;
import com.cohelp.server.model.vo.ActivityVO;
import com.cohelp.server.model.vo.HelpVO;
import com.cohelp.server.model.vo.HoleVO;
import com.cohelp.server.service.*;
import com.cohelp.server.utils.ResultUtil;
import com.cohelp.server.utils.UserHolder;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import javax.annotation.Resource;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
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
    @Resource
    RemarkActivityService remarkActivityService;
    @Resource
    RemarkHelpService remarkHelpService;
    @Resource
    RemarkHoleService remarkHoleService;
    @Override
    public Result getDetail(IdAndType idAndType) {
        //判断参数合法性
        if(ObjectUtils.anyNull(idAndType)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Integer type = idAndType.getType();
        Integer id = idAndType.getId();
        if(!TypeEnum.isTopic(type)|| ObjectUtils.anyNull(id)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        //获取该话题对应的的图片URL列表
        Result images = imageService.getImageList(idAndType);
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
                ActivityServiceImpl activityServiceImpl = (ActivityServiceImpl)activityService;
                ActivityVO activityVO = activityServiceImpl.traverseActivity(activity);
                String publisherAvatarUrl = imageService.getById(activityVO.getAvatar()).getImageUrl();
                DetailResponse detailResponse = new DetailResponse();
                detailResponse.setActivityVO(activityVO);
                detailResponse.setPublisherAvatarUrl(publisherAvatarUrl);
                detailResponse.setImagesUrl(imagesUrl);
                return ResultUtil.returnResult(SUCCESS_GET_DATA,detailResponse,message);
            }
            else{
                return ResultUtil.returnResult(ERROR_GET_DATA,null,"数据获取失败");
            }
        }
        else if(TypeEnum.isHelp(type)){
            Help help = helpService.getById(id);
            if(ObjectUtils.anyNull(help)){
                String message = imageMessage+"基本数据获取成功！";
                HelpServiceImpl helpServiceImpl = (HelpServiceImpl)helpService;
                HelpVO helpVO = helpServiceImpl.traverseHelp(help);
                String publisherAvatarUrl = imageService.getById(helpVO.getAvatar()).getImageUrl();
                DetailResponse detailResponse = new DetailResponse();
                detailResponse.setHelpVO(helpVO);
                detailResponse.setPublisherAvatarUrl(publisherAvatarUrl);
                detailResponse.setImagesUrl(imagesUrl);
                return ResultUtil.returnResult(SUCCESS_GET_DATA,detailResponse,message);
            }
            else{
                return ResultUtil.returnResult(ERROR_GET_DATA,null,"数据获取失败");
            }
        }
        else {
            Hole hole = holeService.getById(id);
            if(ObjectUtils.anyNull(hole)) {
                String message = imageMessage + "基本数据获取成功！";
                HoleServiceImpl holeServiceImpl = (HoleServiceImpl)holeService;
                HoleVO holeVO = holeServiceImpl.traverseHole(hole);
                String publisherAvatarUrl = imageService.getById(holeVO.getAvatar()).getImageUrl();
                DetailResponse detailResponse = new DetailResponse();
                detailResponse.setHoleVO(holeVO);
                detailResponse.setPublisherAvatarUrl(publisherAvatarUrl);
                detailResponse.setImagesUrl(imagesUrl);
                return ResultUtil.returnResult(SUCCESS_GET_DATA,detailResponse, message);
            }
            else{
                return ResultUtil.returnResult(ERROR_GET_DATA,null,"数据获取失败");
            }
        }
    }

    @Override
    public Result search(SearchRequest searchRequest) {
        //判断参数合法性
        if(ObjectUtils.anyNull(searchRequest)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        String key = searchRequest.getKey();
        List<Integer> types = searchRequest.getTypes();
        if(!TypeEnum.isTopic(types)|| StringUtils.isBlank(key)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        //查询数据
        IdAndTypeList idAndTypes = new IdAndTypeList();
        List<IdAndType> idAndTypeList = new ArrayList<>();
        String[] keywords = getKeywords(key).split(",");
        for(Integer type : types){
            if(TypeEnum.isActivity(type)){
                QueryWrapper<Activity> queryWrapper = new QueryWrapper<Activity>()
                        .select("id")
                        .like("activity_title",key)
                        .or().like("activity_detail",key)
                        .or().like("activity_label",key);
                for(String keyword : keywords){
                    queryWrapper.or().like("activity_title",keyword)
                            .or().like("activity_detail",keyword)
                            .or().like("activity_label",keyword);
                }
                List<Activity> activityList = activityService.list(queryWrapper);
                for(Activity activity:activityList){
                    Integer id = activity.getId();
                    idAndTypeList.add(new IdAndType(id,type));
                }
            }
            else if(TypeEnum.isHelp(type)){
                QueryWrapper<Help> queryWrapper = new QueryWrapper<Help>()
                        .select("id")
                        .like("help_title",key)
                        .or().like("help_detail",key)
                        .or().like("help_label",key);
                for(String keyword : keywords){
                    queryWrapper.or().like("help_title",keyword)
                            .or().like("help_detail",keyword)
                            .or().like("help_label",keyword);
                }
                List<Help> helpList = helpService.list(queryWrapper);
                for(Help help:helpList) {
                    Integer id = help.getId();
                    idAndTypeList.add(new IdAndType(id,type));
                }
            }
            else{
                QueryWrapper<Hole> queryWrapper = new QueryWrapper<Hole>()
                        .select("id")
                        .like("hole_title",key)
                        .or().like("hole_detail",key)
                        .or().like("hole_label",key);
                for(String keyword : keywords){
                    queryWrapper.or().like("hole_title",keyword)
                            .or().like("hole_detail",keyword)
                            .or().like("hole_label",keyword);
                }
                List<Hole> holeList = holeService.list(queryWrapper);
                for(Hole hole:holeList) {
                    Integer id = hole.getId();
                    idAndTypeList.add(new IdAndType(id,type));
                }
            }
        }
        idAndTypes.setIdAndTypeList(idAndTypeList);
        return ResultUtil.ok(SUCCESS_GET_DATA, idAndTypes,"数据查询成功！");
    }

    @Override
    public Result insertRemark(RemarkRequest remarkRequest) {
        //判断参数合法性
        if(ObjectUtils.anyNull(remarkRequest)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        //插入评论到评论表
        Integer type = remarkRequest.getType();
        if(!TypeEnum.isTopic(type)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        else if(TypeEnum.isActivity(type)){
            RemarkActivity remarkActivity = remarkRequest.getRemarkActivity();
            if(ObjectUtils.anyNull(remarkActivity))
                return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
            //判断当前用户权限
            Integer remarkOwnerId = remarkActivity.getRemarkOwnerId();
            User user = UserHolder.getUser();
            if(!remarkOwnerId.equals(user.getId()))
                return ResultUtil.fail(ERROR_GET_DATA,"用户不一致！");
            //插入评论
            boolean result = remarkActivityService.saveOrUpdate(remarkActivity);
            if(!result)
                return ResultUtil.fail("评论失败！");
            else
                return ResultUtil.fail("评论成功！");
        }
        else if(TypeEnum.isHelp(type)){
            RemarkHelp remarkHelp = remarkRequest.getRemarkHelp();
            if(ObjectUtils.anyNull(remarkHelp))
                return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
            //判断当前用户权限
            Integer remarkOwnerId = remarkHelp.getRemarkOwnerId();
            User user = UserHolder.getUser();
            if(!remarkOwnerId.equals(user.getId()))
                return ResultUtil.fail(ERROR_GET_DATA,"用户不一致！");
            //插入评论
            boolean result = remarkHelpService.saveOrUpdate(remarkHelp);
            if(!result)
                return ResultUtil.fail("评论失败！");
            else
                return ResultUtil.fail("评论成功！");
        }
        else{
            RemarkHole remarkHole = remarkRequest.getRemarkHole();
            if(ObjectUtils.anyNull(remarkHole))
                return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
            //判断当前用户权限
            Integer remarkOwnerId = remarkHole.getRemarkOwnerId();
            User user = UserHolder.getUser();
            if(!remarkOwnerId.equals(user.getId()))
                return ResultUtil.fail(ERROR_GET_DATA,"用户不一致！");
            //插入评论
            boolean result = remarkHoleService.saveOrUpdate(remarkHole);
            if(!result)
                return ResultUtil.fail("评论失败！");
            else
                return ResultUtil.fail("评论成功！");
        }
    }

    @Override
    public Result deleteRemark(IdAndType idAndType) {
        //判断参数合法性
        if(ObjectUtils.anyNull(idAndType)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Integer type = idAndType.getType();
        Integer id = idAndType.getId();
        //根据type和id到相应评论表删除评论
        User user = UserHolder.getUser();
        if(ObjectUtils.anyNull(type,id)||!TypeEnum.isTopic(type)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        else if(TypeEnum.isActivity(type)){
            RemarkActivity remarkActivity = remarkActivityService.getById(id);
            Integer remarkOwnerId = remarkActivity.getRemarkOwnerId();
            if(!remarkOwnerId.equals(user.getId()))
                return ResultUtil.fail(ERROR_GET_DATA,"用户不一致！");
            boolean result = remarkActivityService.removeById(id);
            if(!result)
                return ResultUtil.fail("评论删除失败！");
            else
                return ResultUtil.ok("评论删除成功！");
        }
        else if(TypeEnum.isHelp(type)){
            RemarkHelp remarkHelp = remarkHelpService.getById(id);
            Integer remarkOwnerId = remarkHelp.getRemarkOwnerId();
            if(!remarkOwnerId.equals(user.getId()))
                return ResultUtil.fail(ERROR_GET_DATA,"用户不一致！");
            boolean result = remarkHelpService.removeById(id);
            if(!result)
                return ResultUtil.fail("评论删除失败！");
            else
                return ResultUtil.ok("评论删除成功！");
        }
        else{
            RemarkHole remarkHole = remarkHoleService.getById(id);
            Integer remarkOwnerId = remarkHole.getRemarkOwnerId();
            if(!remarkOwnerId.equals(user.getId()))
                return ResultUtil.fail(ERROR_GET_DATA,"用户不一致！");
            boolean result = remarkHoleService.removeById(id);
            if(!result)
                return ResultUtil.fail("评论删除失败！");
            else
                return ResultUtil.ok("评论删除成功！");
        }
    }

    @Override
    public Result getRemarkList(IdAndType idAndType) {
        //判断参数合法性
        if(ObjectUtils.anyNull(idAndType)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Integer type = idAndType.getType();
        Integer id = idAndType.getId();
        //根据type和id到相应评论表删除评论
        if(ObjectUtils.anyNull(type,id)||!TypeEnum.isTopic(type)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        else if(TypeEnum.isActivity(type)){
            QueryWrapper<RemarkActivity> remarkQueryWrapper = new QueryWrapper<RemarkActivity>().eq("top_id", id);
            List<RemarkActivity> remarkList = remarkActivityService.list(remarkQueryWrapper);
            return ResultUtil.returnResult(SUCCESS_GET_DATA,remarkList,"评论查询成功");
        }
        else if(TypeEnum.isHelp(type)){
            QueryWrapper<RemarkHelp> remarkQueryWrapper = new QueryWrapper<RemarkHelp>().eq("top_id", id);
            List<RemarkHelp> remarkList = remarkHelpService.list(remarkQueryWrapper);
            return ResultUtil.returnResult(SUCCESS_GET_DATA,remarkList,"评论查询成功");
        }
        else{
            QueryWrapper<RemarkHole> remarkQueryWrapper = new QueryWrapper<RemarkHole>().eq("top_id", id);
            List<RemarkHole> remarkList = remarkHoleService.list(remarkQueryWrapper);
            return ResultUtil.returnResult(SUCCESS_GET_DATA,remarkList,"评论查询成功");
        }
    }

    /**
     * @param key 搜索关键词
     * @return 分词词组(,拼接)
     */
    private String getKeywords(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        StringReader reader = new StringReader(key);
        IKSegmenter iks = new IKSegmenter(reader, true);
        StringBuilder buffer = new StringBuilder();
        try {
            Lexeme lexeme;
            while ((lexeme = iks.next()) != null) {
                buffer.append(lexeme.getLexemeText()).append(',');
            }
        } catch (IOException e) {
        }
        if (buffer.length() > 0) {
            buffer.setLength(buffer.length() - 1);
        }
        return buffer.toString();
    }
}
