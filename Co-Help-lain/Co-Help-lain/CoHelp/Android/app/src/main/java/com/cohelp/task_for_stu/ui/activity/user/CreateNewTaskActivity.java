package com.cohelp.task_for_stu.ui.activity.user;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bigkoo.pickerview.TimePickerView;
import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.UserInfoHolder;
import com.cohelp.task_for_stu.bean.BaseTask;
import com.cohelp.task_for_stu.bean.User;
import com.cohelp.task_for_stu.biz.TaskBiz;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.vo.Task;
import com.cohelp.task_for_stu.utils.BasicUtils;
import com.cohelp.task_for_stu.utils.T;

import java.util.Calendar;
import java.util.Date;

public class CreateNewTaskActivity extends BaseActivity {
    EditText title;
    EditText content;
    EditText reward;
    EditText startTime;
    EditText endTime;
    Button button;
    TaskBiz taskBiz;
    Task task;
    BaseTask baseTask;
    TimePickerView pickerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);
        setUpToolBar();
        setTitle("创建新任务");
        initView();
        initEvent();
    }
    private void initEvent() {
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickerView.show(endTime);
            }
        });
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickerView.show(startTime);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String te = title.getText().toString();
                String ct = content.getText().toString();
                String rds = reward.getText().toString();
                String pt = startTime.getText().toString();
                String et = endTime.getText().toString();
                if (!BasicUtils.StringArrayEmpty(new String[]{te, ct , rds, pt , et})) {
                    T.showToast("输入的信息不能为空哦~");
                    return;
                }
                int rd = Integer.parseInt(rds);

                User user = UserInfoHolder.getInstance().geteUser();
                if(!(rd > 0 && rd <= user.getGrade())){
                    T.showToast("输入的积分不能小于0或者大于自己拥有的数值哦~");
                    return;
                }
                user.setGrade(user.getGrade() - rd);
                UserInfoHolder.getInstance().setUser(user);
                baseTask.setContentDesc(ct);
                baseTask.setPuid(user.getId());
                baseTask.setTitle(te);
                baseTask.setReward(rd);
                baseTask.setJugFlag((byte) Config.IS_TASK);
                //TODO 管理员功能实现后应订正为待审核
                baseTask.setCurrentState(Config.WAIT_PASSED);
//                baseTask.setPostDate(new Date());
                baseTask.setStateMsg("暂无新消息");
                task.setPostedUser(user);
                task.setContext(baseTask);
                startLoadingProgress();
                taskBiz.save(task, new CommonCallback<String>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        T.showToast(e.getMessage());
                        finish();
                    }

                    @Override
                    public void onSuccess(String response) {
                        stopLoadingProgress();
                        T.showToast(response);
                        finish();
                    }
                });
            }
        });
    }

    private void initView() {
        title = findViewById(R.id.id_et_title);
        content = findViewById(R.id.id_et_content);
        reward = findViewById(R.id.id_et_reward);
        button = findViewById(R.id.id_btn_submit);
        startTime = findViewById(R.id.id_et_startDate);
        endTime = findViewById(R.id.id_et_endDate);
        content = findViewById(R.id.id_et_content);
        task = new Task();
        taskBiz = new TaskBiz();
        baseTask = new BaseTask();
        task.setContext(baseTask);
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.setTime(new Date());
        endDate.setTime(new Date(new Date().getTime() + 1000 * 60 * 60 * 24 * 7));
        pickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                EditText tv = (EditText) v;
                tv.setText(Config.dateFormat.format(date));
                if(tv == startTime){
                    task.getContext().setStartDate(date);
                }else if(tv == endTime){
                    task.getContext().setEndDate(date);
                }
            }
        }).setType(new boolean[]{true, true, true, true, true, false})
                .setLabel(" 年", "月", "日", "时", "分", "")
                .isCenterLabel(true)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build();

    }
}