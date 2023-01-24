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
import com.cohelp.task_for_stu.biz.QuestionBiz;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.QuestionAdapter;
import com.cohelp.task_for_stu.ui.vo.Question;
import com.cohelp.task_for_stu.utils.T;

import java.util.ArrayList;
import java.util.List;

public class MyQuestionActivity extends BaseActivity {
    LinearLayout HoleCenter;
    LinearLayout HelpCenter;
    LinearLayout TaskCenter;
    LinearLayout UserCenter;
    TextView all;
    TextView repeated;
    TextView asked;
    RecyclerView eRecyclerView;

    QuestionAdapter questionAdapter;
    List<Question> questionList;
    QuestionBiz questionBiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_question);
        setUpToolBar();
        setTitle("我的参与");
        initView();
        initEvent();
        loadAll();
    }

    private void initEvent() {

        HelpCenter.setOnClickListener(new View.OnClickListener() {
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
                loadAll();
            }
        });
        //todo 展示回复的问题，需要重新写业务方法
        repeated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoadingProgress();
                questionBiz.getAllMyQuestionRepeated(UserInfoHolder.getInstance().geteUser().getId(),new CommonCallback<List<Question>>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        T.showToast(e.getMessage());
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(List<Question> response) {
                        stopLoadingProgress();
                        T.showToast("查询任务数据成功！");
                        updateList(response);
                    }
                });
            }
        });

        asked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoadingProgress();
                questionBiz.getAllMyQuestionAsked(UserInfoHolder.getInstance().geteUser().getId(),new CommonCallback<List<Question>>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        T.showToast(e.getMessage());
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(List<Question> response) {
                        stopLoadingProgress();
                        T.showToast("查询问答数据成功！");
                        updateList(response);
                    }
                });
            }
        });
    }

    private void loadAll() {
        //TODO 查询所有问题
        startLoadingProgress();
        questionBiz.getAllMyQuestion(UserInfoHolder.getInstance().geteUser().getId(),new CommonCallback<List<Question>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                T.showToast(e.getMessage());
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(List<Question> response) {
                stopLoadingProgress();
                T.showToast("更新问答数据成功！");
                updateList(response);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateList(List<Question> response) {
        questionList.clear();
        questionList.addAll(response);
        questionAdapter.notifyDataSetChanged();
    }

    private void initView() {
        HelpCenter = findViewById(R.id.id_ll_helpCenter);
        HoleCenter = findViewById(R.id.id_ll_holeCenter);
        TaskCenter = findViewById(R.id.id_ll_taskCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);

        all = findViewById(R.id.id_tv_all);
        repeated = findViewById(R.id.id_tv_repeated);
        asked = findViewById(R.id.id_tv_asked);
        eRecyclerView = findViewById(R.id.id_recyclerview);
        questionBiz = new QuestionBiz();
        questionList = new ArrayList<>();
        questionAdapter = new QuestionAdapter(this, questionList);
        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eRecyclerView.setAdapter(questionAdapter);
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
        Intent intent = new Intent(this, HelpCenterActivity.class);
        startActivity(intent);
        finish();
    }
}