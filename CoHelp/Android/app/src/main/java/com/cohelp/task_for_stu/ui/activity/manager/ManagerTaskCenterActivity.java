package com.cohelp.task_for_stu.ui.activity.manager;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.biz.TaskBiz;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.ManagerTaskAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefresh;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.cohelp.task_for_stu.ui.vo.Task;
import com.cohelp.task_for_stu.utils.T;
import com.leon.lfilepickerlibrary.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ManagerTaskCenterActivity extends BaseActivity {
    TextView all;
    TextView hasPass;
    TextView waitPass;
    LinearLayout taskCenter;
    LinearLayout questionCenter;
    LinearLayout userBoard;
    EditText search;
    ImageView searchBtn;

    SwipeRefreshLayout eSwipeRefreshLayout;
    RecyclerView eRecyclerView;
    ManagerTaskAdapter managerTaskAdapter;
    TaskBiz taskBiz;
    List<Task> taskList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_task_center);
        setUpToolBar();
        setTitle("任务管理");
        initView();
        initEvent();
        loadAll();
    }

    private void initEvent() {
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
        hasPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoadingProgress();
                taskBiz.getAll(new CommonCallback<List<Task>>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        T.showToast(e.getMessage());
                        eSwipeRefreshLayout.setRefreshing(false);
                        eSwipeRefreshLayout.setPullUpRefreshing(false);
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
        });

        waitPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoadingProgress();
                taskBiz.searchByState(Config.WAIT_PASSED,new CommonCallback<List<Task>>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        T.showToast(e.getMessage());
                        eSwipeRefreshLayout.setRefreshing(false);
                        eSwipeRefreshLayout.setPullUpRefreshing(false);
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
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = search.getText().toString();
                if(!StringUtils.isEmpty(s)){
                    T.showToast("查询的标题不能为空哦~");
                }
                startLoadingProgress();
                taskBiz.searchTaskByTitleForManager(s, new CommonCallback<List<Task>>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        T.showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(List<Task> response) {
                        stopLoadingProgress();
                        T.showToast("查询成功！");
                        updateList(response);
                    }
                });
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 展示全部状态（除未审核的信息）
                loadAll();
            }
        });

        eSwipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadAll();
            }
        });

        eSwipeRefreshLayout.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                loadAll();
            }
        });
    }

    private void initView() {
        all = findViewById(R.id.id_tv_all);
        hasPass = findViewById(R.id.id_tv_hasPass);
        waitPass = findViewById(R.id.id_tv_waitPass);
        taskCenter = findViewById(R.id.id_ll_taskCenter);
        questionCenter = findViewById(R.id.id_ll_questionCenter);
        userBoard = findViewById(R.id.id_ll_userBoard);
        search = findViewById(R.id.id_et_search);
        searchBtn = findViewById(R.id.id_iv_search);
        eSwipeRefreshLayout = findViewById(R.id.id_swiperefresh);
        eRecyclerView = findViewById(R.id.id_recyclerview);
        taskBiz = new TaskBiz();
        taskList = new ArrayList<>();
        managerTaskAdapter = new ManagerTaskAdapter(this, taskList);
        eSwipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        eSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);
        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eRecyclerView.setAdapter(managerTaskAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadAll();
    }
    @SuppressLint("NotifyDataSetChanged")
    private void updateList(List<Task> response) {
        taskList.clear();
        taskList.addAll(response);
        managerTaskAdapter.notifyDataSetChanged();
        eSwipeRefreshLayout.setRefreshing(false);
        eSwipeRefreshLayout.setPullUpRefreshing(false);
    }

    private void loadAll() {
        startLoadingProgress();
        taskBiz.getAllOfAllTasks(new CommonCallback<List<Task>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                T.showToast(e.getMessage());
                eSwipeRefreshLayout.setRefreshing(false);
                eSwipeRefreshLayout.setPullUpRefreshing(false);
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
}