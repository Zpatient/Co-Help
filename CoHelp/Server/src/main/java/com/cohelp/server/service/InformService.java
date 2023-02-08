package com.cohelp.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cohelp.server.model.domain.IdAndType;
import com.cohelp.server.model.domain.PageResponse;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.domain.UserOrTopicOrRemark;
import com.cohelp.server.model.entity.Inform;
import com.cohelp.server.model.vo.InformVO;

/**
 * @author zgy
 * @description 针对表【inform(举报表)】的数据库操作Service
 * @createDate 2022-10-20 18:15:55
 */
public interface InformService extends IService<Inform> {
   Result submitInform(Inform inform);

    /**
     * 查询某组织所有举报
     * @param page 目标页码
     * @param limit 页面最大数据条数
     * @param teamId 组织Id
     * @return java.util.List<com.cohelp.server.model.vo.InformVO>
     */
    PageResponse<InformVO> listInforms(Integer page, Integer limit, Integer teamId);

    /**
     * 删除指定举报
     * @param id ID
     * @return java.lang.String
     */
    String deleteInform(Integer id);
    /**
     * 获取举报对象
     * @param idAndType id和类型
     * @return com.cohelp.server.model.domain.UserOrTopicOrRemark
     */
    UserOrTopicOrRemark getInformTarget(IdAndType idAndType);

}
