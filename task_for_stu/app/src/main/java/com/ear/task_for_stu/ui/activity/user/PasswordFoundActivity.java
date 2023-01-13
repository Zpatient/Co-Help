package com.ear.task_for_stu.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ear.task_for_stu.R;
import com.ear.task_for_stu.bean.User;
import com.ear.task_for_stu.biz.UserBiz;
import com.ear.task_for_stu.net.CommonCallback;
import com.ear.task_for_stu.ui.activity.BaseActivity;
import com.ear.task_for_stu.utils.BasicUtils;
import com.ear.task_for_stu.utils.T;

/**
 * 找回密码控制类
 */
public class PasswordFoundActivity extends BaseActivity {
    EditText username;
    EditText idCard;
    EditText password;
    EditText rePassword;
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
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = username.getText().toString();
                String idcard = idCard.getText().toString();
                String passWord = password.getText().toString();
                String repassword = rePassword.getText().toString();
                if(!BasicUtils.StringArrayEmpty(new String[]{userName,idcard,passWord,repassword})){
                    T.showToast("输入的值不能为空哦~");
                    return;
                }
                if(!passWord.equals(repassword)){
                    T.showToast("两次输入的密码不一致哦~");
                    return;
                }
                user.setUserName(userName);
                user.setPassword(passWord);
                user.setIdCard(idcard);
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
        idCard = findViewById(R.id.id_et_idCard);
        password = findViewById(R.id.id_et_password);
        rePassword = findViewById(R.id.id_et_repassword);
        check = findViewById(R.id.id_btn_found);
        userBiz = new UserBiz();
        user = new User();
    }

}