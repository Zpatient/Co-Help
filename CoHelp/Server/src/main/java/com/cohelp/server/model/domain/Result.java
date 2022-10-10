package com.cohelp.server.model.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jianping5
 * @create 2022/10/10 20:24
 */
@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;

    private Object data;

    private String message;

    public Result(String code, Object data, String message) {
    }

    public Result(String code, String message) {
    }

    public Result(String message) {
    }

    private static Result ok(String code, Object data, String message) {
        return new Result(code, data, message);
    }

    private static Result ok(String code, String message) {
        return new Result(code, message);
    }

    private static Result ok(String message) {
        return new Result(message);
    }

    private static Result fail(String code, Object data, String message) {
        return new Result(code, data, message);
    }

    private static Result fail(String code, String message) {
        return new Result(code, message);
    }

    private static Result fail(String message) {
        return new Result(message);
    }

}
