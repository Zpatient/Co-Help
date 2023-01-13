package com.cohelp.task_for_stu;

import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.ToJsonString;
import com.cohelp.task_for_stu.net.model.domain.HistoryAndCollectRequest;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.net.model.domain.LoginRequest;
import com.cohelp.task_for_stu.net.model.domain.RemarkRequest;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.domain.SearchRequest;
import com.cohelp.task_for_stu.net.model.entity.History;
import com.cohelp.task_for_stu.net.model.entity.RemarkActivity;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.io.IOException;

public class PublishTest {
    OKHttp okHttp  = new OKHttp();
    LoginRequest loginRequest = new LoginRequest();
    IdAndType idAndType = new IdAndType();
    SearchRequest searchRequest = new SearchRequest();
    RemarkRequest remarkRequest = new RemarkRequest();
    RemarkActivity remarkActivity = new RemarkActivity();
    HistoryAndCollectRequest historyAndCollectRequest = new HistoryAndCollectRequest();
    History history = new History();

    @Test
    public void searchPublish(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);
        okHttp.sendGetRequest("http://43.143.90.226:9090/user/searchpub/1234567890",cookieval.split(";")[0]);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Result<User> userResult = gson.fromJson(res,new TypeToken<Result<User>>(){}.getType());
        System.out.println("data:"+userResult);
    }
}
