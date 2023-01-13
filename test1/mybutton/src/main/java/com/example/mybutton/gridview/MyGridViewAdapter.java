package com.example.mybutton.gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mybutton.R;

public class MyGridViewAdapter extends BaseAdapter {

    private Context mycontext;
    private LayoutInflater myLayoutInflater;

    public MyGridViewAdapter(Context context){
        this.mycontext = context;
        this.myLayoutInflater = LayoutInflater.from(context);

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

    public static class ViewHolder{
        public ImageView imageView;
        public TextView textView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView==null){
            convertView = myLayoutInflater.inflate(R.layout.activity_grid_view_adapter,null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.iv_gv_image);
            holder.textView = convertView.findViewById(R.id.iv_gv_title);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText("花");
        Glide.with(mycontext).load("https://pics2.baidu.com/feed/0dd7912397dda144e41dce0973b91ba40cf4860e.jpeg?token=6823f1f554a7c02c2b55757553e70801").into(holder.imageView);
        return convertView;
    }
}
