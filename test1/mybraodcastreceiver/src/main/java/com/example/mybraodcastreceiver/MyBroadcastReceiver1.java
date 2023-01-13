package com.example.mybraodcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


//第一步-建立静态接收者
public class MyBroadcastReceiver1 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "receiver in broadcastreceiver", Toast.LENGTH_SHORT).show();
        Log.d("onReceive", "onReceive: 接收到了");
    }
}
