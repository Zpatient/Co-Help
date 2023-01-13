package com.cohelp.task_for_stu;

import android.util.Log;

import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.ToJsonString;
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
    Gson gson = g();
    Activity activity;
    LoginRequest loginRequest = new LoginRequest();
     public  Gson g(){
            //序列化
            final  JsonSerializer<LocalDateTime> jsonSerializerDateTime = (localDateTime, type, jsonSerializationContext)
                    -> new JsonPrimitive(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            final  JsonSerializer<LocalDate> jsonSerializerDate = (localDate, type, jsonSerializationContext)
                    -> new JsonPrimitive(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            //反序列化
            final  JsonDeserializer<LocalDateTime> jsonDeserializerDateTime = (jsonElement, type, jsonDeserializationContext)
                    -> LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            final  JsonDeserializer<LocalDate> jsonDeserializerDate = (jsonElement, type, jsonDeserializationContext)
                    -> LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                 return new GsonBuilder()
                        .setPrettyPrinting()
                        /* 更改先后顺序没有影响 */
                        .registerTypeAdapter(LocalDateTime.class, jsonSerializerDateTime)
                        .registerTypeAdapter(LocalDate.class, jsonSerializerDate)
                        .registerTypeAdapter(LocalDateTime.class, jsonDeserializerDateTime)
                        .registerTypeAdapter(LocalDate.class, jsonDeserializerDate)
                        .create();
    }
    @Test
    public void getBase(){
        loginRequest.setUserAccount("1234567890");//debug
        loginRequest.setUserPassword("1234567890");//debug
        String loginMessage = ToJsonString.toJson(loginRequest);

        okHttp.sendRequest("http://127.0.0.1:9090/user/login",loginMessage);
        Response response = okHttp.getResponse();
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);
        try {
            System.out.println(okHttp.getResponse().body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://127.0.0.1:9090/user/current")
                .method("GET", null)
                .addHeader("Cookie", cookieval)
                .build();
        try {
             response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        okHttp.sendGetRequest("http://127.0.0.1:9090/user/current",cookieval);
//
//        String res = null;
//        try {
//            res = okHttp.getResponse().body().string();
//            System.out.println(res);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Gson gson = new Gson();
//        Result<User> userResult = gson.fromJson(res, new TypeToken<Result<User>>(){}.getType());
//        System.out.println(userResult);
        return ;
    }
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
        okHttp.sendTextRequest("http://43.143.90.226:9090/activity/update",act,cookie);
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
