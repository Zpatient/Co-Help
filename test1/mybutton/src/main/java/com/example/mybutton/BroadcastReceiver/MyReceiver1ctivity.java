package com.example.mybutton.BroadcastReceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mybutton.R;

public class MyReceiver1ctivity extends AppCompatActivity {
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_receiver1ctivity);
       intentFilter = new IntentFilter();
       intentFilter.addAction("android,net.com.CONNECTIVITY_CHANGE");
       networkChangeReceiver = new NetworkChangeReceiver();
       registerReceiver(networkChangeReceiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    class NetworkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "networ change", Toast.LENGTH_SHORT).show();
            Log.d("NetworkChangeReceiver","断网");
        }
    }
}