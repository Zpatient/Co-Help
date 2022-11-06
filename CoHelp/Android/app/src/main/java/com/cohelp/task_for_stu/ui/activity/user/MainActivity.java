package com.cohelp.task_for_stu.ui.activity.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cohelp.task_for_stu.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button b1,b2,b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=(Button) findViewById(R.id.b1);
        b2=(Button) findViewById(R.id.b2);
        b3=(Button) findViewById(R.id.b3);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if (v.getId()==R.id.b1){
            intent.setClass(this,ActSummaryActivity.class);
        }
        else if(v.getId()== R.id.b2){
            intent.setClass(this,MutualSummaryActivity.class);
        }
        else if(v.getId()==R.id.b3){
            intent.setClass(this,TreeholeSummaryActivity.class);
        }
        startActivity(intent);
    }
}