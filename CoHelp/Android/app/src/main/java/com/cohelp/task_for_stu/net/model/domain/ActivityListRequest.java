package com.cohelp.task_for_stu.net.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 活动展示请求体
 *
 * @author jianping5
 * @createDate 2022/11/2 18:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityListRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer getConditionType() {
        return conditionType;
    }

    public void setConditionType(Integer conditionType) {
        this.conditionType = conditionType;
    }

    public Integer getDayNum() {
        return dayNum;
    }

    public void setDayNum(Integer dayNum) {
        this.dayNum = dayNum;
    }

    /**
     * 条件类型（0：热度 1：时间）
     */
    private Integer conditionType;


    /**
     * 距离当天有几天
     */
    private Integer dayNum;
}
