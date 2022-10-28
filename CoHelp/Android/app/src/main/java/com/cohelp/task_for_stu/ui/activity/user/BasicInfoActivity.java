package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.UserInfoHolder;
import com.cohelp.task_for_stu.bean.User;
import com.cohelp.task_for_stu.biz.UserBiz;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.listener.ClickListener;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.ui.CircleTransform;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.utils.T;
import com.squareup.picasso.Picasso;

/**
 * 普通用户的基本信息展示页
 */
public class BasicInfoActivity extends BaseActivity {
    ImageView icon;
    ImageView qs;
    ImageView tk;
    TextView nickname;
    TextView grade;
    TextView logOut;
    TextView history;
    LinearLayout myTask;
    LinearLayout myQuestion;
    LinearLayout questionCenter;
    LinearLayout TaskCenter;
    LinearLayout UserCenter;


    User user;
    UserBiz userBiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        initView();
        initEvent();
        setTitle("个人中心");
    }
    private void initView() {
        icon = findViewById(R.id.id_iv_icon);
        nickname = findViewById(R.id.id_tv_nickname);
        grade = findViewById(R.id.id_tv_grade);
        logOut = findViewById(R.id.id_tv_logout);
        myTask = findViewById(R.id.id_ll_myTask);
        myQuestion = findViewById(R.id.id_ll_myQuestion);
        questionCenter = findViewById(R.id.id_ll_questionCenter);
        TaskCenter = findViewById(R.id.id_ll_taskCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);
        history = findViewById(R.id.id_tv_browsing_history);
        qs = findViewById(R.id.id_qs);
        tk = findViewById(R.id.id_tk);
        userBiz = new UserBiz();
//        Picasso.get()
//                .load(R.drawable.question)
//                .placeholder(R.drawable.pictures_no)
//                .transform(new CircleTransform())
//                .into(qs);
//        Picasso.get()
//                .load(R.drawable.task)
//                .placeholder(R.drawable.pictures_no)
//                .transform(new CircleTransform())
//                .into(tk);
//        startLoadingProgress();
//        updateUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateUser();
    }

    private void updateUser() {
        startLoadingProgress();
        userBiz.userGet(UserInfoHolder.getInstance().geteUser().getId(), new CommonCallback<User>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                T.showToast(e.getMessage());
            }
            @Override
            public void onSuccess(User response) {
                stopLoadingProgress();
                UserInfoHolder.getInstance().setUser(response);
                user = response;
                Picasso.get()
                        .load(Config.rsUrl + user.getIcon())
                        .placeholder(R.drawable.pictures_no)
                        .transform(new CircleTransform())
                        .into(icon);
                nickname.setText(user.getNickName());
                grade.setText(user.getGrade() + "");
                T.showToast("用户数据更新完成！");
            }
        });
    }

    private void initEvent() {

        setToolbar(R.drawable.setting, new ClickListener() {
            @Override
            public void click() {
                toSettingUserActivity();
            }
        });

        questionCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toQuestionCenterActivity();
            }
        });

        TaskCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toTaskCenterActivity();
            }
        });

        UserCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toUserCenterActivity();
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLoginActivity();
            }
        });

        myTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMyTaskActivity();
            }
        });

        myQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMyQuestionActivity();
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toHistoryActivity();
            }
        });

    }

    private void toUserCenterActivity() {
        Intent intent = new Intent(this,BasicInfoActivity.class);
        startActivity(intent);
        finish();
    }

    private void toTaskCenterActivity() {
        Intent intent = new Intent(this,TaskCenterActivity.class);
        startActivity(intent);
        finish();
    }

    private void toQuestionCenterActivity() {
        Intent intent = new Intent(this,QuestionCenterActivity.class);
        startActivity(intent);
        finish();
    }

    private void toMyQuestionActivity() {
        Intent intent = new Intent(this,MyQuestionActivity.class);
        startActivity(intent);
    }

    private void toMyTaskActivity() {
        Intent intent = new Intent(this,MyTaskActivity.class);
        startActivity(intent);
    }

    private void toLoginActivity() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void toSettingUserActivity() {
        Intent intent = new Intent(this,SettingUserActivity.class);
        startActivityForResult(intent,1001);
    }

    private void toHistoryActivity(){
        Intent intent = new Intent(this,HistoryActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        userBiz.onDestory();
    }
}