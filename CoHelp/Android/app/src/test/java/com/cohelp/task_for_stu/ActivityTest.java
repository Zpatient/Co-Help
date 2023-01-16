package com.cohelp.task_for_stu;

import android.util.Log;

import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.ToJsonString;
import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.ActivityListRequest;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.LoginRequest;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.entity.Activity;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class ActivityTest {
    OKHttp okHttp  = new OKHttp();
    //    LoginRequest loginRequest = new LoginRequest();
    Gson gson = new GSON().gsonSetter();
    Activity activity;
    LoginRequest loginRequest = new LoginRequest();


    @Test
    public void activityPublish(){
//        UserBaseTest userBaseTest = new UserBaseTest();
//        String cookie = userBaseTest.getUserBase();
//        System.out.println(cookie);
        loginRequest.setUserAccount("1234567890");//debug
        loginRequest.setUserPassword( "1234567890");//debug
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        String localDateTime = df.format(time);
        activity = new Activity(null,null,"nice","wow", LocalDateTime.now(),0,0,"",0,0,null);
//        String act = gson.toJson(activity);
        String act = gson.toJson(activity);
        Activity a2 = gson.fromJson(act,Activity.class);
        System.out.println("a2="+a2.getActivityTime().toString().replace('T',' '));
        System.out.println(a2);
//        String act = "{\"activityTitle\":\"nice\",\"activityDetail\":\"wow\",\"activityTime\":\""+localDateTime+"\",\"activityLike\":0,\"activityComment\":0,\"activityLabel\":\"0\",\"activityCollect\":0,\"activityState\":0}";
        System.out.println(act);
//        okHttp.sendTextRequest("http://43.143.90.226:9090/activity/publish",act,cookie);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("activity",act)
            .addFormDataPart("file","签名.PNG",
                             RequestBody.create(MediaType.parse("application/octet-stream"),
    new File("/Users/lain/Pictures/lyh/1_inch_lyh.png")))
            .build();

    Request request = new Request.Builder()
            .url("http://43.143.90.226:9090/activity/publish")
            .method("POST", body)
            //.addHeader("Cookie", "JSESSIONID=5CBC3D9D8425219E4F0293EF2C0EBA45")
            .addHeader("Cookie",cookieval)
            .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(1);
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println(okHttp.getResponse());
    }
    @Test
    public void activityUpdate(){
        UserBaseTest userBaseTest = new UserBaseTest();
        String cookie = userBaseTest.getUserBase();
        System.out.println(cookie);
        activity = new Activity(4,1,"nice","wow", LocalDateTime.now(),0,0,"",0,0,LocalDateTime.now());
        String act = gson.toJson(activity);
        okHttp.sendMediaRequest("http://43.143.90.226:9090/activity/update","activity",act,null,cookie);
        String res = okHttp.getResponse().toString();
        System.out.println(res);

    }
    @Test
    public void activityList(){
        String cookie = new UserBaseTest().getUserBase();
        ActivityListRequest activityListRequest = new ActivityListRequest();
        activityListRequest.setConditionType(1);
        activityListRequest.setDayNum(10000);
        String req = gson.toJson(activityListRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/activity/list",req,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res);
//        Result<DetailResponse> result = gson.fromJson(res, new TypeToken<Result<DetailResponse>>(){}.getType());
//        System.out.println(result.getData().getActivityVO());
    }


}
