package com.example.network;

import com.example.network.bean.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WanAndroidService2 {

    @POST("user/login")
    @FormUrlEncoded
    Call<BaseResponse> login(@Field("username") String username, @Field("password") String pwd);










}
