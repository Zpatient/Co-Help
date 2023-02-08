package com.cohelp.server.model.domain;

import com.cohelp.server.model.entity.User;
import com.cohelp.server.model.vo.DetailRemark;
import com.cohelp.server.model.vo.DetailResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrTopicOrRemark {
    /**
     * 类型
     */
    private Integer type;
    /**
     * 用户
     */
    private User user;
    /**
     * topic
     */
    private DetailResponse detailResponse;
    /**
     * remark
     */
    private DetailRemark detailRemark;

}
