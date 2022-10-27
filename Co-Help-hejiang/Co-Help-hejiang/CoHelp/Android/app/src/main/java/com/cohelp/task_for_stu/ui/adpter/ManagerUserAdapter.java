package com.cohelp.task_for_stu.ui.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.bean.User;
import com.cohelp.task_for_stu.biz.UserBiz;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.ui.CircleTransform;
import com.cohelp.task_for_stu.ui.activity.manager.ManagerCreateNewUserActivity;
import com.cohelp.task_for_stu.ui.activity.manager.ManagerUserDetailsActivity;
import com.cohelp.task_for_stu.utils.BasicUtils;
import com.cohelp.task_for_stu.utils.T;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ManagerUserAdapter extends RecyclerView.Adapter<ManagerUserAdapter.ManagerUserItemViewHolder>{
    List<User> userList;
    Context context;
    UserBiz userBiz;
    private LayoutInflater eInflater;
    public ManagerUserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        this.eInflater = LayoutInflater.from(context);
        userBiz = new UserBiz();
    }
    class ManagerUserItemViewHolder extends RecyclerView.ViewHolder{
        public ImageView icon;
        public TextView nickname;
        public TextView content;
        public TextView state;
        public Button save;
        public Button del;
        public ManagerUserItemViewHolder(@NonNull View itemView){
            //TODO 编写每项的布局文件并拿取到每个对应的控件
            super(itemView);
            icon = itemView.findViewById(R.id.id_iv_icon);
            nickname = itemView.findViewById(R.id.id_tv_author);
            content = itemView.findViewById(R.id.id_tv_content);
            state = itemView.findViewById(R.id.id_tv_state);
            save = itemView.findViewById(R.id.id_btn_save);
            del = itemView.findViewById(R.id.id_btn_del);
            //编写事件
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BasicUtils.startLoadingProgress(view.getContext());
                    User user = userList.get(getLayoutPosition());
                    userBiz.del(user.getId(), new CommonCallback<String>() {
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
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //todo 跳转至createNewUser
                    ManagerCreateNewUserActivity.launch(context,userList.get(getLayoutPosition()));

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO 跳转到人员详情页
                    ManagerUserDetailsActivity.launch(context,userList.get(getLayoutPosition()));
                }
            });

        }
    }

    @NonNull
    @Override
    public ManagerUserItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = eInflater.inflate(R.layout.item_manager_user, parent, false);
        return new ManagerUserItemViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ManagerUserItemViewHolder holder, int position) {
        User user = userList.get(position);
        holder.content.setText(
                "已回答帖子：" + user.getRepeatCount() + "\n" +
                "已完成任务：" + user.getTaskCount() + "\n"
        );
        holder.state.setText(
                user.isManager() ? "(管理员)" : "(普通用户)"
        );
        holder.nickname.setText(user.getNickName());
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
