package com.cohelp.task_for_stu.net.model.domain;

import com.cohelp.task_for_stu.net.model.domain.IdAndType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zgy
 * @create 2022-11-03 15:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdAndTypeList implements Serializable {
    /**
     * 存储话题id和type的Map
     */
    List<IdAndType> idAndTypeList;

    private static final long serialVersionUID = 1L;
}
