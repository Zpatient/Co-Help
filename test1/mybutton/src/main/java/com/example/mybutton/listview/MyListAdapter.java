package com.example.mybutton.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mybutton.R;

public class MyListAdapter extends BaseAdapter {


    private Context myContext;
    private LayoutInflater mLayoutInflater;

    public MyListAdapter(Context context){
        this.myContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //创建一个类
    public static class ViewHolder{
        public ImageView imageView;
        public TextView tvTitle,tvTime,tvContent;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.activity_list_adapter,null);

            //实例化
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.iv_lv_1);
            holder.tvTitle = convertView.findViewById(R.id.tv_title_lv);
            holder.tvTime = convertView.findViewById(R.id.tv_time_lv);
            holder.tvContent = convertView.findViewById(R.id.tv_content_lv);
            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();

        }
        //这里写的文字是最终显示的，而在前面布局的时候只是为了方便预览位置
        holder.tvTitle.setText("这是标题");
        holder.tvTime.setText("这是时间");
        holder.tvContent.setText("这是内容");
        Glide.with(myContext).load("https://dss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2246271396,3843662332&fm=85&app=79&f=JPG?w=121&h=75&s=39C718720E8EBE011B398BAC0300F024").error(R.drawable.ic_baseline_check_box_24).into(holder.imageView);

        return convertView;
    }
}
