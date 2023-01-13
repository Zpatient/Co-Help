package com.cohelp.server.utils;

import com.cohelp.server.model.domain.Result;

import static com.cohelp.server.constant.StatusCode.ERROR_GET_DATA;

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

    public static <T> Result<T> ok(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> ok(T data, String message) {
        return new Result<>(data, message);
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

    public static <T> Result<T> fail(T data, String message) {
        return new Result<>(data, message);
    }
    /**
     * 先判断传入的数据是否为空，若为空则返回失败，否则封装传入的数据并返回成功
     * @author: ZGY
     * @date: 2022-10-22 20:09
     * @param code 状态码
     * @param data 数据
     * @param message 状态补充信息
     * @return com.cohelp.server.model.domain.Result<T>
     */
    public static <T> Result<T> returnResult(String code, T data, String message){
        if(data!=null){
           return ok(code,data,message);
        }
        else{
           return  fail(ERROR_GET_DATA,"数据获取失败！");
        }
    }

}
