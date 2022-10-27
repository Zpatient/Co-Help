package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.UserInfoHolder;
import com.cohelp.task_for_stu.bean.User;
import com.cohelp.task_for_stu.biz.UserBiz;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.ui.CircleTransform;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.utils.BasicUtils;
import com.cohelp.task_for_stu.utils.T;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;
import com.squareup.picasso.Picasso;

import java.io.File;

public class SettingUserActivity extends BaseActivity {
    ImageView icon;
    EditText username;
    EditText phone;
    EditText password;
    EditText repassword;
    EditText nickname;
    EditText realname;
    EditText email;
    Button update;
    User user;
    UserBiz userBiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_user);
        setUpToolBar();
        setTitle("修改个人信息");
        initView();
        initEvent();
    }
    private void initEvent() {
        /**
         * 准备注册
         */
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 发起来自biz的请求
                user.setEmail(email.getText().toString());
                user.setNickName(nickname.getText().toString());
                user.setPassword(password.getText().toString());
                user.setPhone(phone.getText().toString());
                user.setRealName(realname.getText().toString());
                user.setUserName(username.getText().toString());
                String msg;
                if("合法".equals(msg = BasicUtils.UserInfoLegal(user,repassword.getText().toString()))){
                    startLoadingProgress();
                    userBiz.updateUser(user, new CommonCallback<User>() {
                        @Override
                        public void onError(Exception e) {
                            stopLoadingProgress();
                            T.showToast(e.getMessage());
                        }

                        @Override
                        public void onSuccess(User response) {
                            stopLoadingProgress();
                            UserInfoHolder.getInstance().setUser(response);
                            T.showToast("修改成功！");
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
                .withTitle("我的标题")//标题文字
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
            T.showToast("返回个人信息页面~");
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
        email = findViewById(R.id.id_et_email);
        update = findViewById(R.id.id_btn_update);
        userBiz = new UserBiz();
        user = UserInfoHolder.getInstance().geteUser();
        Picasso.get()
                .load(Config.rsUrl + user.getIcon())
                .placeholder(R.drawable.pictures_no)
                .transform(new CircleTransform())
                .into(icon);
        email.setText(user.getEmail());
        nickname.setText(user.getNickName());
        phone.setText(user.getPhone());
        realname.setText(user.getRealName());
        username.setText(user.getUserName());
    }
}