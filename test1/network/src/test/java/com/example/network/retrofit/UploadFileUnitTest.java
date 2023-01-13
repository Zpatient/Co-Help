package com.example.network.retrofit;

import com.example.network.UploadService;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UploadFileUnitTest {
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.httpbin.org/").build();
    UploadService uploadService = retrofit.create(UploadService.class);


    @Test
    public void uploadFileTest() throws IOException {
        File file1 = new File("C:\\Users\\86176\\Desktop\\he.txt");
        MultipartBody.Part part = MultipartBody.Part.createFormData("file1","he.txt",
                RequestBody.create(file1, MediaType.parse("text/plain")));
        Call<ResponseBody> call = uploadService.upload(part);
        System.out.println(call.execute().body().string());

    }

    @Test
    public void download() throws IOException {
        Response<ResponseBody> response = uploadService.download("https://download-ssl.firefox.com.cn/releases-sha2/stub/official/zh-CN/Firefox-latest.exe")
                .execute();
//        response.isSuccessful()
        InputStream inputStream = response.body().byteStream();
        FileOutputStream fos = new FileOutputStream("C:\\Users\\86176\\Desktop\\a.exe");
        int len;
        byte[] buffer = new byte[4096];

        //读写流操作
        while ((len = inputStream.read(buffer)) != -1){
            fos.write(buffer,0,len);
        }
        fos.close();
        inputStream.close();




    }

}
