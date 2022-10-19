package com.cohelp.server.model.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回对象
 *
 * @author jianping5
 * @create 2022/10/10 20:24
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

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

    public Result(String message) {
        this.message = message;
    }

    public Result(T data) {
        this.data = data;
    }

}
