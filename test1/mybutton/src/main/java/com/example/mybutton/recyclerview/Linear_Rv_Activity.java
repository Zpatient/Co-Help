package com.example.mybutton.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mybutton.R;

public class Linear_Rv_Activity extends AppCompatActivity {


    private RecyclerView mRvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_rv);
        mRvMain = findViewById(R.id.rv_linear);
        //设置线性管理器
        mRvMain.setLayoutManager(new LinearLayoutManager(Linear_Rv_Activity.this));
        //绘制分割线
        //利用自带的实体类
        DividerItemDecoration dec = new DividerItemDecoration(Linear_Rv_Activity.this,DividerItemDecoration.VERTICAL);
//        dec.setDrawable(new ColorDrawable(ContextCompat.getColor(Linear_Rv_Activity.this,R.color.teal_700)));  //有点问题
        mRvMain.addItemDecoration(dec);

        //利用内置方法-不推荐
//        mRvMain.addItemDecoration(new MyDecoration());


        //设置适配器:LinearAdapter-仅显示文字  LinearAdapter1-显示文字和图片
        mRvMain.setAdapter(new LinearAdapter1(Linear_Rv_Activity.this, new LinearAdapter1.OnItemClickListener() {
            //此时传入的参数有2个了，需要设置点击事件
            @Override
            public void OnClick(int pos) {
                Toast.makeText(Linear_Rv_Activity.this,"Click"+pos,Toast.LENGTH_SHORT).show();
            }
        }));
    }
    //法二：
    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0,0,0,getResources().getDimensionPixelOffset(R.dimen.rv_dimen));
        }
    }

}