package com.cohelp.task_for_stu.ui.activity.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.bean.Comment;
import com.cohelp.task_for_stu.bean.User;
import com.cohelp.task_for_stu.biz.UserBiz;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.ui.CircleTransform;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.utils.T;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ManagerUserDetailsActivity extends BaseActivity {
    private static final String KEY_USER = "KEY_USER";
    User user;
    UserBiz userBiz;
    LinearLayout taskCenter;
    LinearLayout questionCenter;
    LinearLayout userBoard;
    LinearLayout LpersonalInfo;
    LinearLayout LpersonalData;
    LinearLayout Lcommments;
    ScrollView SpersonalInfo;
    ScrollView SpersonalData;
    ScrollView Scommments;
    TextView personalInfo;
    TextView personalData;
    TextView commments;
    TextView nickname;
    ImageView icon;
    Button save;
    public static void launch(Context context, User user1) {
        Intent intent = new Intent(context, ManagerUserDetailsActivity.class);
        intent.putExtra(KEY_USER,user1);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_user_details);
        setUpToolBar();
        Intent intent = getIntent();
        if(intent != null){
            user = (User) intent.getSerializableExtra(KEY_USER);
            setTitle(user.getNickName() + "的详细信息");
        }else{
            T.showToast("参数传递错误");
            return;
        }
        initView();
        initEvent();
    }

    private void initEvent() {
        LpersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpersonalInfo.setVisibility(
                        SpersonalInfo.getVisibility() == View.GONE ? View.VISIBLE : View.GONE
                );
            }
        });

        LpersonalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpersonalData.setVisibility(
                        SpersonalData.getVisibility() == View.GONE ? View.VISIBLE : View.GONE
                );
            }
        });

        Lcommments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Scommments.setVisibility(
                        Scommments.getVisibility() == View.GONE ? View.VISIBLE : View.GONE
                );
            }
        });
        questionCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toManagerQuestionCenterActivity();
            }
        });

        taskCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toManagerTaskCenterActivity();
            }
        });
        userBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toManagerUserCenterActivity();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManagerCreateNewUserActivity.launch(view.getContext(), user);
                finish();
            }
        });
    }

    private void initView() {
        userBiz = new UserBiz();
        taskCenter = findViewById(R.id.id_ll_taskCenter);
        questionCenter = findViewById(R.id.id_ll_questionCenter);
        userBoard = findViewById(R.id.id_ll_userBoard);
        LpersonalInfo = findViewById(R.id.id_ll_personalInfo);
        LpersonalData = findViewById(R.id.id_ll_personalData);
        Lcommments = findViewById(R.id.id_ll_commments);
        personalInfo = findViewById(R.id.id_tv_personalInfo);
        personalData = findViewById(R.id.id_tv_personalData);
        commments = findViewById(R.id.id_tv_commments);
        nickname = findViewById(R.id.id_tv_nickname);
        icon = findViewById(R.id.id_iv_icon);
        save = findViewById(R.id.id_btn_save);
        SpersonalInfo = findViewById(R.id.id_sv_personalInfo);
        SpersonalData = findViewById(R.id.id_sv_personalData);
        Scommments = findViewById(R.id.id_sv_commments);
        startLoadingProgress();
        userBiz.getCommentByUid(user.getId(), new CommonCallback<List<Comment>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                T.showToast(e.getMessage());
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(List<Comment> response) {
                stopLoadingProgress();
                StringBuilder cs = new StringBuilder();
                T.showToast("数据获取成功！");
                personalInfo.setText(user.toString());
                personalData.setText(
                        "已回答帖子：" + user.getRepeatCount() + "\n" +
                        "已完成任务：" + user.getTaskCount() + "\n"
                );
                for (Comment comment : response) {
                    //todo 添加评论列表
                    cs.append("关于")
                            .append(Config.dateFormat.format(comment.getPostDate()))
                            .append("-标题为 \"")
                            .append(comment.getTitle())
                            .append("\" ")
                            .append("的相关评价:")
                            .append(comment.getContentDesc())
                            .append("\n\n");
                }
                commments.setText(cs.toString());
                nickname.setText(user.getNickName());
                Picasso.get()
                        .load(Config.rsUrl + user.getIcon())
                        .placeholder(R.drawable.pictures_no)
                        .transform(new CircleTransform())
                        .into(icon);
            }
        });

    }
}