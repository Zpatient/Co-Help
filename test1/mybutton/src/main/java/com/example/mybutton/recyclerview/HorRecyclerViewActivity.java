package com.example.mybutton.recyclerview;

import static androidx.recyclerview.widget.LinearLayoutManager.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mybutton.R;

public class HorRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRvHor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hor_recycler_view);
        mRvHor = findViewById(R.id.rv_hor);
        //设置管理器-水平
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HorRecyclerViewActivity.this);
        linearLayoutManager.setOrientation(HORIZONTAL);
        mRvHor.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dec = new DividerItemDecoration(HorRecyclerViewActivity.this,DividerItemDecoration.VERTICAL);
        mRvHor.addItemDecoration(dec);
        mRvHor.setAdapter(new HorAdapter(HorRecyclerViewActivity.this, new HorAdapter.OnItemClickListener() {
            @Override
            public void OnClick(int pos) {
                Toast.makeText(HorRecyclerViewActivity.this,"Click"+pos,Toast.LENGTH_SHORT).show();
            }
        }));
    }
}