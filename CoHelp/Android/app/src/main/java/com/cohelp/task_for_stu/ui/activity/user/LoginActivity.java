package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
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
import com.cohelp.task_for_stu.utils.T;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpToolBar();
        setTitle("登录");
        initView();
        initEvent();
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
                  loginRequest = new LoginRequest();
                  loginRequest.setUserAccount(username.getText().toString());
                  loginRequest.setUserPassword( password.getText().toString());
                loginRequest.setUserAccount("1234567890");
                loginRequest.setUserPassword( "1234567890");
                if(StringUtils.isEmpty(loginRequest.getUserAccount()) || StringUtils.isEmpty(loginRequest.getUserPassword())){
                    T.showToast("密码或账号不能为空哦~");
                    return;
                }
                else{
                    new Thread(()->{
//                        OkHttpClient client = new OkHttpClient();
//                        MediaType mediaType = MediaType.parse("application/json");
//                        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"userAccount\":\"1234567890\",\r\n    \"userPassword\":\"1234567990\"\r\n}");
//                        Request request = new Request.Builder()
//                                .url("http://43.143.90.226:9090/user/login")
//                                .method("POST", body)
//                                .addHeader("Content-Type", "application/json")
//                                //.addHeader("Cookie", "JSESSIONID=F5897AFD64247CDF2941737F626E9075")
//                                .build();
//                        try {
//                            //System.out.println(1);
//                            Response response = client.newCall(request).execute();
//                            //System.out.println(2);
//                            System.out.println(response.header("Set-Cookie"));
//                            System.out.println(response.body().string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                        String loginMessage = ToJsonString.toJson(loginRequest);
                        OKHttp okHttp = new OKHttp();
                        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
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
                        try {
                            JSONObject jsonObject = new JSONObject(res);
                            String data = jsonObject.getString("data");
                            User user = gson.fromJson(data, User.class);
                            System.out.println(user.getUserAccount());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        JsonObject jsonObject = new JsonObject(res);
//                        User data = (User) map.get("data");
                    }).start();

                }

                //startLoadingProgress();
//                userBiz.login(userName, passWord, new CommonCallback<User>() {
//                    @Override
//                    public void onError(Exception e) {
//                        stopLoadingProgress();
//                        T.showToast(e.getMessage());
//                    }
//
//                    @Override
//                    public void onSuccess(User response) {
//                        stopLoadingProgress();
//                        T.showToast("登录成功~" + response.isManager());
//                        UserInfoHolder.getInstance().setUser(response);
//                        if(response.isManager()){
//                            toManagerUserCenterActivity();
//                        }else
                        toBasicInfoActivity();
//                    }
//                });

            }
        });

        toUserFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toUserFoundActivity();
            }
        });
    }

    private void toBasicInfoActivity() {
        Intent intent = new Intent(this,BasicInfoActivity.class);
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