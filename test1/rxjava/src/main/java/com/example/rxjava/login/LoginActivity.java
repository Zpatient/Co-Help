package com.example.rxjava.login;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rxjava.R;
import com.example.rxjava.login.bean.SuccessfulBean;
import com.example.rxjava.login.core.CustmoObserver;
import com.example.rxjava.login.core.LoginEngine;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //起点 总bean -在LoginEngine类里面返回了起点
        LoginEngine.login("hejiang","123456")



                //终点-注意是CustmoObserver这是自己定义的抽象类-需要去实现抽象类里面的抽象方法
                .subscribe(new CustmoObserver() {
                    @Override
                    public void success(SuccessfulBean successfulBean) {
                        Log.d("登陆成功","登陆成功的bean  " + successfulBean.toString());
                    }

                    @Override
                    public void error(String message) {
                        Log.d("登陆失败","登陆失败的Message  " + message);
                    }
                });
    }
}