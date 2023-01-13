package com.example.rxjava;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    private final static String PAth = "https://tppic.chinaz.net/files/default/imgs/2022-08-21/d6b5fd23fa4b86b4_1920x1080.jpg";
    private ImageView image;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = findViewById(R.id.image);

    }

    public void showImageonClick(View view) {


        //TODO 第二步
        //起点--把所有的函数看成操作符  下载图片要将String类型转换成Bitmap格式然后通过流来传递
        Observable.just(PAth)


                //TODO 第三步
                //需求 001  下载图片
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String s) throws Exception {
                        try {
                            Thread.sleep(2000);//睡眠2s

                            URL url = new URL(PAth);
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            httpURLConnection.setConnectTimeout(5000); //设置请求连接时长
                            int responseCode = httpURLConnection.getResponseCode();//拿到服务器的响应 200成功  404失败
                            if (responseCode == httpURLConnection.HTTP_OK) {

                                //拿到流
                                InputStream inputStream = httpURLConnection.getInputStream();
                                //重新编译成bitmap形式
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                return bitmap;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                })




                // TODO 第3.1步 加水印

                .map(new Function<Bitmap, Bitmap>() {
                    @Override
                    public Bitmap apply(Bitmap bitmap) throws Exception {
                        Paint paint = new Paint();
                        paint.setColor(Color.RED);
                        paint.setTextSize(88);
                        Bitmap bitmap1 = drawTextBitmap(bitmap, "新加的水印", paint, 88, 80);
                        return bitmap1;
                    }
                })


                //TODO 第3.2步 打印日志
                .map(new Function<Bitmap, Bitmap>() {
                    @Override
                    public Bitmap apply(Bitmap bitmap) throws Exception {

                        Log.e("打印日志", "什么时候下载好图片" + System.currentTimeMillis());
                        return bitmap;
                    }
                })



                //给上面的分配异步线程-下载图片操作
                .subscribeOn(Schedulers.io())
                //给终点安卓ui设置主线程，不能在异步线程中显示
                .observeOn(AndroidSchedulers.mainThread())


                // TODO 相当于导火索
                //关联：观察者设计模式  关联起点和终点==订阅
                .subscribe(

                        //终点
                        new Observer<Bitmap>() {
                            // TODO 第一步
                            //关联成功
                            @Override
                            public void onSubscribe(Disposable d) {
                                //加载框
                                progressDialog = new ProgressDialog(MainActivity.this);
                                progressDialog.setTitle("正在加载中...");
                                progressDialog.show();

                            }

                            //TODO 第四步  显示图片   加水印
                            //上层给的响应
                            @Override
                            public void onNext(Bitmap bitmap) {
                                image.setImageBitmap(bitmap);
                            }

                            //链条发生异常
                            @Override
                            public void onError(Throwable e) {

                            }

                            //TODO 第五步--整个链条思维结束
                            //链条发生全部结束
                            @Override
                            public void onComplete() {
                                if (progressDialog != null)
                                    progressDialog.dismiss();
                            }
                        });


    }

    //图片上绘制文字
    private final Bitmap drawTextBitmap(Bitmap bitmap, String text, Paint paint, int paddingLeft, int paddingTop) {

        Bitmap.Config bitmapConfig = bitmap.getConfig();
        paint.setDither(true); //获取更清晰的图片采用
        paint.setFilterBitmap(true); //过滤一些

        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, paddingLeft, paddingTop, paint);
        return bitmap;

    }

    //常用操作符-fromArray

    public void showArrayonClick(View view) {
        String[] strings = {"aaa", "bbb", "ccc"};

        //法一：通过for循环
//        for (String string : strings) {
//
//        }
        //法二：Rx
        Observable.fromArray(strings)
                //订阅起点和终点
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("遍历字符串", s);
                    }
                });

    }
}