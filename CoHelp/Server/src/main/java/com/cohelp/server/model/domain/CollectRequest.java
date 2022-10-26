package com.cohelp.server.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zgy
 * @create 2022-10-24 21:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectRequest implements Serializable {
    //页码
    private Integer pageNum;
    //每页最大记录数
    private Integer recordMaxNum;
    private static final long serialVersionUID = 1L;
}
