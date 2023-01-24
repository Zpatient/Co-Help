package com.cohelp.task_for_stu.ui.activity.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.UserInfoHolder;
import com.cohelp.task_for_stu.biz.TaskBiz;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.TaskAdapter;
import com.cohelp.task_for_stu.ui.vo.Task;
import com.cohelp.task_for_stu.utils.T;

import java.util.ArrayList;
import java.util.List;

public class MyTaskActivity extends BaseActivity {
    LinearLayout HoleCenter;
    LinearLayout HelpCenter;
    LinearLayout TaskCenter;
    LinearLayout UserCenter;
    TextView all;
    TextView taskSolved;
    TextView taskPosted;
    RecyclerView eRecyclerView;

    TaskAdapter taskAdapter;
    List<Task> taskList;
    TaskBiz taskBiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        setUpToolBar();
        setTitle("我的任务");
        initView();
        initEvent();
        loadAll();
    }

    private void initEvent() {

        HelpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toHelpCenterActivity();
            }
        });
        HoleCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {toHoleCenterActivity();}
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

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAll();
            }
        });
        //todo 展示接受过和发布过的任务，需要重新写业务方法
        taskSolved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoadingProgress();
                taskBiz.getAllMyTaskSolved(UserInfoHolder.getInstance().geteUser().getId(),new CommonCallback<List<Task>>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        T.showToast(e.getMessage());
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(List<Task> response) {
                        stopLoadingProgress();
                        T.showToast("查询任务数据成功！");
                        updateList(response);
                    }
                });
            }
        });

        taskPosted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoadingProgress();
                taskBiz.getAllMyTaskPosted(UserInfoHolder.getInstance().geteUser().getId(),new CommonCallback<List<Task>>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        T.showToast(e.getMessage());
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(List<Task> response) {
                        stopLoadingProgress();
                        T.showToast("查询任务数据成功！");
                        updateList(response);
                    }
                });
            }
        });
    }

    private void loadAll() {
        //TODO 查询所有问题
        startLoadingProgress();
        taskBiz.getAllMyTask(UserInfoHolder.getInstance().geteUser().getId(),new CommonCallback<List<Task>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                T.showToast(e.getMessage());
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(List<Task> response) {
                stopLoadingProgress();
                T.showToast("更新任务数据成功！");
                updateList(response);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateList(List<Task> response) {
        taskList.clear();
        taskList.addAll(response);
        taskAdapter.notifyDataSetChanged();
    }

    private void initView() {
        HelpCenter = findViewById(R.id.id_ll_helpCenter);
        HoleCenter = findViewById(R.id.id_ll_holeCenter);
        TaskCenter = findViewById(R.id.id_ll_taskCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);
        all = findViewById(R.id.id_tv_all);
        taskSolved = findViewById(R.id.id_tv_taskSolved);
        taskPosted = findViewById(R.id.id_tv_taskPosted);
        eRecyclerView = findViewById(R.id.id_recyclerview);
        taskBiz = new TaskBiz();
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(this,taskList);
        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eRecyclerView.setAdapter(taskAdapter);
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

    private void toHelpCenterActivity() {
        Intent intent = new Intent(this, HelpCenterActivity.class);
        startActivity(intent);
        finish();
    }

    private void toHoleCenterActivity(){
        Intent intent = new Intent(this,HoleCenterActivity.class);
        startActivity(intent);
        finish();
    }
}