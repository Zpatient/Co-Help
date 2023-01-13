package com.example.network.retrofit;

import com.example.network.WanAndroidService;
import com.example.network.WanAndroidService2;
import com.example.network.bean.BaseResponse;
import com.google.gson.Gson;

import org.junit.Test;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WanAndroidUnitTest {
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.wanandroid.com/").build();
    WanAndroidService wanAndroidService = retrofit.create(WanAndroidService.class);

    @Test
    public void loginTest() throws IOException {
        Call<ResponseBody> call = wanAndroidService.login("hejiang", "2486hejiang");
        Response<ResponseBody> response = call.execute();
        System.out.println(response.body().string());
        //反序列化-json转换为Javabean
        BaseResponse baseResponse = new Gson().fromJson(response.body().string(), BaseResponse.class);
        System.out.println(baseResponse);
    }

    Retrofit retrofit2 = new Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create()) //添加gson转换器-自动完成json转gson
            .build();
    WanAndroidService2 wanAndroidService2 = retrofit2.create(WanAndroidService2.class);

    //字符串转化为javabean
    @Test
    public void loginTest2() throws IOException {
        Call<BaseResponse> call = wanAndroidService2.login("hejiang", "2486hejiang");
        Response<BaseResponse> response = call.execute();
        BaseResponse baseResponse = response.body();
        System.out.println(baseResponse);

    }

}
