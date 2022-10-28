package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.biz.UserBiz;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;


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

//                String userName = username.getText().toString();
//                String passWord = password.getText().toString();
//                if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord)){
//                    T.showToast("密码或账号不能为空哦~");
//                    return;
//                }
//                startLoadingProgress();
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