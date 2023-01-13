package com.example.mybutton.recyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mybutton.R;

public class RecylerViewActivity extends AppCompatActivity {

    private Button myRv_ln,myRv_hor,myRV_grid,myRv_pu;
    //创建监听和点击事件，跳转到Linear_Rv_Activity里面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler_viewctivity);
        myRv_ln = findViewById(R.id.bt_linear);
        myRv_hor = findViewById(R.id.bt_hor);
        myRV_grid = findViewById(R.id.bt_grid);
        myRv_pu = findViewById(R.id.bt_pu);



        myRv_ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecylerViewActivity.this,Linear_Rv_Activity.class);
                startActivity(intent);
            }
        });

        myRv_hor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecylerViewActivity.this,HorRecyclerViewActivity.class);
                startActivity(intent);
            }
        });

        myRV_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecylerViewActivity.this,GridRecyclerViewActivity.class);
                startActivity(intent);
            }
        });

        myRv_pu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecylerViewActivity.this,PuRecyclerViewActivity.class);
                startActivity(intent);
            }
        });

    }
}