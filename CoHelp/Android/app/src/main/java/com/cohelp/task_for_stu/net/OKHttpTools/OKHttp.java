package com.cohelp.task_for_stu.net.OKHttpTools;

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
    public void sendRequest(String ip,String requestBody){
        client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestBody);
        request = new Request.Builder()
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

}
