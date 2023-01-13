package com.ear.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ear.task_for_stu.R;
import com.ear.task_for_stu.bean.User;
import com.ear.task_for_stu.biz.UserBiz;
import com.ear.task_for_stu.config.Config;
import com.ear.task_for_stu.net.CommonCallback;
import com.ear.task_for_stu.ui.CircleTransform;
import com.ear.task_for_stu.ui.activity.BaseActivity;
import com.ear.task_for_stu.utils.T;
import com.ear.task_for_stu.utils.BasicUtils;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * 注册控制类
 */
public class RegisterActivity extends BaseActivity {

    ImageView icon;
    EditText username;
    EditText phone;
    EditText password;
    EditText repassword;
    EditText nickname;
    EditText realname;
    EditText idcard;
    User user;
    UserBiz userBiz;
    Button register;
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
                user.setGrade(100);
                user.setRepeatCount(0);
                user.setTaskCount(0);
                user.setManager(false);
                user.setIdCard(idcard.getText().toString());
                user.setNickName(nickname.getText().toString());
                user.setPassword(password.getText().toString());
                user.setPhone(phone.getText().toString());
                user.setRealName(realname.getText().toString());
                user.setUserName(username.getText().toString());
                String msg;
                if("合法".equals(msg = BasicUtils.UserInfoLegal(user,repassword.getText().toString()))){
                    startLoadingProgress();
                    userBiz.register(user, new CommonCallback<User>() {
                        @Override
                        public void onError(Exception e) {
                            stopLoadingProgress();
                            T.showToast(e.getMessage());
                        }

                        @Override
                        public void onSuccess(User response) {
                            stopLoadingProgress();
                            T.showToast("欢迎你~"+user.getNickName() + "，可以登录了哦~");
                            finish();
                        }
                    });

                }else{
                    T.showToast(msg);
                }


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
        icon = findViewById(R.id.id_iv_icon);
        username = findViewById(R.id.id_et_username);
        phone = findViewById(R.id.id_et_phone);
        password = findViewById(R.id.id_et_password);
        repassword = findViewById(R.id.id_et_repassword);
        nickname = findViewById(R.id.id_et_nickname);
        realname = findViewById(R.id.id_et_realname);
        idcard = findViewById(R.id.id_et_idCard);
        register = findViewById(R.id.id_btn_register);
        userBiz = new UserBiz();
        user = new User();
        Picasso.get()
                .load(R.drawable.pictures_no)
                .transform(new CircleTransform())
                .into(icon);
    }
}