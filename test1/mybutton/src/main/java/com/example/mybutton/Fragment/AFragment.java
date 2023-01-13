package com.example.mybutton.Fragment;

import static android.os.Build.VERSION_CODES.R;

import static com.example.mybutton.R.*;
import static com.example.mybutton.R.id.bt_massege;
import static com.example.mybutton.R.id.tv_fragment_a;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mybutton.R;


public class AFragment extends Fragment {

    private TextView mTvFga;
    private Button mBtnChange,mBtnReset,mBtnMassege;
    private BFragment bFragment;
    private IOnMessageClick listener;



//    public AFragment() {
//
//    }
//
//    public AFragment(TextView mTvFga, String mTvTitle) {
//        this.mTvFga = mTvFga;
//        this.mTvTitle = mTvTitle;
//    }
//
//    public AFragment(int contentLayoutId, String mTvTitle) {
//        super(contentLayoutId);
//        this.mTvFga = mTvFga;
//        this.mTvTitle = mTvTitle;
//    }


    //通过bundle的方式来传递参数
    public static AFragment getInstance(String title){
        AFragment fragment = new AFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        fragment.setArguments(bundle);
        return fragment;
    }


    //通过接口来实现活动向碎片传递信息
    public interface IOnMessageClick{
        void onClick(String text);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(layout.activity_fragment_a,container,false);
        Log.d("AFragment","-----onCreateView----");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvFga = view.findViewById(tv_fragment_a);

        //向活动发送文字
        mBtnMassege = view.findViewById(bt_massege);
        mBtnMassege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这样写，当找不到活动时会报错
//                ((ContainerActivity)getActivity()).setData("你好呀");
                //发送文字
                listener.onClick("你好,我是新文字");
            }
        });



        //替换碎片
        mBtnChange = view.findViewById(id.bt_change);
        mBtnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bFragment == null) {
                    bFragment = new BFragment();

//                    Fragment fragment = getFragmentManager().findFragmentById(aFragment);----有问题
                    Fragment fragment = getFragmentManager().findFragmentByTag("a");//实际上就是判断Fragment_a是否存在，用一个teg值去判断
                    if (fragment != null) {
                        getFragmentManager().beginTransaction().hide(fragment).add(com.example.mybutton.R.id.fl_container,bFragment).addToBackStack(null).commitAllowingStateLoss();
                    }else {
                        //添加到返回栈中-addToBackStack
                        getFragmentManager().beginTransaction().replace(com.example.mybutton.R.id.fl_container,bFragment).addToBackStack(null).commitAllowingStateLoss();
                    }

                }
            }
        });

        //更改内容
        mBtnReset = view.findViewById(id.bt_reset);
        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvFga.setText("我是新文字");
            }
        });
        //判断getarguement--获取参数
        if (getArguments() != null) {
            mTvFga.setText(getArguments().getString("title"));
        }

    }

    //当活动销毁时，碎片和活动的依赖关系就解除了
    @Override
    public void onDetach() {
        super.onDetach();
    }
    //实现碎片和活动的联系
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (IOnMessageClick) context;
        }catch (ClassCastException e){
            throw new ClassCastException("活动必须实现接口");
        }

    }

}
