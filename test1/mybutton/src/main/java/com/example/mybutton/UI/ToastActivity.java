package com.example.mybutton.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybutton.R;
import com.example.mybutton.util.ToastUtil;

public class ToastActivity extends AppCompatActivity {

    private Button mBtnToast1,mBtnToast2,mBtnToast3,mBtnToast4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
        mBtnToast1 = findViewById(R.id.bt_toast1);
        mBtnToast2 = findViewById(R.id.bt_toast2);
        mBtnToast3 = findViewById(R.id.bt_toast3);
        mBtnToast4 = findViewById(R.id.bt_toast4);
        OnClick onClick = new OnClick();
        mBtnToast1.setOnClickListener(onClick);
        mBtnToast2.setOnClickListener(onClick);
        mBtnToast3.setOnClickListener(onClick);
        mBtnToast4.setOnClickListener(onClick);
    }

    class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_toast1:
                    Toast.makeText(getApplicationContext(),"Toast",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.bt_toast2:
                    Toast toastcenter = Toast.makeText(getApplicationContext(),"居中",Toast.LENGTH_SHORT);
                    toastcenter.setGravity(Gravity.CENTER,0,0);
                    toastcenter.show();
                    break;
                case R.id.bt_toast3:
                    //创建对象
                    Toast toastcustom = new Toast(getApplicationContext());
                    //调用inflater
                    LayoutInflater inflater = LayoutInflater.from(ToastActivity.this);
                    //找到需要加载到哪里的布局
                    View view = inflater.inflate(R.layout.activity_toast_image,null);
                    //找到需要加载到哪里的view--注意是view下面用find，直接找不到
                    ImageView imageView =view.findViewById(R.id.iv_toast);
                    TextView textView = view.findViewById(R.id.tv_toast);
                    //设置需要加载的图片
                    imageView.setImageResource(R.drawable.ic_baseline_check_box_24);
                    //设置文字和图片
                    textView.setText("自定义Toast");
                    toastcustom.setView(view);
                    //显示
                    toastcustom.show();
                    break;
                case R.id.bt_toast4:
                    ToastUtil.showMsg(getApplicationContext(),"自定义");
                    break;
            }
        }
    }
}