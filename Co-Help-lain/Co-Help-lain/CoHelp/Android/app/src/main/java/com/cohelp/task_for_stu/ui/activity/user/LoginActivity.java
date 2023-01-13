package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.cohelp.task_for_stu.biz.UserBiz;
import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.ToJsonString;
import com.cohelp.task_for_stu.net.model.domain.LoginRequest;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.cohelp.task_for_stu.utils.T;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.leon.lfilepickerlibrary.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 登陆页控制类
 */
public class LoginActivity extends BaseActivity {
    EditText password;
    EditText username;
    Button login;
    TextView toRegister;
    TextView toUserFound;
    UserBiz userBiz;
    LoginRequest loginRequest;
    User user;
    OKHttp okHttp;

//    public  Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Result<User> userResult = (Result<User>) msg.obj;
//            switch (msg.what){
//                case 1:
//                    username.setText(userResult.getData().getUserAccount());
//                    Log.e("ddddddd","Dddddddd");
//                    break;
//            }
//        }
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpToolBar();
        setTitle("登录");
//        new Thread(()-> {
//            loginRequest = new LoginRequest();loginRequest.setUserAccount(username.getText().toString());loginRequest.setUserPassword( password.getText().toString());
//            loginRequest.setUserAccount("1234567890");
//            loginRequest.setUserPassword( "1234567890");
//            String loginMessage = ToJsonString.toJson(loginRequest);
//            okHttp = new OKHttp();
//            okHttp.sendRequest("http://43.143.90.226:9090/user/login", loginMessage, getSession());
//            String res = null;
//            try {
//                System.out.println(okHttp.getResponse());
//                res = okHttp.getResponse().body().string();
//                //System.out.println(res);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println(res);
//            Gson gson = new Gson();
//            Result<User> userResult = gson.fromJson(res, new TypeToken<Result<User>>() {
//            }.getType());
//            if (userResult == null) {
//                T.showToast(userResult.getMessage());
//            } else {
//                Message message = new Message();
//                message.obj=userResult;
//                message.what=1;
//                handler.sendMessage(message);
//                String cookieval = okHttp.getResponse().header("Set-Cookie");
//                SessionUtils.saveCookiePreference(this, cookieval);
//                System.out.println(cookieval);
//                user = userResult.getData();
//               // toBasicInfoActivity();
//            }
//        }).start();
        initView();
        initEvent();

            //保存cookie
    }

    private void initEvent() {
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               toRegisterActivity();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    loginRequest = new LoginRequest();loginRequest.setUserAccount(username.getText().toString());loginRequest.setUserPassword( password.getText().toString());
                    loginRequest.setUserAccount("1234567890");//debug
                    loginRequest.setUserPassword( "1234567890");//debug
                    if(StringUtils.isEmpty(loginRequest.getUserAccount()) || StringUtils.isEmpty(loginRequest.getUserPassword())){
                        T.showToast("密码或账号不能为空哦~");
                        return;
                    }
                    else{
                        loginRequest();
                    }
            }
        });

        toUserFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toUserFoundActivity();
            }
        });
    }
    private void loginRequest(){
        new Thread(()->{
            String loginMessage = ToJsonString.toJson(loginRequest);
            okHttp = new OKHttp();
            okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage,getSession());
            String res = null;
            try {
                System.out.println(okHttp.getResponse());
                res = okHttp.getResponse().body().string();
                //System.out.println(res);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(res);
            Gson gson = new Gson();
            Result<User> userResult = gson.fromJson(res, new TypeToken<Result<User>>(){}.getType());
            if (userResult == null){
                T.showToast(userResult.getMessage());
            }
            else {
                String cookieval = okHttp.getResponse().header("Set-Cookie");
                SessionUtils.saveCookiePreference(this, cookieval);

                System.out.println(cookieval);
                user = userResult.getData();
                toBasicInfoActivity();
            }
            //保存cookie


        }).start();
    }

    private String  getSession(){
            //...
            return SessionUtils.getCookiePreference(this.getApplicationContext());
    /* // 其他请求要将sessionId设置到请求头的Cookie属性中，让服务端区分客户端
    SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
    // 获取文件中名为session对应的value（即sessionId），第二个参数是在第一个key值不存在的情况下的默认值
    String sessionId = sharedPreferences.getString("session",""); */
            //...
    }
    private void toBasicInfoActivity() {
        Intent intent = new Intent(this,BasicInfoActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
        finish();
    }

    private void toUserFoundActivity() {
        Intent intent = new Intent(this,PasswordFoundActivity.class);
        startActivity(intent);
    }

    private void toRegisterActivity() {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    private void initView() {
        password = findViewById(R.id.id_et_password);
        username = findViewById(R.id.id_et_username);
        login = findViewById(R.id.id_btn_login);
        toRegister = findViewById(R.id.id_tv_register);
        toUserFound = findViewById(R.id.id_tv_found);
        userBiz = new UserBiz();
    }
}