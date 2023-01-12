package com.cohelp.task_for_stu.net.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 互助展示请求体
 *
 * @author jianping5
 * @createDate 2022/11/2 21:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelpListRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 条件类型（0：热度 1：时间 2：有偿 3：无偿）
     */
    private Integer conditionType;

    public Integer getConditionType() {
        return conditionType;
    }

    public void setConditionType(Integer conditionType) {
        this.conditionType = conditionType;
    }
}
