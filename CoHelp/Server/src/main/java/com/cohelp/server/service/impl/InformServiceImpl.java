package com.cohelp.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.entity.Inform;
import com.cohelp.server.service.InformService;
import com.cohelp.server.mapper.InformMapper;
import com.cohelp.server.utils.ResultUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static com.cohelp.server.constant.StatusCode.*;

/**
* @author zgy
* @description 针对表【inform(举报表)】的数据库操作Service实现
* @createDate 2022-10-20 18:15:55
*/
@Service
public class InformServiceImpl extends ServiceImpl<InformMapper, Inform>
    implements InformService {

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

}




