package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.mapper.InformMapper;
import com.cohelp.server.model.PageResponse;
import com.cohelp.server.model.domain.DetailResponse;
import com.cohelp.server.model.domain.IdAndType;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.domain.UserOrTopicOrRemark;
import com.cohelp.server.model.entity.Inform;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.model.vo.DetailRemark;
import com.cohelp.server.model.vo.InformVO;
import com.cohelp.server.service.GeneralService;
import com.cohelp.server.service.InformService;
import com.cohelp.server.service.UserService;
import com.cohelp.server.utils.ResultUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.cohelp.server.constant.StatusCode.*;

/**
 * @author zgy
 * @description 针对表【inform(举报表)】的数据库操作Service实现
 * @createDate 2022-10-20 18:15:55
 */
@Service
public class InformServiceImpl extends ServiceImpl<InformMapper, Inform>
        implements InformService {
    @Resource
    GeneralService generalService;
    @Resource
    UserService userService;
    @Resource
    InformMapper informMapper;
    @Override
    public Result submitInform(Inform inform) {
        if (ObjectUtils.isEmpty(inform))
            return ResultUtil.fail(ERROR_PARAMS, "参数为空");
        if (ObjectUtils.anyNull(inform.getInformedInstanceId(), inform.getInformedInstanceType(), inform.getInformType()
                , inform.getInformContent(), inform.getCreateTime(), inform.getInformerId()))
            return ResultUtil.fail(ERROR_PARAMS, "参数不完整");
        if (this.saveOrUpdate(inform))
            return ResultUtil.ok(SUCCESS_REQUEST, "举报成功");
        else
            return ResultUtil.ok(ERROR_REQUEST, "举报失败");
    }

    @Override
    public PageResponse<InformVO> listInforms(Integer page, Integer limit, Integer teamId) {
        if(ObjectUtils.anyNull(page,limit,teamId)){
            return null;
        }
        Page<Inform> informPage = new Page<>(page, limit);
        Page<Inform> informs = informMapper.selectPage(informPage, new QueryWrapper<Inform>().eq("team_id", teamId));
        List<Inform> records = informs.getRecords();
        ArrayList<InformVO> informVOS = new ArrayList<>();
        for (Inform inform:records){
            InformVO informVO = traverseInform(inform);
            informVOS.add(informVO);
        }
        return new PageResponse<InformVO>(informVOS,informPage.getTotal());
    }

    @Override
    public String deleteInform(Integer id) {
        if(id==null){
            return "参数为空！";
        }
        Inform byId = getById(id);
        if (byId==null){
            return "举报已删除！";
        }
        boolean b = removeById(byId);
        if(b){
            return "删除成功！";
        }else {
            return "删除失败！";
        }
    }

    @Override
    public UserOrTopicOrRemark getInformTarget(IdAndType idAndType) {
        if(idAndType==null){
            return null;
        }
        UserOrTopicOrRemark userOrTopicOrRemark = new UserOrTopicOrRemark();
        Integer type = idAndType.getType();
        if(TypeEnum.isTopic(type)){
            DetailResponse detailResponse = generalService.getDetailResponse(idAndType);
            userOrTopicOrRemark.setDetailResponse(detailResponse);
            userOrTopicOrRemark.setType(type);
            return userOrTopicOrRemark;
        }else if(TypeEnum.isRemark(type)){
            DetailRemark detailRemark = generalService.getDetailRemark(idAndType);
            userOrTopicOrRemark.setDetailRemark(detailRemark);
            userOrTopicOrRemark.setType(type);
            return userOrTopicOrRemark;
        }else if(TypeEnum.isUser(type)){
            User user = userService.getById(idAndType.getId());
            if(user!=null){
                User safetyUser = userService.getSafetyUser(user);
                userOrTopicOrRemark.setUser(safetyUser);
            }
            userOrTopicOrRemark.setType(type);
            return userOrTopicOrRemark;
        }else {
            return null;
        }
    }
    public InformVO traverseInform(Inform inform){
        if(inform==null){
            return null;
        }
        InformVO informVO = new InformVO();
        BeanUtils.copyProperties(inform,informVO);
        User user = userService.getById(inform.getInformerId());
        if (user!=null){
            informVO.setInformerName(user.getUserName());
        }
        Integer informedInstanceType = inform.getInformedInstanceType();
        if(informedInstanceType!=null){
            switch(informedInstanceType){
                case 0 : informVO.setInformedTypeStr("用户");break;
                case 1 : informVO.setInformedTypeStr("活动");break;
                case 2 : informVO.setInformedTypeStr("互助");break;
                case 3 : informVO.setInformedTypeStr("树洞");break;
                case 4 : informVO.setInformedTypeStr("活动评论");break;
                case 5 : informVO.setInformedTypeStr("互助评论");break;
                case 6 : informVO.setInformedTypeStr("树洞评论");break;
                default: informVO.setInformedTypeStr("未知类型");
            }
        }
        informVO.setInformTime(inform.getCreateTime());
        return informVO;
    }
}




