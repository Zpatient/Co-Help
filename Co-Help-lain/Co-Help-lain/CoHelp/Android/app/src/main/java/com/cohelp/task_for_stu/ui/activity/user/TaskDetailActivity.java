package com.cohelp.task_for_stu.ui.activity.user;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.UserInfoHolder;
import com.cohelp.task_for_stu.bean.Comment;
import com.cohelp.task_for_stu.bean.User;
import com.cohelp.task_for_stu.biz.TaskBiz;
import com.cohelp.task_for_stu.biz.UserBiz;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.ui.CircleTransform;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.vo.Task;
import com.cohelp.task_for_stu.utils.T;
import com.leon.lfilepickerlibrary.utils.StringUtils;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.utils.L;

import java.util.Calendar;

public class TaskDetailActivity extends BaseActivity {
    private static final String KEY_TASK = "TASK";
    LinearLayout questionCenter;
    LinearLayout TaskCenter;
    LinearLayout UserCenter;

    TextView content;
    TextView nickname;
    TextView postTime;
    TextView timeLimit;
    TextView state;
    TextView loadMore;


    Button fun;
    ImageView icon;

    Task task;
    TaskBiz taskBiz;
    UserBiz userBiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        setUpToolBar();
        Intent intent = getIntent();
        if(intent != null){
            task = (Task) intent.getSerializableExtra(KEY_TASK);
            setTitle(task.getContext().getTitle());
        }
        if(task == null){
            T.showToast("参数传递错误");
            finish();
            return;
        }
        initView();
        initEvent();
    }

    private void initEvent() {
        //编写功能键
        fun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("劳动不易，理性评价哦^^");

                if(UserInfoHolder.getInstance().geteUser().getId().equals(task.getPostedUser().getId())){
//                    if(!task.getContext().getCurrentState().equals(Config.TASK_DOING_SOLVE)){
//                        T.showToast("还没有人接受该任务或者该任务已经完成哦~");
//                        return;
//                    }
                    //如果是本人那就结算评价
                    LinearLayout layout = new LinearLayout(builder.getContext());
                    LinearLayout layout1 = new LinearLayout(builder.getContext());
                    EditText editText = new EditText(builder.getContext());
                    TextView textView = new TextView(builder.getContext());
                    EditText editText1 = new EditText(builder.getContext());
                    //额外奖赏
                    editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
                    //评价框
                    editText.setMaxLines(3);
                    editText.setHint("在这里输入评价");
                    textView.setText("想额外打赏吗？在此输入哦^^");
                    layout.setVerticalGravity(Gravity.CENTER);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout1.setOrientation(LinearLayout.HORIZONTAL);
                    layout1.addView(textView);
                    layout1.addView(editText1);
                    layout.addView(editText);
                    layout.addView(layout1);
                    builder.setView(layout);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String s = editText1.getText().toString();
                            if(!StringUtils.isEmpty(s)){
                                Integer exReward = Integer.parseInt(s);
                                int finalReward = task.getPostedUser().getGrade() - exReward;
                                if(finalReward >= 0){
                                    User user = UserInfoHolder.getInstance().geteUser();
                                    user.setGrade(finalReward);
                                    task.getContext().setReward(task.getContext().getReward() + exReward);
                                    //更新用户积分
                                    userBiz.updateUser(user, new CommonCallback<User>() {
                                        @Override
                                        public void onError(Exception e) {
                                            L.e(e.getMessage());
                                        }
                                        @Override
                                        public void onSuccess(User response) {
                                        }
                                    });
                                }else{
                                    T.showToast("积分不足哦^^");
                                }
                            }
                            String s1 = editText.getText().toString();
                            if(StringUtils.isEmpty(s1)){
                                T.showToast("评价内容不能为空哈^^");
                                return;
                            }else{
                                Comment comment = new Comment();
                                comment.setContentDesc(s1);
                                comment.setPostDate(Calendar.getInstance().getTime());
                                comment.setPid(task.getDoneUser().getId());
                                comment.setDid(task.getPostedUser().getId());
                                comment.setRid(task.getContext().getId());
                                comment.setTitle(task.getContext().getTitle());
                                task.setComment(comment);
                            }
                            task.getContext().setCurrentState(Config.TASK_SOLVE);

                            //todo 发起更新任务的请求
                            startLoadingProgress();
                            taskBiz.finishTask(task, new CommonCallback<String>() {
                                @Override
                                public void onError(Exception e) {
                                    stopLoadingProgress();
                                    T.showToast(e.getMessage());
                                    toTaskCenterActivity();
                                }

                                @Override
                                public void onSuccess(String response) {
                                    stopLoadingProgress();
                                    T.showToast(response);
                                    toTaskCenterActivity();
                                }
                            });


                        }
                    });
                    builder.setNeutralButton("取消",null);
                    builder.show();
                }else{
                    startLoadingProgress();
                    //如果不是本人那最多就只能接受任务
                    if(task.getContext().getCurrentState().equals(Config.TASK_WAIT_SOLVE)){
                        //需要在服务端再次进行任务状态判断，避免并发情况发生（小概率事件）
                        task.getContext().setDuid(UserInfoHolder.getInstance().geteUser().getId());
                        task.getContext().setCurrentState(Config.TASK_DOING_SOLVE);
                        taskBiz.save(task, new CommonCallback<String>() {
                            @Override
                            public void onError(Exception e) {
                                stopLoadingProgress();
                                T.showToast(e.getMessage());
                                toTaskCenterActivity();
                                finish();
                            }

                            @Override
                            public void onSuccess(String response) {
                                stopLoadingProgress();
                                T.showToast(response);
                                toTaskCenterActivity();
                                finish();
                            }
                        });
                    }else{
                        stopLoadingProgress();
                        T.showToast("该任务名花有主啦^^");
                    }
                }
            }
        });
        //加载更多信息
        loadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = UserInfoHolder.getInstance().geteUser();
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("更多详情");
                //如果等待接受的状态，那么任何人都不能查看用户信息
                if(task.getContext().getCurrentState().equals(Config.TASK_WAIT_SOLVE)){
                    builder.setMessage("该任务还没有被任何人接受哦^^");
                }else{
                    //进行中或者已完成的状态下，任务双方可以互相查看对方联系方式
                    if(user.getId().equals(task.getPostedUser().getId())){
                        builder.setMessage("被委托人姓名："+task.getDoneUser().getRealName() + "\n" + "被委托人电话号码：" + task.getDoneUser().getPhone());
                        if(task.getComment() != null){
                            builder.setMessage("被委托人姓名："+task.getDoneUser().getRealName() + "\n" + "被委托人电话号码：" + task.getDoneUser().getPhone()
                                    + "\n评价：" + task.getComment().getContentDesc()
                            );
                        }
                    }else if(user.getId().equals(task.getDoneUser().getId())){
                        builder.setMessage("委托人姓名："+task.getPostedUser().getRealName() + "\n" + "委托人电话号码：" + task.getPostedUser().getPhone());
                        if(task.getComment() != null){
                            builder.setMessage("委托人姓名："+task.getPostedUser().getRealName() + "\n" + "委托人电话号码：" + task.getPostedUser().getPhone()
                                    + "\n评价：" + task.getComment().getContentDesc()
                            );
                        }
                    }else{
                        builder.setMessage("名花自有名主属,您暂无权限查看该信息哦^^");
                    }
                }
                builder.setPositiveButton("知道了",null);
                builder.show();
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

    @SuppressLint("SetTextI18n")
    private void initView() {
        content = findViewById(R.id.id_tv_content);
        nickname = findViewById(R.id.id_tv_nickname);
        fun = findViewById(R.id.id_btn_fun);
        postTime = findViewById(R.id.id_tv_postTime);
        icon =  findViewById(R.id.id_iv_icon);
        questionCenter = findViewById(R.id.id_ll_questionCenter);
        TaskCenter = findViewById(R.id.id_ll_taskCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);
        timeLimit = findViewById(R.id.id_tv_timeLimit);
        state = findViewById(R.id.id_tv_state);
        loadMore = findViewById(R.id.id_tv_loadMore);

        userBiz = new UserBiz();
        taskBiz = new TaskBiz();

        timeLimit.setText("任务时间：" + Config.dateFormat.format(task.getContext().getPostDate()) + "-" + Config.dateFormat.format(task.getContext().getEndDate()));
        state.setText(task.getContext().getCurrentState());
        nickname.setText(task.getPostedUser().getNickName());
        content.setText(task.getContext().getContentDesc());
        postTime.setText(Config.dateFormat.format(task.getContext().getPostDate()));
        Picasso.get()
                .load(Config.rsUrl + task.getPostedUser().getIcon())
                .placeholder(R.drawable.pictures_no)
                .transform(new CircleTransform())
                .into(icon);
        fun.setText(UserInfoHolder.getInstance().geteUser().getId().equals(task.getPostedUser().getId()) ? "结算" : "接受");
    }
    public static void launch(Context context, Task task) {
        Intent intent = new Intent(context,TaskDetailActivity.class);
        intent.putExtra(KEY_TASK,task);
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