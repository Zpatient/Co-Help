package com.cohelp.task_for_stu.net.model.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jianping5
 * @createDate 2022/10/26 11:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishDeleteRequest {

    private int id;

    private int ownerId;

    private int typeNumber;
}
