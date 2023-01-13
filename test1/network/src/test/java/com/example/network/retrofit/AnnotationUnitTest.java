package com.example.network.retrofit;

import com.example.network.HttpbinService;

import org.junit.Test;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AnnotationUnitTest {
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.httpbin.org").build();
    HttpbinService httpbinService = retrofit.create(HttpbinService.class);

    @Test
    public void bodyTest() throws IOException {
        FormBody formBody = new FormBody.Builder().add("a", "1").add("b", "2").build();
        Response<ResponseBody> response = httpbinService.postBody(formBody).execute();
        System.out.println(response.body().string());
    }


    @Test
    public void pathTest() throws IOException {
        //实际请求的是https://www.httpbin.org/post
        Response<ResponseBody> response = httpbinService.postInPath("post","Android","hj","123").execute();
        System.out.println(response.body().string());
    }

    @Test
    public void headsTest() throws IOException {
        Call<ResponseBody> responseBodyCall = httpbinService.postWithHeads();
        System.out.println(responseBodyCall.execute().body().string());

    }

    @Test
    public void urlTest() throws IOException {
        Response<ResponseBody> response = httpbinService.postUrl("https://www.httpbin.org/post").execute();
        System.out.println(response.body().string());

    }

}
