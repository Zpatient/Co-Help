package com.cohelp.task_for_stu.ui.adpter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.UserInfoHolder;
import com.cohelp.task_for_stu.biz.QuestionBiz;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.ui.vo.RepeatForQuestion;
import com.cohelp.task_for_stu.utils.BasicUtils;
import com.cohelp.task_for_stu.utils.T;

import java.util.Collections;
import java.util.List;

public class RepeatAdapter extends RecyclerView.Adapter<RepeatAdapter.RepeatItemViewHolder>{
    List<RepeatForQuestion> repeatForQuestionList;
    Context context;
    private LayoutInflater eInflater;
    public RepeatAdapter(){}
    public RepeatAdapter(Context context, List<RepeatForQuestion> repeat){
        this.context = context;
        this.repeatForQuestionList = repeat;
        this.eInflater = LayoutInflater.from(context);
    }
    class RepeatItemViewHolder extends RecyclerView.ViewHolder{
        //TODO 编写数据绑定类
        public TextView title;
        public TextView author;
        public TextView content;
        public TextView grade;
        public TextView state;
        public RepeatAdapter repeatAdapter;
        public RepeatItemViewHolder(@NonNull View itemView,RepeatAdapter repeatAdapter){
            super(itemView);
            this.repeatAdapter = repeatAdapter;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO 跳转到问题详情页
                   //查看回答详情
                    QuestionBiz questionBiz = new QuestionBiz();
                    RepeatForQuestion repeat = repeatForQuestionList.get(getLayoutPosition());
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage(repeat.getContext().getContentDesc());
                    builder.setTitle("来自"+repeat.getUser().getNickName()+"的回答");
                    builder.setPositiveButton("确定",null);
                    if(UserInfoHolder.getInstance().geteUser().getId().equals(repeat.getContext().getDuid())){
                        builder.setPositiveButton("采纳", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RepeatForQuestion firstRpt = repeatForQuestionList.get(0);
                                if(firstRpt.getContext().getCurrentState().equals(Config.USER_PASSED)){
                                    T.showToast("你的问题已经采纳过了哦，不允许重复采纳哈~");
                                    return;
                                }
                                repeat.getContext().setCurrentState(Config.USER_PASSED);
                                //
                                BasicUtils.startLoadingProgress(view.getContext());
                                questionBiz.updateRepeat(repeat, new CommonCallback<List<RepeatForQuestion>>() {
                                    @Override
                                    public void onError(Exception e) {
                                        BasicUtils.stopLoadingProgress();
                                        T.showToast(e.getMessage());
                                    }

                                    @SuppressLint("NotifyDataSetChanged")
                                    @Override
                                    public void onSuccess(List<RepeatForQuestion> response) {
                                        BasicUtils.stopLoadingProgress();
                                        T.showToast("采纳成功！");
                                        //将已经采纳的放到第一个
                                        for(int i = 0 ; i < response.size() ; ++ i){
                                            RepeatForQuestion question = response.get(i);
                                            if(i != 0 && question.getContext().getCurrentState().equals(Config.USER_PASSED)){
                                                Collections.swap(response,i,0);
                                                break;
                                            }
                                        }
                                        repeatForQuestionList.clear();
                                        repeatForQuestionList.addAll(response);
                                        repeatAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        });
                        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                    }
//                    TextView textView = new TextView(builder.getContext());
//                    textView.setMaxLines(5);
//                    textView.setText(repeat.getContext().getContentDesc());
//                    builder.setView(textView);
                    builder.show();
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
    public RepeatItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = eInflater.inflate(R.layout.item_base_task, parent, false);
        return new RepeatItemViewHolder(view,this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RepeatItemViewHolder holder, int position) {
        RepeatForQuestion repeat = repeatForQuestionList.get(position);
        holder.title.setText("回答" + (position + 1) + ":");
        holder.author.setText(repeat.getUser().getNickName());
        holder.content.setText(repeat.getContext().getContentDesc());
        holder.grade.setText("答主采纳数:"+repeat.getUser().getRepeatCount());
        holder.state.setText("("+repeat.getContext().getCurrentState()+")");
    }

    @Override
    public int getItemCount() {
        return repeatForQuestionList.size();
    }
}
