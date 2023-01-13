package com.example.mybraodcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //第二步-发送给静态接收者
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.bt_bcr);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.example.broadcast.MY_BROADCAST");
                sendBroadcast(intent);
                Log.d("onClick", "onClick: 点击发送");
            }
        });




        //第一步建立动态发送者
        Button button1 = findViewById(R.id.bt_bcr1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.example.broadcast.MY_BROADCAST_DONGTAI");
                sendBroadcast(intent);
            }
        });

        //第二步建立动态接收者
        MyBroadcastReceiver2 receiver2 = new MyBroadcastReceiver2();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.broadcast.MY_BROADCAST_DONGTAI");
        registerReceiver(receiver2,filter);


    }
}