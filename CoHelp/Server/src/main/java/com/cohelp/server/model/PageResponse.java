package com.cohelp.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    /**
     * 当前页数据
     */
    private List<T> result;
    /**
     * 数据库总记录数
     */
    private Long total;
}
