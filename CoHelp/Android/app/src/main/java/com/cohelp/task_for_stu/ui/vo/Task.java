package com.cohelp.task_for_stu.ui.vo;

import com.cohelp.task_for_stu.bean.BaseTask;
import com.cohelp.task_for_stu.bean.Comment;
import com.cohelp.task_for_stu.bean.User;

import java.io.Serializable;

public class Task implements Serializable {
    BaseTask context;
    User doneUser;
    User postedUser;
    Comment comment;
    @Override
    public String toString() {
        return "Task{" +
                "context=" + context +
                ", doneUser=" + doneUser +
                ", postedUser=" + postedUser +
                '}';
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public BaseTask getContext() {
        return context;
    }

    public void setContext(BaseTask context) {
        this.context = context;
    }

    public User getDoneUser() {
        return doneUser;
    }

    public void setDoneUser(User doneUser) {
        this.doneUser = doneUser;
    }

    public User getPostedUser() {
        return postedUser;
    }

    public void setPostedUser(User postedUser) {
        this.postedUser = postedUser;
    }
}
