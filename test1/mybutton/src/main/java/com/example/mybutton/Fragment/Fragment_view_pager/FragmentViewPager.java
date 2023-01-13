package com.example.mybutton.Fragment.Fragment_view_pager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mybutton.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentViewPager#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentViewPager extends Fragment {

    /*
        利用Bundle将传入的数据适配到每个碎片布局上
        1  Bundle 接收数据
        2  inflater解析需要在哪个布局上显示
        3  设置一个方法，用于将接收到的数据设置在textview中显示出来
     */



    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mTextFragment;

    public FragmentViewPager() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment FragmentViewPager.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentViewPager newInstance(String param1) {
        FragmentViewPager fragment = new FragmentViewPager();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTextFragment = getArguments().getString(ARG_PARAM1);
        }
        Log.d("onCreate","调用适配器里的oncreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //用if判断是为了防止布局已经解析过了造成再次解析导致资源浪费，所以在这里判断一下

        if(view == null){
             view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        }
        initView();
        Log.d("onCreateview","调用适配器里的oncreateview");
        return view;

    }

    private void initView() {

        TextView textView = view.findViewById(R.id.tv_fragment_viewpager);
        textView.setText(mTextFragment);
        Log.d("initView","调用适配器里的initView");
    }
}