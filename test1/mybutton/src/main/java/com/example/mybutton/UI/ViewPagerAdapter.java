package com.example.mybutton.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybutton.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder> {

    private List<String> title = new ArrayList<>();
    private List<Integer> colors = new ArrayList<>();

    public ViewPagerAdapter(){
        title.add("hello0");
        title.add("hello1");
        title.add("hello2");
        title.add("hello3");
        title.add("hello4");
        colors.add(R.color.purple_700);
        colors.add(R.color.white);
        colors.add(R.color.teal_200);
        colors.add(R.color.teal_700);
        colors.add(R.color.white);


    }



    @NonNull
    @Override
    //这个viewholder返回的是下面定义的内部类对应的view，所以我们这里必须要找到一个view来返回
    public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //解析xml，从而得到了一个view
        return new ViewPagerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_item,parent,false));
    }

    //实现具体动作-把需要的数据传进来进行操作
    @Override
    public void onBindViewHolder(@NonNull ViewPagerViewHolder holder, int position) {
        holder.mTvVp.setText(title.get(position));
        holder.mLinerLayout.setBackgroundResource(colors.get(position));
    }

    @Override
    public int getItemCount() {
        return 5;
    }


    //封装-定义了一个内部类-----用于解析viewpager_item
    class ViewPagerViewHolder extends RecyclerView.ViewHolder{

        TextView mTvVp;
        LinearLayout mLinerLayout;
        public ViewPagerViewHolder(@NonNull View itemView) {
            super(itemView);

            mLinerLayout = itemView.findViewById(R.id.vp_linerlayout);
            mTvVp = itemView.findViewById(R.id.tv_vp);
        }
    }

}
