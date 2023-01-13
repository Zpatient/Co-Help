package com.example.mydata;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText et_name, et_pwd;
    private CheckBox cb_remenber, cb_auto;
    private Button bt_register, bt_login;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //获取首选项sp
        sp = getSharedPreferences("Login", Context.MODE_PRIVATE);

        initView();
        SecondLoginView();

    }

    private void SecondLoginView() {

        //第二次打开时，从sp获取数据，进行画面同步
        boolean remenber = sp.getBoolean("remenber",false);
        boolean auto = sp.getBoolean("auto",false);

        //记住密码-之前打钩上的
        if (remenber) {
            //获取sp里面的name和pwd 并保存到edittext
            String name = sp.getString("username","");
            String pwd = sp.getString("password","");
            et_name.setText(name);
            et_pwd.setText(pwd);
            cb_remenber.setChecked(true); //别忘了打钩

        }

        //自动登陆-之前打钩上的
        if (auto) {
            cb_auto.setChecked(true);
            Toast.makeText(this, "我自动登陆了", Toast.LENGTH_SHORT).show();

        }

    }

    private void initView() {
        et_name = findViewById(R.id.et_username);
        et_pwd = findViewById(R.id.et_pwd);
        cb_remenber = findViewById(R.id.cb_remenber);
        cb_auto = findViewById(R.id.cb_auto);
        bt_register = findViewById(R.id.bt_register);
        bt_login = findViewById(R.id.bt_login);


        //设置监听
        Myonclick onclick = new Myonclick();
        bt_login.setOnClickListener(onclick);
        bt_register.setOnClickListener(onclick);
    }

    private class Myonclick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_register:
                    break;
                case R.id.bt_login:
                    //登录操作
                    String name = et_name.getText().toString().trim(); //获取名字，trim省略空格
                    String pwd = et_pwd.getText().toString().trim();
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
                        Toast.makeText(LoginActivity.this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
                    }else {
                        //记住密码是否打钩
                        if(cb_remenber.isChecked()){
                            //用户名和密码都要保存且记住密码的状态也要保存
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("username",name);
                            editor.putString("password",pwd);
                            editor.putBoolean("remenber",true);
                            editor.apply();
                        }else {
                            //点击取消记住密码后也要设置
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putBoolean("remenber",false);
                            editor.apply();
                        }


                        if(cb_auto.isChecked()){
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putBoolean("auto",true);
                            editor.apply();
                        }else {
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putBoolean("auto",false);
                            editor.apply();
                        }
                    }


                    break;
            }

        }
    }
}