package com.cohelp.task_for_stu.ui.adpter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.biz.TaskBiz;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.ui.vo.Task;
import com.cohelp.task_for_stu.utils.BasicUtils;
import com.cohelp.task_for_stu.utils.T;
import com.leon.lfilepickerlibrary.utils.StringUtils;

import java.util.List;

public class ManagerTaskAdapter extends RecyclerView.Adapter<ManagerTaskAdapter.TaskItemViewHolder>{
    List<Task> taskList;
    Context context;
    private LayoutInflater eInflater;
    TaskBiz taskBiz;
    public ManagerTaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
        this.eInflater = LayoutInflater.from(context);
        taskBiz = new TaskBiz();
    }
    class TaskItemViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView author;
        public TextView content;
        public TextView state;
        public Button jug;
        public Button delete;
        public TaskItemViewHolder(@NonNull View itemView){
            //TODO 编写每项的布局文件并拿取到每个对应的控件
            super(itemView);

            title = itemView.findViewById(R.id.id_tv_title);
            author = itemView.findViewById(R.id.id_tv_author);
            content = itemView.findViewById(R.id.id_tv_content);
            state = itemView.findViewById(R.id.id_tv_state);
            jug = itemView.findViewById(R.id.id_btn_jug);
            delete = itemView.findViewById(R.id.id_btn_del);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //提示框展示详细信息
                    Task task = taskList.get(getLayoutPosition());
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle(task.getContext().getTitle() + "的详情内容");
                    TextView textView = new TextView(view.getContext());
                    textView.setText(task.getContext().getContentDesc());
                    ScrollView scrollView = new ScrollView(view.getContext());
                    scrollView.addView(textView);
                    builder.setView(scrollView);
                    builder.setPositiveButton("已了解",null);
                    builder.show();
                }
            });

            jug.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Task task = taskList.get(getLayoutPosition());
                    if(!task.getContext().getCurrentState().equals(Config.WAIT_PASSED)){
                        T.showToast("当前状态下无需再审核了哦^^");
                        return;
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("审核任务");
                    EditText editText = new EditText(view.getContext());
                    editText.setHint("输入审核信息，当不通过时，用户可以看到原因");
                    builder.setView(editText);
                    builder.setPositiveButton("审核通过", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            BasicUtils.startLoadingProgress(view.getContext());
                            taskBiz.save(task, new CommonCallback<String>() {
                                @Override
                                public void onError(Exception e) {
                                    BasicUtils.stopLoadingProgress();
                                    T.showToast(e.getMessage());
                                }

                                @Override
                                public void onSuccess(String response) {
                                    BasicUtils.stopLoadingProgress();
                                    T.showToast(response);
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("不通过", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(StringUtils.isEmpty(editText.getText().toString())){
                                T.showToast("评审信息不能为空");
                                return;
                            }
                            BasicUtils.startLoadingProgress(view.getContext());
                            task.getContext().setCurrentState(Config.NOT_PASSED);
                            task.getContext().setStateMsg(editText.getText().toString());
                            taskBiz.save(task, new CommonCallback<String>() {
                                @Override
                                public void onError(Exception e) {
                                    BasicUtils.stopLoadingProgress();
                                    T.showToast(e.getMessage());
                                }

                                @Override
                                public void onSuccess(String response) {
                                    BasicUtils.stopLoadingProgress();
                                    T.showToast(response);
                                }
                            });
                        }
                    });
                    builder.show();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("删除任务");
                    builder.setMessage("确认删除？删除该任务会连同一起删除对应所有评论");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            BasicUtils.startLoadingProgress(view.getContext());
                            Task task = taskList.get(getLayoutPosition());
                            taskBiz.deleteTask(task.getContext().getId(), new CommonCallback<String>() {
                                @Override
                                public void onError(Exception e) {
                                    BasicUtils.stopLoadingProgress();
                                    T.showToast(e.getMessage());
                                }

                                @Override
                                public void onSuccess(String response) {
                                    BasicUtils.stopLoadingProgress();
                                    T.showToast(response);
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("取消",null);
                    builder.show();
                }
            });
        }
    }

    @NonNull
    @Override
    public TaskItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = eInflater.inflate(R.layout.item_manager_base_task, parent, false);
        return new ManagerTaskAdapter.TaskItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskItemViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.title.setText(task.getContext().getTitle());
        holder.author.setText(task.getPostedUser().getNickName());
        holder.content.setText(task.getContext().getContentDesc());
        holder.state.setText(task.getContext().getCurrentState());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
