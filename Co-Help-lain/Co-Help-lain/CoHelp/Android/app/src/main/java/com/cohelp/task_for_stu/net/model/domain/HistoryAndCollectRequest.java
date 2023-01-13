package com.cohelp.task_for_stu.net.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zgy
 * @create 2022-10-24 23:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryAndCollectRequest {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 页码
     */
    private Integer pageNum;
    /**
     * 每页最大记录数
     */
    private Integer recordMaxNum;
    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getRecordMaxNum() {
        return recordMaxNum;
    }

    public void setRecordMaxNum(Integer recordMaxNum) {
        this.recordMaxNum = recordMaxNum;
    }
}
