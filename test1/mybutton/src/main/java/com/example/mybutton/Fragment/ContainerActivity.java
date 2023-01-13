package com.example.mybutton.Fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mybutton.R;

public class ContainerActivity extends AppCompatActivity implements AFragment.IOnMessageClick {

    private AFragment aFragment;
    private Button mBtnChange;
    private BFragment bFragment;
    private TextView mTvFM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        mTvFM = findViewById(R.id.tv_fragment_message);
        //创建对象


        aFragment = AFragment.getInstance("我是参数");

        //把AFragment加载到Activity中，注意要调用commit这样可以避免一些错误
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container,aFragment,"a").commitAllowingStateLoss();


    }

    //活动和碎片的交互

    //法一：通过在活动里写一个方法，然后通过碎片a进行调用传文字
    public void setData(String text){
        mTvFM.setText(text);

    }


    //法二：通过实现接口里面的方法来进行传递
    @Override
    public void onClick(String text) {
        mTvFM.setText(text);
    }
}