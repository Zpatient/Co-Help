package com.ear.task_for_stu.ui.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ear.task_for_stu.R;
import com.ear.task_for_stu.bean.User;
import com.ear.task_for_stu.config.Config;
import com.ear.task_for_stu.ui.CircleTransform;
import com.ear.task_for_stu.ui.vo.RepeatForQuestion;
import com.squareup.picasso.Picasso;


import java.util.List;

public class LeadAdapter extends RecyclerView.Adapter<LeadAdapter.LeadItemViewHolder>{
    List<User> userList;
    Context context;
    private LayoutInflater eInflater;
    public LeadAdapter(Context context, List<User> userList){
        this.context = context;
        this.userList = userList;
        this.eInflater = LayoutInflater.from(context);
    }
    class LeadItemViewHolder extends RecyclerView.ViewHolder{
        public TextView nickname;
        public TextView topNum;
        public TextView solveNum;
        public ImageView icon;
        public LeadItemViewHolder(@NonNull View itemView){
            super(itemView);
            nickname = itemView.findViewById(R.id.id_tv_nickname);
            topNum = itemView.findViewById(R.id.id_tv_topNum);
            solveNum = itemView.findViewById(R.id.id_tv_solveNum);
            icon = itemView.findViewById(R.id.id_iv_icon);
        }
    }

    @NonNull
    @Override
    public LeadItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = eInflater.inflate(R.layout.item_lead, parent, false);
        return new LeadItemViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LeadItemViewHolder holder, int position) {
        User user = userList.get(position);
        holder.topNum.setText("No."+(position+1));
        holder.nickname.setText(user.getNickName());
        holder.solveNum.setText("解决数："+user.getTaskCount());
        Picasso.get()
                .load(Config.rsUrl + user.getIcon())
                .placeholder(R.drawable.pictures_no)
                .transform(new CircleTransform())
                .into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
