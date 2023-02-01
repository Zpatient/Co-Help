package com.cohelp.server.service;

import com.cohelp.server.model.domain.*;

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
     * @author: ZGY
     * @param remarkRequest
     * @return com.cohelp.server.model.domain.Result
     */
    Result deleteRemark(IdAndType idAndType);
    /**
     * 获取评论
     * @author: ZGY
     * @param remarkRequest
     * @return com.cohelp.server.model.domain.Result
     */
    Result listRemark(IdAndType idAndType);
    /**
     * 返回组织用户 Id 数组
     * @return
     */
    List<Integer> getUserIdList();
    /**
     * 返回组织用户 Id 数组
     * @return
     */
    List<Integer> getUserIdList(Integer teamId);
    /**
     * 根据Id和Type列表获取话题详情
     * @param idAndTypes Id和Type列表
     * @return java.util.List<com.cohelp.server.model.domain.DetailResponse>
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
