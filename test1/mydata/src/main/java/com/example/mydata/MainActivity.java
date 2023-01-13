package com.example.mydata;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 名字
     * 保存的模式-常规（更新）、追加
     *
     * @Override public SharedPreferences getSharedPreferences(String name, int mode) {
     * return mBase.getSharedPreferences(name, mode);
     * }
     **/

    public void saveToSp(View view) {
        SharedPreferences sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
        sp.edit().putString("username", "hejiang").apply();  //要写apply才会写到xml配置文件中
        sp.edit().putString("password", "123456").apply();

    }

    public void getToSp(View view) {
        SharedPreferences sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String value = sp.getString("username", "未找到用户");//假设获取的username为空就会使用默认值
        Toast.makeText(this, "" + value, Toast.LENGTH_LONG).show();


    }
}