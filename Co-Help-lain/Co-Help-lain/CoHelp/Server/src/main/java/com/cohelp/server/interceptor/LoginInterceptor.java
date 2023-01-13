package com.cohelp.server.interceptor;

import com.cohelp.server.model.entity.User;
import com.cohelp.server.utils.UserHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author jianping5
 * @create 2022/10/10 21:39
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取session中的user对象
        Object userObj = request.getSession().getAttribute("user");
        // 若不存在，说明当前用户未登录，则返回444
        if (userObj == null) {
            response.setStatus(444);
            return false;
        }
        // 存在，则存入 UserHolder 中，方便获取
        User user = (User) userObj;
        UserHolder.setUser(user);
        // 放行
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 一次请求完全结束后，清空UserHolder中的数据
        UserHolder.clear();
    }
}
