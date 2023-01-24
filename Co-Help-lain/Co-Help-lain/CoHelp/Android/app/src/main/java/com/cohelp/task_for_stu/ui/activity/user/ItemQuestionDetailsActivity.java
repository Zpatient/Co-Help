package com.cohelp.task_for_stu.ui.activity.user;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.UserInfoHolder;
import com.cohelp.task_for_stu.bean.BaseTask;
import com.cohelp.task_for_stu.biz.QuestionBiz;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.ui.CircleTransform;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.RepeatAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefresh;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.cohelp.task_for_stu.ui.vo.Question;
import com.cohelp.task_for_stu.ui.vo.RepeatForQuestion;
import com.cohelp.task_for_stu.utils.T;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemQuestionDetailsActivity extends BaseActivity {
    private static final String KEY_QUESTION = "QUESTION";
    LinearLayout questionCenter;
    LinearLayout TaskCenter;
    LinearLayout UserCenter;

    TextView content;
    TextView nickname;
    TextView postTime;

    Button createRepeat;

    SwipeRefreshLayout eSwipeRefreshLayout;
    RecyclerView eRecyclerView;


    ImageView icon;
    Question question;
    QuestionBiz questionBiz;
    RepeatAdapter repeatAdapter;
    List<RepeatForQuestion> repeats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_question_details);
        setUpToolBar();
        Intent intent = getIntent();
        if(intent != null){
            question = (Question) intent.getSerializableExtra(KEY_QUESTION);
            setTitle(question.getContext().getTitle());
        }
        if(question == null){
            T.showToast("参数传递错误");
            finish();
            return;
        }
        initView();
        initEvent();
        loadRepeats();
    }

    /**
     *TODO 编写所有事件和控件初始化 最后编写列表更新类
     */
    private void initEvent() {
        Context context = this;
        /**
         * 创建新回答
         */
        createRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!question.getUser().getId().equals(UserInfoHolder.getInstance().geteUser().getId())){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    EditText editText = new EditText(builder.getContext());
                    editText.setMaxLines(5);
                    editText.setHint("输入回答内容");
                    builder.setTitle("撰写回答");
                    builder.setView(editText);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BaseTask baseTask = new BaseTask();
                            baseTask.setContentDesc(editText.getText().toString());
                            baseTask.setPuid(UserInfoHolder.getInstance().geteUser().getId());
                            baseTask.setDuid(question.getUser().getId());
                            baseTask.setReward(question.getContext().getReward());
                            baseTask.setJugFlag((byte) Config.IS_REPEAT);
                            baseTask.setRid(question.getContext().getId());
                            baseTask.setCurrentState(Config.WAIT_USER_PASSED);
//                baseTask.setPostDate(new Date());
                            startLoadingProgress();
                            questionBiz.insertRepeat(baseTask, new CommonCallback<String>() {
                                @Override
                                public void onError(Exception e) {
                                    stopLoadingProgress();
                                    T.showToast(e.getMessage());

                                }

                                @Override
                                public void onSuccess(String response) {
                                    stopLoadingProgress();
                                    T.showToast(response);
                                    loadRepeats();
                                }
                            });
                        }
                    });
                    //一样要show
                    builder.show();
                }else{
                    T.showToast("自己不可以回答自己的问题哦^^");
                }
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
        eSwipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRepeats();
            }
        });

        eSwipeRefreshLayout.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                loadRepeats();
            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    private void initView() {
        content = findViewById(R.id.id_tv_content);
        nickname = findViewById(R.id.id_tv_nickname);
        createRepeat = findViewById(R.id.id_btn_repeat);
        postTime = findViewById(R.id.id_tv_postTime);
        icon =  findViewById(R.id.id_iv_icon);
        questionCenter = findViewById(R.id.id_ll_questionCenter);
        TaskCenter = findViewById(R.id.id_ll_taskCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);
        eRecyclerView = findViewById(R.id.id_recyclerview);
        eSwipeRefreshLayout = findViewById(R.id.id_swiperefresh);
        eSwipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        eSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);
        questionBiz = new QuestionBiz();
        repeats = new ArrayList<>();
        repeatAdapter = new RepeatAdapter(this,repeats);
        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eRecyclerView.setAdapter(repeatAdapter);

        nickname.setText(question.getUser().getNickName());
        content.setText(question.getContext().getContentDesc());
        postTime.setText(Config.dateFormat.format(question.getContext().getPostDate()));
        Picasso.get()
                .load(Config.rsUrl + question.getUser().getIcon())
                .placeholder(R.drawable.pictures_no)
                .transform(new CircleTransform())
                .into(icon);

    }

    private void loadRepeats() {
        startLoadingProgress();
        questionBiz.getRepeatByRid(question.getContext().getId(),new CommonCallback<List<RepeatForQuestion>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                T.showToast(e.getMessage());
                eSwipeRefreshLayout.setRefreshing(false);
                eSwipeRefreshLayout.setPullUpRefreshing(false);
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(List<RepeatForQuestion> response) {
                stopLoadingProgress();
                System.out.println(response);
                T.showToast("更新问答数据成功！");
                updateList(response);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateList(List<RepeatForQuestion> response) {
        //将已经采纳的放到第一个
        for(int i = 0 ; i < response.size() ; ++ i){
            RepeatForQuestion question = response.get(i);
            if(i != 0 && question.getContext().getCurrentState().equals(Config.USER_PASSED)){
                Collections.swap(response,i,0);
                break;
            }
        }
        repeats.clear();
        repeats.addAll(response);
        repeatAdapter.notifyDataSetChanged();
        eSwipeRefreshLayout.setRefreshing(false);
        eSwipeRefreshLayout.setPullUpRefreshing(false);
    }

    public static void launch(Context context, Question question) {
        Intent intent = new Intent(context,ItemQuestionDetailsActivity.class);
        intent.putExtra(KEY_QUESTION,question);
        context.startActivity(intent);
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