package com.cohelp.task_for_stu.ui.activity.manager;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.bean.User;
import com.cohelp.task_for_stu.biz.UserBiz;
import com.cohelp.task_for_stu.listener.ClickListener;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.ManagerUserAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefresh;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.cohelp.task_for_stu.utils.T;
import com.leon.lfilepickerlibrary.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ManagerUserCenterActivity extends BaseActivity {
    TextView all;
    TextView manager;
    TextView commonUser;
    LinearLayout taskCenter;
    LinearLayout questionCenter;
    LinearLayout userBoard;
    EditText search;
    ImageView searchBtn;

    SwipeRefreshLayout eSwipeRefreshLayout;
    RecyclerView eRecyclerView;
    ManagerUserAdapter managerUserAdapter;
    UserBiz userBiz;
    List<User> userList;
    private void initEvent() {
        setToolbar(R.drawable.common_add, new ClickListener() {
            @Override
            public void click() {
                toManagerCreateNewUserActivity();
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
        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoadingProgress();
                userBiz.getAllManagers(new CommonCallback<List<User>>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        T.showToast(e.getMessage());
                        eSwipeRefreshLayout.setRefreshing(false);
                        eSwipeRefreshLayout.setPullUpRefreshing(false);
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(List<User> response) {
                        stopLoadingProgress();
                        T.showToast("更新人员数据成功！");
                        updateList(response);
                    }
                });
            }
        });

        commonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoadingProgress();
                userBiz.getAll(new CommonCallback<List<User>>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        T.showToast(e.getMessage());
                        eSwipeRefreshLayout.setRefreshing(false);
                        eSwipeRefreshLayout.setPullUpRefreshing(false);
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(List<User> response) {
                        stopLoadingProgress();
                        T.showToast("更新人员数据成功！");
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
                    T.showToast("查询的昵称不能为空哦~");
                }
                startLoadingProgress();
                userBiz.searchByName(s, new CommonCallback<List<User>>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        T.showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(List<User> response) {
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
        manager = findViewById(R.id.id_tv_manager);
        commonUser = findViewById(R.id.id_tv_commonUser);
        taskCenter = findViewById(R.id.id_ll_taskCenter);
        questionCenter = findViewById(R.id.id_ll_questionCenter);
        userBoard = findViewById(R.id.id_ll_userBoard);
        search = findViewById(R.id.id_et_search);
        searchBtn = findViewById(R.id.id_iv_search);
        eSwipeRefreshLayout = findViewById(R.id.id_swiperefresh);
        eRecyclerView = findViewById(R.id.id_recyclerview);
        userBiz = new UserBiz();
        userList = new ArrayList<>();
        managerUserAdapter = new ManagerUserAdapter(this,userList);
        eSwipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        eSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);
        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eRecyclerView.setAdapter(managerUserAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadAll();
    }
    @SuppressLint("NotifyDataSetChanged")
    private void updateList(List<User> response) {
        userList.clear();
        userList.addAll(response);
        managerUserAdapter.notifyDataSetChanged();
        eSwipeRefreshLayout.setRefreshing(false);
        eSwipeRefreshLayout.setPullUpRefreshing(false);
    }

    private void loadAll() {
        startLoadingProgress();
        userBiz.getAllOfAllUsers(new CommonCallback<List<User>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                T.showToast(e.getMessage());
                eSwipeRefreshLayout.setRefreshing(false);
                eSwipeRefreshLayout.setPullUpRefreshing(false);
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(List<User> response) {
                stopLoadingProgress();
                T.showToast("更新人员数据成功！");
                updateList(response);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_user_center);
        setTitle("人员管理");
        initView();
        initEvent();
        loadAll();
    }

}