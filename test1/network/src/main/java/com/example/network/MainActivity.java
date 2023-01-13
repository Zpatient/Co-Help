package com.example.network;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    private Button mGS, mGA, mPS, mPA;
    private static String TAG = "MainActivity";
    private Retrofit retrofit;
    private HttpbinService httpbingservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置请求代理器
        OkHttpClient okHttpClient = new OkHttpClient();


        //法二-retrofit
        retrofit = new Retrofit.Builder().baseUrl("https://www.httpbin.org/").build();
        //实例化
        httpbingservice = retrofit.create(HttpbinService.class);




        //同步请求
        mGS = findViewById(R.id.getSync);
        mGS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //建立子线程
                new Thread() {
                    @Override
                    public void run() {
                        //建立请求对象，发送a=1,b=2
                        Request request = new Request.Builder().url("https://www.httpbin.org/get?a=1&b=2").build();
                        //准备好请求的call对象
                        Call call = okHttpClient.newCall(request);
                        try {
                            //得到响应--同步有可能执行到这会被阻塞
                            Response response = call.execute();
                            //得到响应体
                            Log.i(TAG, "getSync " + response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
        });

        //异步请求-不需要主动创建子线程，内部就会创建子线程了-不会阻塞之后的代码
        mGA = findViewById(R.id.getAync);
        mGA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request request = new Request.Builder().url("https://www.httpbin.org/get?a=1&b=2").build();
                //准备好请求的call对象
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        //有回应不一定代表请求成功，主要看响应码，404也是一种回应。需要判断是否成功-响应码200~300之间则成功
                        if (response.isSuccessful()) {
                            Log.i(TAG, "getAync " + response.body().string());
                        }
                    }
                });
            }
        });

        mPS = findViewById(R.id.postSync);
        mPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        //通过表单的形式传递数据
                        FormBody formBody = new FormBody.Builder().add("a", "1").add("b", "2").build();
                        Request request = new Request.Builder().url("https://www.httpbin.org/post").post(formBody).build();

                        Call call = okHttpClient.newCall(request);
                        try {
                            //得到响应--同步有可能执行到这会被阻塞
                            Response response = call.execute();
                            //得到响应体
                            Log.i(TAG, "postSync " + response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();


            }
        });

        mPA = findViewById(R.id.postAync);
        mPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //表单存储数据
//                FormBody formBody = new FormBody.Builder().add("a", "1").add("b", "2").build();
//                Request request = new Request.Builder().url("https://www.httpbin.org/post").post(formBody).build();
//
//                Call call = okHttpClient.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                        Log.i(TAG, "postAync " + response.body().string());
//                    }
//                });


                //法二-通过retrofit

                retrofit2.Call<ResponseBody> call = httpbingservice.post("hejiang", "123");
                call.enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        try {
                            Log.i(TAG, "postAync " + response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

                    }
                });


            }
        });

    }


}