package com.ear.task_for_stu.ui.activity.user;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ear.task_for_stu.R;
import com.ear.task_for_stu.bean.User;
import com.ear.task_for_stu.biz.UserBiz;
import com.ear.task_for_stu.net.CommonCallback;
import com.ear.task_for_stu.ui.activity.BaseActivity;
import com.ear.task_for_stu.ui.adpter.LeadAdapter;
import com.ear.task_for_stu.utils.T;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeadActivity extends BaseActivity {
    LinearLayout questionCenter;
    LinearLayout TaskCenter;
    LinearLayout UserCenter;
    LinearLayout lead;
    RecyclerView eRecyclerView;
    LeadAdapter leadAdapter;
    List<User> userList;
    UserBiz userBiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        setUpToolBar();
        setTitle("排行榜");
        initView();
        initEvent();
    }

    private void initEvent() {
        startLoadingProgress();
        userBiz.getAll(new CommonCallback<List<User>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                T.showToast(e.getMessage());
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(List<User> response) {
                stopLoadingProgress();
                for(User user : response){
                    user.setTaskCount(user.getTaskCount()+user.getRepeatCount());
                }
                Collections.sort(response);
                userList.clear();
                userList.addAll(response);
                leadAdapter.notifyDataSetChanged();
                T.showToast("排行榜加载成功!");
            }
        });
        lead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLeadActivity();
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
    }

    private void initView() {
        questionCenter = findViewById(R.id.id_ll_questionCenter);
        TaskCenter = findViewById(R.id.id_ll_taskCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);
        lead = findViewById(R.id.id_ll_leaderBoard);
        eRecyclerView = findViewById(R.id.id_pane);
        userBiz = new UserBiz();
        userList = new ArrayList<>();

        leadAdapter = new LeadAdapter(this,userList);

        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eRecyclerView.setAdapter(leadAdapter);
    }
    private void toLeadActivity() {
        Intent intent = new Intent(this,LeadActivity.class);
        startActivity(intent);
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
}