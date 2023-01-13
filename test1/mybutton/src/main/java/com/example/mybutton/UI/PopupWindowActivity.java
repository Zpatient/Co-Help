package com.example.mybutton.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.mybutton.R;
import com.example.mybutton.util.ToastUtil;

public class PopupWindowActivity extends AppCompatActivity {

    private Button mBtPw;
    private PopupWindow mPop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);
        mBtPw = findViewById(R.id.bt_pw);
        mBtPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取需要展示的view
                View view = getLayoutInflater().inflate(R.layout.activity_popup_window_item,null);
                //设置pop的长宽
                mPop = new PopupWindow(view,mBtPw.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
                //点击旁边能够退出选项
                mPop.setOutsideTouchable(true);
                //再次点击则会消失
                mPop.setFocusable(true);
                //在mBtpw下面显示--必须设置在所有的方法下面，否则其他方法会失效
                mPop.showAsDropDown(mBtPw);

                TextView textView = view.findViewById(R.id.tv_good);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //加逻辑
                        mPop.dismiss();
                        ToastUtil.showMsg(PopupWindowActivity.this,"好");
                    }
                });

                TextView textView1 = view.findViewById(R.id.tv_normal);
                textView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //加逻辑
                        mPop.dismiss();
                        ToastUtil.showMsg(PopupWindowActivity.this,"还行");
                    }
                });
                TextView textView2 = view.findViewById(R.id.tv_bad);
                textView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //加逻辑
                        mPop.dismiss();
                        ToastUtil.showMsg(PopupWindowActivity.this,"不好");
                    }
                });
            }
        });
    }
}