package com.cohelp.task_for_stu.net.model.domain;



import com.cohelp.task_for_stu.utils.T;

import java.io.Serializable;

import lombok.Data;

/**
 * 返回对象
 *
 * @author jianping5
 * @create 2022/10/10 20:24
 */

public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public Result() {
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private T data;

    private String message;

    public Result(String code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public Result(String message) {
        this.message = message;
    }

    public Result(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
