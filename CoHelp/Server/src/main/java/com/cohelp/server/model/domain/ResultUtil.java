package com.cohelp.server.model.domain;

/**
 * 返回工具类
 *
 * @author jianping5
 * @create 2022/10/12 20:58
 */
public class ResultUtil {

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
