package com.example.mybutton.Fragment.Fragment1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mybutton.R;

public class Container_cdActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnchange,mBtnreplace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_cd);
        mBtnchange = findViewById(R.id.bt_change);
        mBtnreplace = findViewById(R.id.bt_replace);
        mBtnchange.setOnClickListener(this);
        mBtnreplace.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_change:
                //法1直接发送，利用之前说的getactivity
                Bundle bundle = new Bundle();
                bundle.putString("message","活动向碎片发送文字");
                //创建碎片
                CFragment cfragment = new CFragment();
                //传入bunlde
                cfragment.setArguments(bundle);
                //利用匿名内部类实例化
                cfragment.setFragmentCallBack(new IFragmentCallBack() {
                    @Override
                    public void sendMsgToActivity(String Msg) {
                        Toast.makeText(Container_cdActivity.this,Msg,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public String getMsgFromActivity(String Msg) {
                        return "接收";
                    }
                });
                //之前是new CFragment()--现在直接改成cfragment不然会报错
                replaceFragment(cfragment);
                break;
            case R.id.bt_replace:
                replaceFragment(new ItemFragment());
                break;

        }
    }


    //动态切换fragment
    private void replaceFragment(Fragment fragment) {
        //fragment管理器
        FragmentManager fragmentManager = getSupportFragmentManager();
        //触发器
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //事件提交
        transaction.replace(R.id.fragmelayout,fragment);
        //加入返回栈--默认为null
        transaction.addToBackStack(null);

        //事件执行
        transaction.commitAllowingStateLoss();


    }
}