package com.example.mybutton.Fragment.Fragment1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mybutton.R;

public class CFragment extends Fragment {


    private View view;
   // private Button mBtnFc;

    public CFragment() {
        // Required empty public constructor
    }
    //创建接口对象
    private IFragmentCallBack fragmentCallBack;
    //为了能够让活动对接口赋值，则定义一个方法，让活动调用此方法进行赋值--活动里面有创建碎片，碎片可以调用这个方法，然后对接口里面的方法进行重写
    //定义一个方法是为了让呈递一个接口类型的对象，接口类型对象的值在活动里面，需要在活动里面调用setFragmentCallBack
    public void setFragmentCallBack(IFragmentCallBack callBack){
        fragmentCallBack = callBack;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        String string = bundle.getString("message");
        Log.d("onCreate","onCreate"+string);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        if (view == null) {
//            view = inflater.inflate(R.layout.fragment_c, container, false);
//        }
        view = inflater.inflate(R.layout.fragment_c, container, false);
       Button mBtnFc = view.findViewById(R.id.bt_fragment_c);
        mBtnFc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //发送
//                fragmentCallBack.sendMsgToActivity("向活动发送文字");
                //接收
                String Msg = fragmentCallBack.getMsgFromActivity("接收活动的文字");
                Toast.makeText(CFragment.this.getContext(), Msg, Toast.LENGTH_SHORT).show();//注意要是getContext，因为这个是碎片本身不是活动文本
            }
        });
        return view;
    }
}