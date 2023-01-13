package com.example.mybutton.listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mybutton.R;

public class ListViewActivity extends Activity {

    private ListView mylv1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        mylv1 = findViewById(R.id.lv_1);
        mylv1.setAdapter(new MyListAdapter(ListViewActivity.this));

        //点击事件
        mylv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListViewActivity.this,"点击"+position,Toast.LENGTH_SHORT).show();
            }
        });

        //长按事件
        mylv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListViewActivity.this,"长按"+position,Toast.LENGTH_SHORT).show();
                //ture为执行完不会再执行点击事件了
                return false;


            }
        });


    }
}
