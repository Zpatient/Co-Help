package com.cohelp.task_for_stu.bean;

import java.io.Serializable;

public class User implements Serializable ,Comparable<User>{
    Long id;
    Integer grade;
    Integer repeatCount;
    Integer taskCount;
    String nickName;
    String userName;
    String password;
    String icon;
    String realName;
    String phone;
    String email;
    Boolean isManager;
    //账号、邮箱、邮箱验证码及按钮、输入密码、确认密码
    @Override
    public int compareTo(User user) {
        return  user.getTaskCount() - this.taskCount;
    }

    @Override
    public String toString() {
        return
                "积分:" + grade + "\n\n" +
                "采纳回复数：" + repeatCount + "\n\n" +
                "任务达成量：" + taskCount + "\n\n" +
                "昵称：" + nickName + "\n\n" +
                "账号: " + userName + "\n\n" +
                "密码：" + password + "\n\n" +
//                "真实姓名：" + realName + "\n\n" +
//                "手机号：" + phone + "\n\n" +
                "邮箱：" + email + "\n\n" +
                "平台角色：" + (isManager ? "管理员" : "普通用户") + "\n\n";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount) {
        this.repeatCount = repeatCount;
    }

    public Integer getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(Integer taskCount) {
        this.taskCount = taskCount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String idCard) {
        this.email = email;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }
}
