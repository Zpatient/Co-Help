package com.cohelp.task_for_stu.net.domain;

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
public class IdAndType implements Serializable {

    /**
     * 话题ID
     */
    private Integer id;
    /**
     * 话题类型
     */
    private Integer type;
    private static final long serialVersionUID = 1L;
}
