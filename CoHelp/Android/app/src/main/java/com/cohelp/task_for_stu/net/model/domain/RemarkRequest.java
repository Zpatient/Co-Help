package com.cohelp.task_for_stu.net.model.domain;

import com.cohelp.task_for_stu.net.model.entity.RemarkActivity;
import com.cohelp.task_for_stu.net.model.entity.RemarkHelp;
import com.cohelp.task_for_stu.net.model.entity.RemarkHole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zgy
 * @create 2022-11-03 21:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemarkRequest implements Serializable {
    /**
     * 活动类评论
     */
    RemarkActivity remarkActivity;
    /**
     * 互助类评论
     */
    RemarkHelp remarkHelp;
    /**
     * 树洞类评论
     */
    RemarkHole remarkHole;
    /**
     * 评论的所属类型
     */
    Integer type;
}
