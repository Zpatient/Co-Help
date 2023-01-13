package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.bean.User;
import com.cohelp.task_for_stu.biz.UserBiz;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.ToJsonString;
import com.cohelp.task_for_stu.net.model.domain.RegisterRequest;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.ui.CircleTransform;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.utils.BasicUtils;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.cohelp.task_for_stu.utils.T;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

/**
 * 注册控制类
 */
public class RegisterActivity extends BaseActivity {

    ImageView icon;
    EditText username;
    EditText password;
    EditText repassword;
    EditText email;
    EditText comfirmCode;
    User user;
    UserBiz userBiz;
    Button register,getConfirmCode;
    RegisterRequest registerRequest;
    OKHttp okHttp;
    String emailString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUpToolBar();
        setTitle("注册");
        initView();
        initEvent();
    }

    private void initEvent() {
        /**
         * 准备注册
         */

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 发起来自biz的请求
//                user.setGrade(100);
//                user.setRepeatCount(0);
//                user.setTaskCount(0);
//                user.setManager(false);
//                user.setEmail(email.getText().toString());
//                user.setPassword(password.getText().toString());
//                user.setUserName(username.getText().toString());
                registerRequest.setUserAccount(username.getText().toString());
                registerRequest.setUserPassword(password.getText().toString());
                registerRequest.setUserEmail(email.getText().toString());
                registerRequest.setUserConfirmPassword(repassword.getText().toString());
                registerRequest.setConfirmCode(comfirmCode.getText().toString());

                sendRegistRequest();
                String msg;
//                if("合法".equals(msg = BasicUtils.UserInfoLegal(user,repassword.getText().toString()))){
//                    startLoadingProgress();
//                    userBiz.register(user, new CommonCallback<User>() {
//                        @Override
//                        public void onError(Exception e) {
//                            stopLoadingProgress();
//                            T.showToast(e.getMessage());
//                        }
//
//                        @Override
//                        public void onSuccess(User response) {
//                            stopLoadingProgress();
//                            T.showToast("欢迎你~"+user.getNickName() + "，可以登录了哦~");
//                            finish();
//                        }
//                    });
//
//                }else{
//                    T.showToast(msg);
//                }


            }
        });
        getConfirmCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                System.out.println(emailString);
                System.out.println(1);
                emailString = email.getText().toString();
                if (emailString!=null){
                    System.out.println(2);
                    sendComfirmCodeRequest();

                    System.out.println(4);
                }

                else T.showToast("请输入合法的邮箱！");
            }

        });
        /**
         * 选择图片
         */
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPickFile();
            }
        });
    }
    private void sendComfirmCodeRequest(){
        new Thread(()->{
             emailString= registerRequest.getUserEmail();

            System.out.println(3);
            okHttp.sendGetRequest("http://43.143.90.226:9090/user/sendconfirmcode?userEmail="+emailString);
            String cookieval = okHttp.getResponse().header("Set-Cookie");
            SessionUtils.saveCookiePreference(this, cookieval);
            System.out.println(cookieval);
            System.out.println(5);
            System.out.println(okHttp.getResponse());
        }).start();
    }
    private void sendRegistRequest(){
        new Thread(()->{
            String cookie = SessionUtils.getCookiePreference(this);
            String registMessage = ToJsonString.toJson(registerRequest);

            okHttp.sendRequest("http://43.143.90.226:9090/user/register",registMessage,cookie);
            String res = null;
            try {
                System.out.println(okHttp.getResponse());
                res = okHttp.getResponse().body().string();
                //System.out.println(res);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(res).getAsJsonObject();
            String message = jsonObject.get("message").getAsString();

            System.out.println(message);

        }).start();
    }
    private void startPickFile() {
        new LFilePicker()
                .withActivity(this)
                .withRequestCode(1001)
                .withMutilyMode(false)
                .withTitle("上传个人头像")//标题文字
                .withFileFilter(new String[]{".jpg", ".png",".jpeg"})//支持的上传的文件类型
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1001) {
                String fileName = data.getStringArrayListExtra(Constant.RESULT_INFO).get(0);
                File file = new File(fileName);
                if(file.exists()){
                    startLoadingProgress();
                    userBiz.uploadImg(file, new CommonCallback<String>() {
                        @Override
                        public void onError(Exception e) {
                            stopLoadingProgress();
                            T.showToast(e.getMessage());
                        }

                        @Override
                        public void onSuccess(String response) {
                            stopLoadingProgress();
                            user.setIcon(response);
                            Picasso.get()
                                    .load(Config.rsUrl + response)
                                    .placeholder(R.drawable.pictures_no)
                                    .transform(new CircleTransform())
                                    .into(icon);
                        }
                    });
                }else{
                    T.showToast("不能找到对应文件");
                }
            }
        }else if(resultCode == RESULT_CANCELED){
            T.showToast("返回登录页面~");
        }
    }

    private void initView() {
        okHttp = new OKHttp();
        icon = findViewById(R.id.id_iv_icon);
        username = findViewById(R.id.id_et_username);
        password = findViewById(R.id.id_et_password);
        repassword = findViewById(R.id.id_et_repassword);
        email = findViewById(R.id.id_et_email);
        register = findViewById(R.id.id_btn_register);
        comfirmCode = findViewById(R.id.id_et_idCard_Confirm);
        userBiz = new UserBiz();
        user = new User();
        getConfirmCode = findViewById(R.id.id_btn_sendComfirmCode);
//        registerRequest = new RegisterRequest();
//        registerRequest.setUserAccount("12345678990");
//        registerRequest.setUserEmail("2836969767@qq.com");
//        registerRequest.setUserPassword("12345678");
//        registerRequest.setUserConfirmPassword("12345678");

        Picasso.get()
                .load(R.drawable.pictures_no)
                .transform(new CircleTransform())
                .into(icon);
    }
}