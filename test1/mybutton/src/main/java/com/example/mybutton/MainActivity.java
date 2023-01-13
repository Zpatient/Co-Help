package com.example.mybutton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mybutton.Activity_study.AActivity;
import com.example.mybutton.Fragment.ContainerActivity;
import com.example.mybutton.Fragment.Fragment1.Container_cdActivity;
import com.example.mybutton.Fragment.Fragment_view_pager.ViewPagerFragmentActivity;
import com.example.mybutton.Fragment.Fragment_view_pager1.BottomNagActivity;
import com.example.mybutton.UI.UIActivity;

public class MainActivity extends AppCompatActivity {

    private Button myUI;
    private Button myAt;
    private Button myFg;
    private Button myFg1;
    private Button myFV1;
    private Button myFV2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myUI = findViewById(R.id.bt_ui);
        myAt = findViewById(R.id.bt_activity);
        myFg = findViewById(R.id.bt_fragment);
        myFg1= findViewById(R.id.bt_fragment1);
        myFV1 = findViewById(R.id.bt_fragment_viewpage);
        myFV2 = findViewById(R.id.bt_fragment_viewpage1);


        OnClick onClick = new OnClick();
        myUI.setOnClickListener(onClick);
        myAt.setOnClickListener(onClick);
        myFg.setOnClickListener(onClick);
        myFg1.setOnClickListener(onClick);
        myFV1.setOnClickListener(onClick);
        myFV2.setOnClickListener(onClick);
    }


    class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){
                case R.id.bt_ui:
                    intent = new Intent(MainActivity.this, UIActivity.class);
                    break;
                case R.id.bt_activity:
                    intent = new Intent(MainActivity.this, AActivity.class);
                    break;
                case R.id.bt_fragment:
                    intent = new Intent(MainActivity.this, ContainerActivity.class);
                    break;
                case R.id.bt_fragment1:
                    intent = new Intent(MainActivity.this, Container_cdActivity.class);
                    break;
                case R.id.bt_fragment_viewpage:
                    intent = new Intent(MainActivity.this, ViewPagerFragmentActivity.class);
                    break;
                case R.id.bt_fragment_viewpage1:
                    intent = new Intent(MainActivity.this, BottomNagActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}