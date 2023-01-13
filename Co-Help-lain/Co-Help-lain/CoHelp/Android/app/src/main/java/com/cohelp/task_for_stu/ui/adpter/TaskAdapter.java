package com.cohelp.task_for_stu.ui.adpter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.config.Config;
//import com.cohelp.task_for_stu.ui.activity.user.TaskDetailActivity;
import com.cohelp.task_for_stu.ui.vo.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskItemViewHolder>  {
    List<Task> taskList;
    Context context;
    private LayoutInflater eInflater;
    public TaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
        this.eInflater = LayoutInflater.from(context);
    }
    class TaskItemViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView author;
        public TextView content;
        public TextView grade;
        public TextView state;
        public TaskItemViewHolder(@NonNull View itemView){
            //TODO 编写每项的布局文件并拿取到每个对应的控件
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO 跳转到任务详情页
                    Task task = taskList.get(getLayoutPosition());
                    if(task.getContext().getCurrentState().equals(Config.NOT_PASSED)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("审核信息");
                        builder.setMessage("您的任务审核不通过，原因："+task.getContext().getStateMsg());
                        builder.setPositiveButton("知道了",null);
                        builder.show();
                    }else{
//                    TaskDetailActivity.launch(view.getContext(),task);
                    }

                }
            });
            title = itemView.findViewById(R.id.id_tv_title);
            author = itemView.findViewById(R.id.id_tv_author);
            content = itemView.findViewById(R.id.id_tv_content);
            grade = itemView.findViewById(R.id.id_tv_grade);
            state = itemView.findViewById(R.id.id_tv_state);

        }
    }
    @NonNull
    @Override
    public TaskAdapter.TaskItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = eInflater.inflate(R.layout.item_base_task, parent, false);
        return new TaskAdapter.TaskItemViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskItemViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.title.setText(task.getContext().getTitle());
        holder.author.setText(task.getPostedUser().getNickName());
        holder.content.setText(task.getContext().getContentDesc());
        holder.grade.setText("完成+"+task.getContext().getReward());
        holder.state.setText("("+task.getContext().getCurrentState()+")");
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
