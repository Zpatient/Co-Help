package com.example.mybutton.gridview;

import android.content.Context;
import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mybutton.R;
import com.example.mybutton.listview.MyListAdapter;

public class GridViewActivity extends AppCompatActivity {

    private GridView myGV1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        myGV1 = findViewById(R.id.gv_1);
        myGV1.setAdapter(new MyListAdapter(GridViewActivity.this));
    }
}
