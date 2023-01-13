package com.example.network;

import okhttp3.FormBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface HttpbinService {

    //https://www.httpbin.org/post/username=userName
    @POST("post")
    //传递方式-通过表单的形式
    @FormUrlEncoded
    //方法名post无所谓，看的是注解里面写的
    Call<ResponseBody> post(@Field(("username")) String userName, @Field(("password")) String pwd);  //自动的通过Field创建请求体

    @GET("get")
    Call<ResponseBody> get(@Query("username") String userName, @Query(("password")) String pwd);

    //同上get请求一样
    @HTTP(method = "Get", path = "get")
    Call<ResponseBody> http(@Query("username") String userName, @Query(("password")) String pwd);

    @POST("post")
    Call<ResponseBody> postBody(@Body FormBody body);


    @POST("{id}")
    @FormUrlEncoded
    Call<ResponseBody> postInPath(@Path("id") String path, @Header("os")String os ,@Field(("username")) String userName, @Field(("password")) String pwd);


    @Headers({"os:android","version:1.1"})
    @POST("post")
    Call<ResponseBody> postWithHeads();


    //里面不用写post了，因为url已经传入了一个完整的域名了
    @POST
    Call<ResponseBody> postUrl(@Url String url);
}
