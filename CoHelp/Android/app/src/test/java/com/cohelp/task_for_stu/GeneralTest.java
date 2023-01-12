package com.cohelp.task_for_stu;

import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.net.model.domain.LoginRequest;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.entity.Activity;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.io.IOException;

public class GeneralTest {
    OKHttp okHttp  = new OKHttp();
//    LoginRequest loginRequest = new LoginRequest();
    Gson gson = new Gson();

    @Test
    public void testGetDetail(){
        IdAndType idAndType = new IdAndType(1,1);
        String checkMessage = gson.toJson(idAndType);
        System.out.println(checkMessage);
        okHttp.sendRequest("http://43.143.90.226:9090/general/getdetail",checkMessage);
        try {
            String res = okHttp.getResponse().body().string();

            System.out.println(res);
            Result<DetailResponse> result = gson.fromJson(res, new TypeToken<Result<DetailResponse>>(){}.getType());
            System.out.println(result.getData().getActivityVO());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
