package com.example.network;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadFileUnitTest {

    //上传多个文件-不需要关心文件的格式


    @org.junit.Test

    public void uploadFileTest() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();


        String printTxtPath = getApplicationContext().getFilesDir().getAbsolutePath();

        File file1 = new File("C:\\Users\\86176\\Desktop\\he.txt");
        File file2 = new File("C:\\Users\\86176\\Desktop\\2.txt");
//        File externalCacheFile = new File(context.getExternalCacheDir(), filename);

        //键值对，加提交的文本的形式
        MultipartBody multipartBody = new MultipartBody.Builder()
                .addFormDataPart("file1", file1.getName(), RequestBody.create(file1, MediaType.parse("text/plain ")))
                .addFormDataPart("file2", file2.getName(), RequestBody.create(file2, MediaType.parse("text/plain ")))
                .build();
        //提交到服务器
        Request request = new Request.Builder().url("https://www.httpbin.org/post").post(multipartBody).build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        System.out.println(response.body().string());

    }
}
