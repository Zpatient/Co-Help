package com.cohelp.task_for_stu.bean;


import java.io.Serializable;
import java.util.Date;

/**
 * 评论评价实体类
 */
public class Comment implements Serializable {
  Long id;
  Long rid;
  Long did;
  Long pid;
  String contentDesc;
  Date postDate;
  String title;
  @Override
  public String toString() {
    return "Comment{" +
            "id=" + id +
            ", rid=" + rid +
            ", did=" + did +
            ", pid=" + pid +
            ", desc='" + contentDesc + '\'' +
            ", postDate=" + postDate +
            '}';
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getRid() {
    return rid;
  }

  public void setRid(Long rid) {
    this.rid = rid;
  }

  public Long getDid() {
    return did;
  }

  public void setDid(Long did) {
    this.did = did;
  }

  public Long getPid() {
    return pid;
  }

  public void setPid(Long pid) {
    this.pid = pid;
  }

  public String getContentDesc() {
    return contentDesc;
  }

  public void setContentDesc(String contentDesc) {
    this.contentDesc = contentDesc;
  }

  public Date getPostDate() {
    return postDate;
  }

  public void setPostDate(Date postDate) {
    this.postDate = postDate;
  }
}
