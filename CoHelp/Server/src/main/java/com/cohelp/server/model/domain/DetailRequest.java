package com.cohelp.server.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zgy
 * @create 2022-10-26 15:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailRequest implements Serializable {
    //动态类型
    private Integer type;
    //动态ID
    private String id;
    private static final long serialVersionUID = 1L;
}
