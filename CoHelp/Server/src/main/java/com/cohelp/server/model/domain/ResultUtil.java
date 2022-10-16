package com.cohelp.server.model.domain;

/**
 * 返回工具类
 *
 * @author jianping5
 * @create 2022/10/12 20:58
 */
public class ResultUtil {

    public static <T> Result<T> ok(String code, T data, String message) {
        return new Result<>(code, data, message);
    }

    public static Result ok(String code, String message) {
        return new Result(code, message);
    }

    public static Result ok(String message) {
        return new Result(message);
    }

    public static <T> Result<T> fail(String code, T data, String message) {
        return new Result<>(code, data, message);
    }

    public static Result fail(String code, String message) {
        return new Result(code, message);
    }

    public static Result fail(String message) {
        return new Result(message);
    }
}