package com.cohelp.task_for_stu.net.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author zgy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest implements Serializable {
    /**
     * 搜索关键词
     */
    private String key;
    /**
     * 搜索类型
     */
    private List<Integer> types;

    private static final long serialVersionUID = 1L;
}
