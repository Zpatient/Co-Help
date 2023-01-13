package com.cohelp.task_for_stu.ui.vo;

import com.cohelp.task_for_stu.bean.BaseTask;
import com.cohelp.task_for_stu.bean.User;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    BaseTask context;
    User user;
    List<RepeatForQuestion> repeat;

    @Override
    public String toString() {
        return "Question{" +
                "context=" + context +
                ", user=" + user +
                ", repeat=" + repeat +
                '}';
    }

    public BaseTask getContext() {
        return context;
    }

    public void setContext(BaseTask context) {
        this.context = context;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<RepeatForQuestion> getRepeat() {
        return repeat;
    }

    public void setRepeat(List<RepeatForQuestion> repeat) {
        this.repeat = repeat;
    }
}
