package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.model.domain.*;
import com.cohelp.server.model.entity.Activity;
import com.cohelp.server.model.entity.Help;
import com.cohelp.server.model.entity.Hole;
import com.cohelp.server.model.vo.ActivityVO;
import com.cohelp.server.model.vo.HelpVO;
import com.cohelp.server.model.vo.HoleVO;
import com.cohelp.server.service.*;
import com.cohelp.server.utils.ResultUtil;
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
        IdAndTypeMap idAndTypeMap = new IdAndTypeMap();
        HashMap<Integer, Integer> idAndTypeList = new HashMap<>();
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
                    idAndTypeList.put(id,1);
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
                    idAndTypeList.put(id, 2);
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
                    idAndTypeList.put(id, 3);
                }
            }
        }
        idAndTypeMap.setIdAndTypeMap(idAndTypeList);
        return ResultUtil.ok(SUCCESS_GET_DATA, idAndTypeMap,"数据查询成功！");
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
