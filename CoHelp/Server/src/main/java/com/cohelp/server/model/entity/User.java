package com.cohelp.server.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 昵称
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 头像
     */
    private Integer avatar;

    /**
     * 性别（0：男 1：女）
     */
    private Integer sex;

    /**
     * 联系方式
     */
    private String phoneNumber;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 用户角色（0：普通用户 1：管理员）
     */
    private Integer userRole;

    /**
     * 状态（0：正常 1：异常）
     */
    private Integer state;

    /**
     * 用户创建时间（默认当前时间）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date userCreateTime;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 组织id
     */
    private Integer teamId;
    /**
     * 类型（0：学生 1：教师）
     */
    private Integer type;

    /**
     * 组织名
     */
    @TableField(exist = false)
    private String teamName;

    /**
     * 类型
     */
    private Integer type;

    public User(Integer id, String userAccount, String userName, String userPassword, Integer avatar, Integer sex, String phoneNumber, String userEmail, Integer userRole, Integer state, Date userCreateTime, Integer age, Integer teamId, String teamName, Integer type, String animalSign) {
        this.id = id;
        this.userAccount = userAccount;
        this.userName = userName;
        this.userPassword = userPassword;
        this.avatar = avatar;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.userEmail = userEmail;
        this.userRole = userRole;
        this.state = state;
        this.userCreateTime = userCreateTime;
        this.age = age;
        this.teamId = teamId;
        this.teamName = teamName;
        this.type = type;
        this.animalSign = animalSign;
    }

    public User(Integer id, String userAccount, String userName, String userPassword, Integer avatar, Integer sex, String phoneNumber, String userEmail, Integer userRole, Integer state, Date userCreateTime, Integer age, Integer teamId, Integer type, String teamName, String animalSign) {
        this.id = id;
        this.userAccount = userAccount;
        this.userName = userName;
        this.userPassword = userPassword;
        this.avatar = avatar;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.userEmail = userEmail;
        this.userRole = userRole;
        this.state = state;
        this.userCreateTime = userCreateTime;
        this.age = age;
        this.teamId = teamId;
        this.type = type;
        this.teamName = teamName;
        this.animalSign = animalSign;
    }

    /**
     * 生肖
     */
    @TableField(exist = false)
    private String animalSign;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public User() {

    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserAccount() == null ? other.getUserAccount() == null : this.getUserAccount().equals(other.getUserAccount()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getUserPassword() == null ? other.getUserPassword() == null : this.getUserPassword().equals(other.getUserPassword()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getPhoneNumber() == null ? other.getPhoneNumber() == null : this.getPhoneNumber().equals(other.getPhoneNumber()))
            && (this.getUserEmail() == null ? other.getUserEmail() == null : this.getUserEmail().equals(other.getUserEmail()))
            && (this.getUserRole() == null ? other.getUserRole() == null : this.getUserRole().equals(other.getUserRole()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getUserCreateTime() == null ? other.getUserCreateTime() == null : this.getUserCreateTime().equals(other.getUserCreateTime()))
            && (this.getAge() == null ? other.getAge() == null : this.getAge().equals(other.getAge()))
            && (this.getTeamId() == null ? other.getTeamId() == null : this.getTeamId().equals(other.getTeamId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserAccount() == null) ? 0 : getUserAccount().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getUserPassword() == null) ? 0 : getUserPassword().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getPhoneNumber() == null) ? 0 : getPhoneNumber().hashCode());
        result = prime * result + ((getUserEmail() == null) ? 0 : getUserEmail().hashCode());
        result = prime * result + ((getUserRole() == null) ? 0 : getUserRole().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getUserCreateTime() == null) ? 0 : getUserCreateTime().hashCode());
        result = prime * result + ((getAge() == null) ? 0 : getAge().hashCode());
        result = prime * result + ((getTeamId() == null) ? 0 : getTeamId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userAccount=").append(userAccount);
        sb.append(", userName=").append(userName);
        sb.append(", userPassword=").append(userPassword);
        sb.append(", avatar=").append(avatar);
        sb.append(", sex=").append(sex);
        sb.append(", phoneNumber=").append(phoneNumber);
        sb.append(", userEmail=").append(userEmail);
        sb.append(", userRole=").append(userRole);
        sb.append(", state=").append(state);
        sb.append(", userCreateTime=").append(userCreateTime);
        sb.append(", age=").append(age);
        sb.append(", teamId=").append(teamId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}