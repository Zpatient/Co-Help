package com.cohelp.server.utils;

import com.cohelp.server.model.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author jianping5
 * @create 2022/10/10 21:56
 */

@Component
public class UserHolder {

    private ThreadLocal<User> userHolder = new ThreadLocal<User>();

    public void setUser(User user){
        userHolder.set(user);
    }

    public User getUser(){
        return userHolder.get();
    }

    public void clear(){
        userHolder.remove();
    }

}
