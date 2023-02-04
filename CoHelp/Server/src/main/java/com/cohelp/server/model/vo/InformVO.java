package com.cohelp.server.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformVO {
    /**
     * 举报人昵称
     */
    private String informerName;
    /**
     * 举报类型
     */
    private String informType;
    /**
     * 举报内容
     */
    private String informContent;
    /**
     * 被举报对象的id
     */
    private Integer informedInstanceId;
    /**
     * 被举报对象的类型（0：用户 1：活动 2：互助 3：树洞 4：活动评论 5：互助评论 6：树洞评论）
     */
    private Integer informedInstanceType;
    /**
     * 被举报对象的类型
     */
    private String informedTypeStr;
    /**
     * 举报时间
     */
    private Date informTime;
}
