package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.model.domain.CollectRequest;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Collect;
import com.cohelp.server.service.CollectService;
import com.cohelp.server.mapper.CollectMapper;
import com.cohelp.server.utils.ResultUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.cohelp.server.constant.StatusCode.*;

/**
* @author zgy
* @description 针对表【collect(收藏表)】的数据库操作Service实现
* @createDate 2022-10-25 12:09:43
*/
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect>
    implements CollectService{

    @Override
    public Result getCollectList(CollectRequest collectRequest) {
        if(ObjectUtils.anyNull(collectRequest)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Integer pageNum = collectRequest.getPageNum();
        Integer recordMaxNum = collectRequest.getRecordMaxNum();
        if(ObjectUtils.anyNull(pageNum,recordMaxNum)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        Page<Collect> collectPage = getBaseMapper().selectPage(new Page<>(pageNum, recordMaxNum),
                new QueryWrapper<Collect>().select().orderByDesc("collect_time"));

        if(ObjectUtils.anyNull(collectPage)){
            return ResultUtil.fail(ERROR_GET_DATA,"数据查询失败！");
        }
        else{
            List<Collect> records = collectPage.getRecords();
            return ResultUtil.returnResult(SUCCESS_GET_DATA,records,"数据获取成功！");
        }
    }
    @Override
    public Result insertCollectRecord(Collect collect) {
        if(ObjectUtils.anyNull(collect)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空");
        }
        Integer topicId = collect.getTopicId();
        Integer topicType = collect.getTopicType();
        Integer userId = collect.getUserId();
        Date collectTime = collect.getCollectTime();
        if(ObjectUtils.anyNull(topicId,userId,collectTime)||!TypeEnum.isTopic(topicType)){
            return ResultUtil.fail(ERROR_PARAMS,"参数不合法");
        }
        boolean bool = saveOrUpdate(collect);
        if(bool)
            return ResultUtil.ok(SUCCESS_REQUEST,"记录插入/更新成功！");
        else
            return ResultUtil.fail(ERROR_REQUEST,"记录插入失败");
    }

    @Override
    public Result deleteCollectRecord(String id) {
        if(ObjectUtils.anyNull(id)){
            return ResultUtil.fail(ERROR_PARAMS,"参数为空！");
        }
        if(ObjectUtils.anyNull(getById(id))){
            return ResultUtil.fail(ERROR_PARAMS,"记录不存在！");
        }
        boolean bool = removeById(id);
        if(bool)
            return ResultUtil.ok(SUCCESS_REQUEST,"记录删除成功！");
        else
            return ResultUtil.fail(ERROR_REQUEST,"记录删除失败！");
    }
}




