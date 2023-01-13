package com.example.mybutton.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.mybutton.R;
import com.example.mybutton.util.ToastUtil;

public class ProgressActivity extends AppCompatActivity {

    private ProgressBar mPb4;
    private Button mBt1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        mPb4 = findViewById(R.id.pb_4);
        mBt1 = findViewById(R.id.bt_pb1);
        mBt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(0);
            }
        });
    }



    Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mPb4.getProgress()<100){
                handler.postDelayed(runnable,500);
            }else {
                ToastUtil.showMsg(ProgressActivity.this,"加载完成");
            }
        }
    };
//
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mPb4.setProgress(mPb4.getProgress()+5);
            handler.sendEmptyMessage(0);
        }
    };
}