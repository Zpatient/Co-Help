package com.cohelp.task_for_stu.net.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 互助标签请求体
 * @author jianping5
 * @createDate 2022/11/2 22:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelpTagRequest implements Serializable {

    public static final long serialVersionUID = 1L;

    /**
     * 标签
     */
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
