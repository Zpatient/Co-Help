package com.cohelp.task_for_stu.ui.activity.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.cohelp.task_for_stu.R;


public class MutualReleaseActivity extends AppCompatActivity implements View.OnClickListener{
    private Button release;
    private ImageButton report;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutual_release);
        release = (Button) findViewById(R.id.button_MutRel);
        //report = (ImageButton)findViewById(R.id.imageButton_Report);
//        release.setOnClickListener(this);
//        report.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

    }
}