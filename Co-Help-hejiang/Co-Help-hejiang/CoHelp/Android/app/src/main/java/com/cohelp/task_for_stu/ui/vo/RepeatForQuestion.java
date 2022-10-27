package com.cohelp.task_for_stu.ui.vo;


import com.cohelp.task_for_stu.bean.BaseTask;
import com.cohelp.task_for_stu.bean.User;

import java.io.Serializable;
import java.util.List;

public class RepeatForQuestion implements Serializable {
  User user;
  BaseTask context;
  List<Appraise> comments;

  @Override
  public String toString() {
    return "RepeatForQuestion{" +
            "user=" + user +
            ", context=" + context +
            ", comments=" + comments +
            '}';
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public BaseTask getContext() {
    return context;
  }

  public void setContext(BaseTask context) {
    this.context = context;
  }

  public List<Appraise> getComments() {
    return comments;
  }

  public void setComments(List<Appraise> comments) {
    this.comments = comments;
  }
}
