package com.cohelp.task_for_stu;

import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.model.domain.ActivityListRequest;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.entity.Activity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.time.LocalDateTime;

public class ActivityTest {
    OKHttp okHttp  = new OKHttp();
    //    LoginRequest loginRequest = new LoginRequest();
    Gson gson = new Gson();
    Activity activity;
    @Test
    public void activityPublish(){
        activity = new Activity(78,1,"nice","wow", LocalDateTime.now(),0,0,"",0,0,LocalDateTime.now());
        String act = gson.toJson(activity);
        okHttp.sendRequest("http://43.143.90.226:9090/activity/publish",act);
        String res = okHttp.getResponse().toString();
        System.out.println(res);
    }
    @Test
    public void activityUpdate(){
        activity = new Activity(4,1,"nice","wow", LocalDateTime.now(),0,0,"",0,0,LocalDateTime.now());
        String act = gson.toJson(activity);
        okHttp.sendRequest("http://43.143.90.226:9090/activity/update",act);
        String res = okHttp.getResponse().toString();
        System.out.println(res);

    }
    @Test
    public void activityList(){
        ActivityListRequest activityListRequest = new ActivityListRequest();
        activityListRequest.setConditionType(1);
        activityListRequest.setDayNum(100000);
        String req = gson.toJson(activityListRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/activity/list",req);
        String res = okHttp.getResponse().toString();
        System.out.println(res);
//        Result<DetailResponse> result = gson.fromJson(res, new TypeToken<Result<DetailResponse>>(){}.getType());
//        System.out.println(result.getData().getActivityVO());
    }


}
