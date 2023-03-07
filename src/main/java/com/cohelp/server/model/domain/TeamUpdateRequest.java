package com.cohelp.server.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jianping5
 * @createDate 22/1/2023 下午 6:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 更改类型（0：加入，1：退出）
     */
    private Integer conditionType;

    /**
     * 组织 id
     */
    private Integer teamId;


}
