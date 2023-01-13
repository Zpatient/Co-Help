package com.cohelp.task_for_stu.net.OKHttpTools;

import com.cohelp.task_for_stu.utils.SessionUtils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OKHttp {
    OkHttpClient client;
    Request request;
    Response response;
    public Response getResponse() {
        return response;
    }


    public void sendRequest(String ip,String requestBody) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestBody);
        Request request = new Request.Builder()
                .url(ip)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendGetRequest(String ip) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(ip)
                .method("GET",null)
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendGetRequest(String ip,String session) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(ip)
                .method("GET",null)
                .addHeader("Cookie",session)
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendGetRequest(String ip,String requestBody,String session) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestBody);
        Request request = new Request.Builder()
                .url(ip)
                .method("GET",body)
                .addHeader("Cookie",session)
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendRequest(String ip,String requestBody,String session) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestBody);
        Request request = new Request.Builder()
                .url(ip)
                .method("POST", body)
                .addHeader("Cookie",session)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
