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

    public RemarkActivity getRemarkActivity() {
        return remarkActivity;
    }

    public void setRemarkActivity(RemarkActivity remarkActivity) {
        this.remarkActivity = remarkActivity;
    }

    public RemarkHelp getRemarkHelp() {
        return remarkHelp;
    }

    public void setRemarkHelp(RemarkHelp remarkHelp) {
        this.remarkHelp = remarkHelp;
    }

    public RemarkHole getRemarkHole() {
        return remarkHole;
    }

    public void setRemarkHole(RemarkHole remarkHole) {
        this.remarkHole = remarkHole;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
