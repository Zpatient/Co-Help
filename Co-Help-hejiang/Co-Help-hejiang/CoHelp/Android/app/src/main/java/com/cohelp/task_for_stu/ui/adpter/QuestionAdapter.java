package com.cohelp.task_for_stu.ui.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.ui.activity.user.ItemQuestionDetailsActivity;
import com.cohelp.task_for_stu.ui.vo.Question;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionItemViewHolder> {
    List<Question> questionList;
    Context context;
    private LayoutInflater eInflater;
    public QuestionAdapter(){}
    public QuestionAdapter(Context context, List<Question> questions){
        this.context = context;
        this.questionList = questions;
        this.eInflater = LayoutInflater.from(context);
    }
    class QuestionItemViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView author;
        public TextView content;
        public TextView grade;
        public TextView state;
        public QuestionItemViewHolder(@NonNull View itemView){
            //TODO 编写每项的布局文件并拿取到每个对应的控件
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO 跳转到问题详情页
                    ItemQuestionDetailsActivity.launch(context,questionList.get(getLayoutPosition()));
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
    public QuestionItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = eInflater.inflate(R.layout.item_base_task, parent, false);
        return new QuestionItemViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull QuestionItemViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.title.setText(question.getContext().getTitle());
        holder.author.setText(question.getUser().getNickName());
        holder.content.setText(question.getContext().getContentDesc());
        holder.grade.setText("采纳+"+question.getContext().getReward());
        holder.state.setText("("+question.getContext().getCurrentState()+")");
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
}

