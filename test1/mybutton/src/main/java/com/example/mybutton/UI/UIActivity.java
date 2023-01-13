package com.example.mybutton.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mybutton.R;
import com.example.mybutton.gridview.GridViewActivity;
import com.example.mybutton.listview.ListViewActivity;
import com.example.mybutton.recyclerview.RecylerViewActivity;

public class UIActivity extends AppCompatActivity {

    private Button myBt1;//定义
    private Button myEt1;
    private Button myRBt;
    private Button myCB1;
    private Button myIV1;
    private Button myLv1;
    private Button myGv1;
    private Button myRv1;
    private Button myWv1;
    private Button myTs1;
    private Button myDl1;
    private Button myPb1;
    private Button myCd1;
    private Button myPw1;
    private Button myVp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
//        Log.d("MainActivity","onCreate execute");
        myBt1 = findViewById(R.id.bt_1);//查找
        myBt1.setOnClickListener(new View.OnClickListener() {  //设置点击事件
            @Override
            public void onClick(View view) {
                //跳转到TextView
                Intent intent = new Intent(UIActivity.this, TextViewActivity.class);
                startActivity(intent);
            }
        });

        myEt1 = findViewById(R.id.et_1);
        myEt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UIActivity.this, EditTextActivity.class);
                startActivity(intent);
            }
        });


        myRBt = findViewById(R.id.bt_2);
        myCB1 = findViewById(R.id.bt_checkbox);
        myIV1 = findViewById(R.id.bt_imageview);
        myLv1 = findViewById(R.id.bt_listview);
        myGv1 = findViewById(R.id.bt_gridview);
        myRv1 = findViewById(R.id.bt_recyclerview);
        myWv1 = findViewById(R.id.bt_webview);
        myTs1 = findViewById(R.id.bt_toast);
        myDl1 = findViewById(R.id.bt_dialog);
        myPb1 = findViewById(R.id.bt_progress);
        myCd1 = findViewById(R.id.bt_customdialog);
        myPw1 = findViewById(R.id.bt_popupwindow);
        myVp1 = findViewById(R.id.bt_viewpager);

        setListeners();
    }

    //法二-利用方法实现接口，然后设置一个监听事件，接收到点击事件
    private  void setListeners(){
        OnClick onClick = new OnClick();
        myRBt.setOnClickListener(onClick);
        myCB1.setOnClickListener(onClick);
        myIV1.setOnClickListener(onClick);
        myLv1.setOnClickListener(onClick);
        myGv1.setOnClickListener(onClick);
        myRv1.setOnClickListener(onClick);
        myWv1.setOnClickListener(onClick);
        myTs1.setOnClickListener(onClick);
        myDl1.setOnClickListener(onClick);
        myPb1.setOnClickListener(onClick);
        myCd1.setOnClickListener(onClick);
        myPw1.setOnClickListener(onClick);
        myVp1.setOnClickListener(onClick);

    }
    private class OnClick implements View.OnClickListener{
        public void onClick(View v){
            Intent intent = null;
            switch (v.getId()){
                case R.id.bt_1:
                    intent = new Intent(UIActivity.this, TextViewActivity.class);
                    break;
                case R.id.et_1:
                    intent = new Intent(UIActivity.this, EditTextActivity.class);
                    break;

                case R.id.bt_2:
                    intent = new Intent(UIActivity.this, RadioButtonActivity.class);
                    break;

                case R.id.bt_checkbox:
                    intent = new Intent(UIActivity.this, CheckBoxActivity.class);
                    break;
                case R.id.bt_imageview:
                    intent = new Intent(UIActivity.this, ImageViewActivity.class);
                    break;
                case R.id.bt_listview:
                    intent = new Intent(UIActivity.this, ListViewActivity.class);
                    break;
                case R.id.bt_gridview:
                    intent = new Intent(UIActivity.this, GridViewActivity.class);
                    break;
                case R.id.bt_recyclerview:
                    intent = new Intent(UIActivity.this, RecylerViewActivity.class);
                    break;
                case R.id.bt_webview:
                    intent = new Intent(UIActivity.this, WebViewActivity.class);
                    break;
                case R.id.bt_toast:
                    intent = new Intent(UIActivity.this, ToastActivity.class);
                    break;
                case R.id.bt_dialog:
                    intent = new Intent(UIActivity.this, DialogActivity.class);
                    break;
                case R.id.bt_progress:
                    intent = new Intent(UIActivity.this, ProgressActivity.class);
                    break;
                case R.id.bt_customdialog:
                    intent = new Intent(UIActivity.this, CustomDialogActivity.class);
                    break;
                case R.id.bt_popupwindow:
                    intent = new Intent(UIActivity.this, PopupWindowActivity.class);
                    break;
                case R.id.bt_viewpager:
                    intent = new Intent(UIActivity.this, ViewPagerActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}