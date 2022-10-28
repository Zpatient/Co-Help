package com.cohelp.task_for_stu.ui.activity.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.bean.User;
import com.cohelp.task_for_stu.biz.UserBiz;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.utils.BasicUtils;
import com.cohelp.task_for_stu.utils.T;
import com.leon.lfilepickerlibrary.utils.StringUtils;

public class ManagerCreateNewUserActivity extends BaseActivity {

    private static final String KEY_USER = "KEY_USER";
    User user;
    EditText username;
    EditText phone;
    EditText password;
    EditText repassword;
    EditText nickname;
    EditText realname;
    EditText email;
    EditText grade;
    UserBiz userBiz;
    Button create;
    RadioGroup radioGroup;
    RadioButton isManager;
    RadioButton isCommonUser;
    boolean isCreating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_create_new_user);
        setUpToolBar();
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(KEY_USER);
        if(user != null){
            setTitle(user.getNickName() + "的信息修改");
            isCreating = false;
        }else{
            user = new User();
            setTitle("创建新用户");
            isCreating = true;
        }
        initView();
        initEvent();
    }

    private void initEvent() {
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCreating){
                    int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                    if(checkedRadioButtonId != isCommonUser.getId() && checkedRadioButtonId != isManager.getId()){
                        T.showToast("需要选择角色哦^^");
                        return;
                    }
                    user.setManager(checkedRadioButtonId == isManager.getId());
                }
                if(StringUtils.isEmpty(grade.getText().toString())){
                    T.showToast("积分不能为空哦^^");
                    return;
                }
                user.setRepeatCount(0);
                user.setTaskCount(0);
                user.setGrade(Integer.parseInt(grade.getText().toString()));
                user.setEmail(email.getText().toString());
                user.setNickName(nickname.getText().toString());
                user.setPassword(password.getText().toString());
                user.setPhone(phone.getText().toString());
                user.setRealName(realname.getText().toString());
                user.setUserName(username.getText().toString());
                String s = BasicUtils.UserInfoLegal(user, repassword.getText().toString());
                if("合法".equals(s)){
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
                            T.showToast("操作成功！");
                            finish();
                        }
                    });
                }else{
                    T.showToast(s);
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initView() {

        username = findViewById(R.id.id_et_username);
        phone = findViewById(R.id.id_et_phone);
        password = findViewById(R.id.id_et_password);
        repassword = findViewById(R.id.id_et_repassword);
        nickname = findViewById(R.id.id_et_nickname);
        realname = findViewById(R.id.id_et_realname);
        email = findViewById(R.id.id_et_email);
        create = findViewById(R.id.id_btn_create);
        grade = findViewById(R.id.id_et_grade);
        radioGroup = findViewById(R.id.id_rg_sy);
        isManager = findViewById(R.id.id_rbtn_isManager);
        isCommonUser = findViewById(R.id.id_rbtn_isCommonUser);
        userBiz = new UserBiz();
        if(!isCreating){
            create.setText("保存修改");
            radioGroup.setVisibility(View.GONE);
        }
        grade.setText(user.getGrade() == null ? "" :user.getGrade()+"");
        email.setText(user.getEmail());
        nickname.setText(user.getNickName());
        password.setText(user.getPassword());
        phone.setText(user.getPhone());
        realname.setText(user.getRealName());
        username.setText(user.getUserName());
    }

    public static void launch(Context context, User user) {
        Intent intent = new Intent(context, ManagerCreateNewUserActivity.class);
        intent.putExtra(KEY_USER,user);
        context.startActivity(intent);
    }
}