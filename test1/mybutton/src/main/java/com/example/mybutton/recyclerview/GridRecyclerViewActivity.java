package com.example.mybutton.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.example.mybutton.R;

public class GridRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRvGrid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_recycler_view);
        mRvGrid = findViewById(R.id.rv_grid);
        mRvGrid.setLayoutManager(new GridLayoutManager(GridRecyclerViewActivity.this,6));//3列
        mRvGrid.setAdapter(new GridAdapter(GridRecyclerViewActivity.this, new GridAdapter.OnItemClickListener() {
            @Override
            public void OnClick(int pos) {
                Toast.makeText(GridRecyclerViewActivity.this,"Click"+pos,Toast.LENGTH_SHORT).show();
            }
        }));
    }
}