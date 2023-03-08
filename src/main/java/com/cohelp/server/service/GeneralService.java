package com.cohelp.server.service;

import com.cohelp.server.model.domain.*;
import com.cohelp.server.model.entity.Activity;
import com.cohelp.server.model.entity.Help;
import com.cohelp.server.model.entity.Hole;
import com.cohelp.server.model.vo.DetailRemark;
import com.cohelp.server.model.vo.DetailResponse;
import com.cohelp.server.model.vo.RemarkVO;

import java.util.List;

/**
 * @author zgy
 * @create 2022-10-23 15:43
 */
public interface GeneralService{
    /**
     * 根据传入的参数获取话题详情
     * @author: ZGY
     * @date: 2022-10-22 18:15
     * @param idAndType 话题详情请求参数
     * @return com.cohelp.server.model.domain.Result
     */
    Result getDetail(IdAndType idAndType);
    /**
     * 根据参数内容查询相应表的数据
     * @author: ZGY
     * @param searchRequest 查询请求参数
     * @return com.cohelp.server.model.domain.Result
     */
    Result search(SearchRequest searchRequest);
    /**
     * 插入评论
     * @author: ZGY
     * @param remarkRequest
     * @return com.cohelp.server.model.domain.Result
     */
    Result insertRemark(RemarkRequest remarkRequest);
    /**
     * 删除评论
     * @param idAndType
     * @return com.cohelp.server.model.domain.Result
     */
    Result deleteRemark(IdAndType idAndType);
    /**
     * 获取评论
     * @author: ZGY
     * @param idAndType
     * @return com.cohelp.server.model.domain.Result
     */
    Result<List<RemarkVO>> listRemark(IdAndType idAndType,Integer userId);
    /**
     * 返回组织用户 Id 数组
     * @return
     */
    List<Integer> getUserIdList();
    /**
     * 查询某组织指定类型的所有话题
     * @param page 目标页码
     * @param limit 页面最大数据条数
     * @param teamId 组织Id
     * @param type 类型
     * @return java.util.List<com.cohelp.server.model.vo.DetailResponse>
     */
    PageResponse<DetailResponse> listTopics(Integer page, Integer limit, Integer teamId, Integer type);
    /**
     * 搜索某组织某类型话题的标签、标题中包含关键字的所有话题
     * @param teamId 组织Id
     * @param key 关键字
     * @return java.util.List<com.cohelp.server.model.vo.DetailResponse>
     */
    PageResponse<DetailResponse> searchTopics(Integer page,Integer limit,Integer teamId,Integer type,String key);
    /**
     * 更改话题信息
     * @param type 话题类型
     * @param activity 待更改的活动
     * @param help 待更改的互助
     * @param hole 待更改的树洞
     * @return java.lang.String
     */
    String changeTopic(Integer type,Activity activity, Help help, Hole hole);
   /**
    * 查询某组织指定类型的所有话题的评论
    * @param page 目标页码
    * @param limit 页面最大数据条数
    * @param teamId 组织Id
    * @param type 类型
    * @return java.util.List<com.cohelp.server.model.vo.DetailResponse>
    */
    PageResponse<DetailRemark> listRemarks(Integer page, Integer limit, Integer teamId, Integer type);
    /**
     * 搜索某组织某类型话题评论的内容中包含关键字的所有评论
     * @param page 目标页码
     * @param limit 页面最大数据条数
     * @param teamId 组织Id
     * @param type 类型
     * @param key 关键字
     * @return java.util.List<com.cohelp.server.model.vo.DetailResponse>
     */
    PageResponse<DetailRemark> searchRemarks(Integer page,Integer limit,Integer teamId,Integer type,String key);
    /**
     * 删除指定评论
     * @param type 类型
     * @param id ID
     * @return java.lang.String
     */
    String deleteRemark(Integer type, Integer id);
    /**
     * 返回组织用户 Id 数组
     * @return
     */
    List<Integer> getUserIdList(Integer teamId);
    /**
     * 根据Id和Type获取评论详情
     * @param idAndType 评论话题的类型及Id
     * @return java.util.List<com.cohelp.server.model.vo.DetailRemark>
     */
    DetailRemark  getDetailRemark(IdAndType idAndType);
    /**
     * 根据Id和Type获取话详情
     * @param idAndType 话题的类型及Id
     * @return com.cohelp.server.model.vo.DetailResponse
     */
    DetailResponse getDetailResponse(IdAndType idAndType);
    /**
     * 根据Id和Type列表获取话题详情
     * @param idAndTypes Id和Type列表
     * @return java.util.List<com.cohelp.server.model.vo.DetailResponse>
     */
    List<DetailResponse> listDetailResponse(List<IdAndType> idAndTypes);
    /**
     * 获取当天指定组织的话题发布量
     * @param teamId 组织Id
     * @return com.cohelp.server.model.domain.TopicNumber
     */
    TopicNumber getCurrentDayPublish(Integer teamId);
    /**
     * 获取本年度指定组织的话题发布量
     * @param teamId 组织Id
     * @return com.cohelp.server.model.domain.TopicNumber
     */
    TopicNumber getCurrentYearPublish(Integer teamId);
}
