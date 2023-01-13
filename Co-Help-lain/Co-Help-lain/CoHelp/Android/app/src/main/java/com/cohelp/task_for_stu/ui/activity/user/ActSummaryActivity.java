package com.cohelp.task_for_stu.ui.activity.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.cohelp.task_for_stu.R;

public class ActSummaryActivity extends AppCompatActivity implements View.OnClickListener {
    private Button release;
    private ImageButton report;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_summary);
        release = (Button) findViewById(R.id.button_TreeRel);
        report = (ImageButton)findViewById(R.id.imageButton_Report);
        release.setOnClickListener(this);
        report.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        Intent intent = new Intent();
        if (v.getId()==R.id.button_TreeRel){
            intent.setClass(ActSummaryActivity.this,EventReleaseActivity.class);
        }
        else if(v.getId()==R.id.imageButton_Report){
            intent.setClass(ActSummaryActivity.this,ReportActivity.class);
        }
        startActivity(intent);
    }
}