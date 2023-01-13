package com.example.mybutton.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mybutton.R;

public class RadioButtonActivity extends AppCompatActivity {
    //定义变量
    private RadioGroup myRg1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_button);
        //寻找变量
        myRg1 = findViewById(R.id.rg_1);
        //设置监听事件
        myRg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            //方法重写
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //找到变换的id
                RadioButton radioButton = radioGroup.findViewById(i);
                //显示字符
                Toast.makeText(RadioButtonActivity.this,radioButton.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}