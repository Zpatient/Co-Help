package com.example.mybutton.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mybutton.R;

//原本是继承RecylerView.Adapter且具有泛型，后来我们自己写了一个ViewHolder
public  class HorAdapter extends RecyclerView.Adapter<HorAdapter.LinearViewHodler> {

    private Context mContext;
    //自制接口实现点击事件
    private OnItemClickListener mListener;

    public HorAdapter(Context context, OnItemClickListener listener){
        this.mContext = context;
        this.mListener = listener;
    }
    @Override
    public HorAdapter.LinearViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        //传入一个view 在什么地方显示出对于的内容
        //不是传入activity_hor_re_view
        return new LinearViewHodler(LayoutInflater.from(mContext).inflate(R.layout.activity_hor_rv_item,parent,false));
    }

    @Override
    public void onBindViewHolder (HorAdapter.LinearViewHodler holder, @SuppressLint("RecyclerView") int position) {
        holder.textView.setText("hello");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //利用接口的方法把位置传进来-创建对象调用方法，获取参数
                mListener.OnClick(position);
            }
        });
    }

    //正常工程下用list<String>装一个可变的长度
    @Override
    public int getItemCount() {
        return 30;
    }


    //自己写一个Hodlerr然后继承ViewHolder，然后可以把自己需要的内容放入进来
    class LinearViewHodler extends RecyclerView.ViewHolder{
        private TextView textView;
        public LinearViewHodler( View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_rv_hor);
        }
    }
    public interface OnItemClickListener{
        void OnClick(int pos);
    }
}
/*
     1 写了一个接口OnItemClickListener并在里面写了一个OnClick的方法，创建mListener的对象，并调用接口里面的方法
     2 在onBindViewHolder里面设计了点击事件，因为需要传入一个View v所以需要在Linear_Rv_Activity得到一个v，
     又因为点击事件里面默认有一个参数position，因此创建了mListener的对象，调用了OnClick的方法，获得了position
     3 在Linear_Rv_Activity重写了OnClick方法，然后利用Toast方法显示内容

     总之写这个接口是为了得到位置参数position


 */