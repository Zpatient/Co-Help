package com.cohelp.task_for_stu;

import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.ToJsonString;
import com.cohelp.task_for_stu.net.model.domain.LoginRequest;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.io.IOException;

public class UserBaseTest {
    OKHttp okHttp  = new OKHttp();
    LoginRequest loginRequest = new LoginRequest();

    @Test
    public void getUserBase(){
        loginRequest.setUserAccount("1234567890");//debug
        loginRequest.setUserPassword( "1234567890");//debug
        String loginMessage = ToJsonString.toJson(loginRequest);

        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);
        okHttp.sendGetRequest("http://43.143.90.226:9090/user/current",cookieval);

        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Result<User> userResult = gson.fromJson(res, new TypeToken<Result<User>>(){}.getType());
        System.out.println(userResult.getData());
    }

}
