package com.cohelp.server.model.domain;

/**
 * @author jianping5
 * @create 2022/10/10 20:47
 */
public class StatusCode {

    public static final String SUCCESS_REGISTER = "200";
    public static final String ERROR_REGISTER = "400";

    public static final String SUCCESS_LOGIN = "201";
    public static final String ERROR_LOGIN = "401";

    /**
     * 参数错误（例如：为空）
     */
    public static final String ERROR_PARAMS = "402";

    /**
     * 登录拦截器（请求被拦截-> 返回状态码 -> 跳转登录页面）
     * 清空本地cookie
     */
    public static final String INTERCEPTOR_LOGIN = "444";


}
