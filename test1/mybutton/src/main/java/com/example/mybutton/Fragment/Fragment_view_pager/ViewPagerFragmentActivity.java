package com.example.mybutton.Fragment.Fragment_view_pager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mybutton.R;

import java.util.ArrayList;

public class ViewPagerFragmentActivity extends AppCompatActivity implements View.OnClickListener {


    ViewPager2 viewPager2;

    private LinearLayout llChat,llContact,llFind,llProfile;
    private ImageView ivChat,ivContact,ivFind,ivProfile,ivCurrent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_fragment);
        Log.d("onCreate","创建成功");
        initPager();
        initView();


    }

    private void initView() {
        llChat = findViewById(R.id.ln_tab_weixin);
        llChat.setOnClickListener(this);
        llContact = findViewById(R.id.ln_tab_contact);
        llContact.setOnClickListener(this);
        llFind = findViewById(R.id.ln_tab_find);
        llFind.setOnClickListener(this);
        llProfile = findViewById(R.id.ln_tab_profile);
        llProfile.setOnClickListener(this);

        ivChat = findViewById(R.id.iv_tab_weixin);
        ivContact = findViewById(R.id.iv_tab_contact);
        ivFind = findViewById(R.id.iv_tab_find);
        ivProfile = findViewById(R.id.iv_tab_profile);

        ivChat.setSelected(true);
        ivCurrent = ivChat;
    }


    private void initPager() {
        viewPager2 = findViewById(R.id.viewpagerfragment);
        Log.d("initPager","调用InitPager");
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(FragmentViewPager.newInstance("微信聊天"));
        fragments.add(FragmentViewPager.newInstance("通讯录"));
        fragments.add(FragmentViewPager.newInstance("发现"));
        fragments.add(FragmentViewPager.newInstance("我"));
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        viewPager2.setAdapter(myFragmentPagerAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                initChange(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    private void initChange(int position) {
        ivCurrent.setSelected(false);
        switch (position){
            case R.id.ln_tab_weixin:
                //用来点击按钮后页面也跟着变化
                viewPager2.setCurrentItem(0);
            case 0:
                //用来判断当前点击的界面和灭掉其他界面
                ivChat.setSelected(true);
                ivCurrent = ivChat;
                break;
            case R.id.ln_tab_contact:
                viewPager2.setCurrentItem(1);
            case 1:
                ivContact.setSelected(true);
                ivCurrent = ivContact;
                break;
            case R.id.ln_tab_find:
                viewPager2.setCurrentItem(2);
            case 2:
                ivFind.setSelected(true);
                ivCurrent = ivFind;
                break;
            case R.id.ln_tab_profile:
                viewPager2.setCurrentItem(3);
            case 3:
                ivProfile.setSelected(true);
                ivCurrent = ivProfile;
                break;
        }

    }

    @Override
    public void onClick(View v) {
        initChange(v.getId());
    }
}