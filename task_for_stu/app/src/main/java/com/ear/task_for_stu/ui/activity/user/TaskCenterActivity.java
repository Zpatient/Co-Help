package com.ear.task_for_stu.ui.activity.user;

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

import com.ear.task_for_stu.R;
import com.ear.task_for_stu.biz.TaskBiz;
import com.ear.task_for_stu.config.Config;
import com.ear.task_for_stu.listener.ClickListener;
import com.ear.task_for_stu.net.CommonCallback;
import com.ear.task_for_stu.ui.activity.BaseActivity;
import com.ear.task_for_stu.ui.adpter.TaskAdapter;
import com.ear.task_for_stu.ui.view.SwipeRefresh;
import com.ear.task_for_stu.ui.view.SwipeRefreshLayout;
import com.ear.task_for_stu.ui.vo.Task;
import com.ear.task_for_stu.utils.T;
import com.leon.lfilepickerlibrary.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TaskCenterActivity extends BaseActivity {
    LinearLayout questionCenter;
    LinearLayout TaskCenter;
    LinearLayout UserCenter;
    LinearLayout lead;
    TextView all;
    TextView waitedSolve;
    TextView solved;
    TextView doing;
    EditText searchedContent;
    ImageView searchBtn;
    SwipeRefreshLayout eSwipeRefreshLayout;
    RecyclerView eRecyclerView;

    TaskAdapter taskAdapter;
    List<Task> taskList;
    TaskBiz taskBiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_center);
        initView();
        initEvent();
        setTitle("任务中心");
    }

    private void initEvent() {
        setToolbar(R.drawable.common_add, new ClickListener() {
            @Override
            public void click() {
                toCreateNewTaskActivity();
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

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 展示全部状态（除未审核的信息）
                loadAll();
            }
        });

        waitedSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 展示待解决的问题
                startLoadingProgress();
                taskBiz.searchByState(Config.TASK_WAIT_SOLVE, new CommonCallback<List<Task>>() {
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

        solved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 展示已解决的问题
                startLoadingProgress();
                taskBiz.searchByState(Config.TASK_SOLVE, new CommonCallback<List<Task>>() {
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
        doing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 展示正在解决的问题
                startLoadingProgress();
                taskBiz.searchByState(Config.TASK_DOING_SOLVE, new CommonCallback<List<Task>>() {
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
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 从服务端搜索
                String s = searchedContent.getText().toString();
                if(!StringUtils.isEmpty(s)){
                    T.showToast("查询的标题不能为空哦~");
                }
                startLoadingProgress();
                taskBiz.searchByTitle(s, new CommonCallback<List<Task>>() {
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

    private void toCreateNewTaskActivity() {
        //TODO 创建新任务
        Intent intent = new Intent(this,CreateNewTaskActivity.class);
        startActivityForResult(intent,1001);
    }

    private void loadAll() {
        //TODO 查询所有问题
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

    @SuppressLint("NotifyDataSetChanged")
    private void updateList(List<Task> response) {
        taskList.clear();
        taskList.addAll(response);
        taskAdapter.notifyDataSetChanged();
        eSwipeRefreshLayout.setRefreshing(false);
        eSwipeRefreshLayout.setPullUpRefreshing(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadAll();
    }

    private void initView() {
        questionCenter = findViewById(R.id.id_ll_questionCenter);
        TaskCenter = findViewById(R.id.id_ll_taskCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);
        lead = findViewById(R.id.id_ll_leaderBoard);
        all = findViewById(R.id.id_tv_all);
        waitedSolve = findViewById(R.id.id_tv_waitedSolve);
        solved = findViewById(R.id.id_tv_solved);
        searchedContent = findViewById(R.id.id_et_search);
        searchBtn = findViewById(R.id.id_iv_search);
        eSwipeRefreshLayout = findViewById(R.id.id_swiperefresh);
        eRecyclerView = findViewById(R.id.id_recyclerview);
        doing = findViewById(R.id.id_tv_doing);
        eSwipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        eSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);
        taskBiz = new TaskBiz();
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(this,taskList);
        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eRecyclerView.setAdapter(taskAdapter);
        loadAll();
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