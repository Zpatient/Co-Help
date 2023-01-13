package com.cohelp.task_for_stu.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.bean.User;
import com.cohelp.task_for_stu.biz.UserBiz;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.utils.BasicUtils;
import com.cohelp.task_for_stu.utils.T;

/**
 * 找回密码控制类
 */
public class PasswordFoundActivity extends BaseActivity {
    EditText username;
    EditText email;
    EditText password;
    EditText rePassword;
    EditText vc;
    Button checkVC;
    Button check;
    UserBiz userBiz;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_found);
        setUpToolBar();
        setTitle("找回密码");
        initView();
        initEvent();
    }

    private void initEvent() {


        checkVC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //向后端请求发送验证码
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = username.getText().toString();
                String Email = email.getText().toString();
                String passWord = password.getText().toString();
                String repassword = rePassword.getText().toString();
                String verificationCode = vc.getText().toString();
                if(!BasicUtils.StringArrayEmpty(new String[]{userName,Email,passWord,repassword})){
                    T.showToast("输入的值不能为空哦~");
                    return;
                }
                if(!passWord.equals(repassword)){
                    T.showToast("两次输入的密码不一致哦~");
                    return;
                }
                if(!verificationCode.equals("后端发送回来的验证码")){
                    T.showToast("验证码输入错误请重新输入");
                    return;
                }
                user.setUserName(userName);
                user.setPassword(passWord);
                user.setEmail(Email);
                startLoadingProgress();
                userBiz.userFound(user, new CommonCallback<String>() {
                            @Override
                            public void onError(Exception e) {
                                stopLoadingProgress();
                                T.showToast(e.getMessage());
                            }

                            @Override
                            public void onSuccess(String response) {
                                stopLoadingProgress();
                                T.showToast(response);
                                finish();
                            }
                        }
                );
            }
        });
    }

    private void initView() {
        username = findViewById(R.id.id_et_username);
        email = findViewById(R.id.id_et_email);
        password = findViewById(R.id.id_et_password);
        rePassword = findViewById(R.id.id_et_repassword);
        check = findViewById(R.id.id_btn_found);
        checkVC = findViewById(R.id.id_btn_email);
        vc = findViewById(R.id.id_et_idCard_VC);
        userBiz = new UserBiz();
        user = new User();
    }

}