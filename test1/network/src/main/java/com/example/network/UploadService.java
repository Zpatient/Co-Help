package com.example.network;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface UploadService {


    @POST("post")
    @Multipart
    Call<ResponseBody> upload(@Part MultipartBody.Part file);


    @GET
    Call<ResponseBody> download(@Url String url);




}
