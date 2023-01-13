package com.example.mybutton.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybutton.R;

public class PuRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRvPu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pu_recycler_view);
        mRvPu = findViewById(R.id.rv_pu);
        //瀑布式的视图  2-表示2列
        mRvPu.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
//        DividerItemDecoration dec = new DividerItemDecoration(PuRecyclerViewActivity.this,DividerItemDecoration.VERTICAL);
//
        mRvPu.addItemDecoration(new MyDecoration());

        mRvPu.setAdapter(new StaggeredGridAdapter(PuRecyclerViewActivity.this, new StaggeredGridAdapter.OnItemClickListener() {
            @Override
            public void OnClick(int pos) {
                Toast.makeText(PuRecyclerViewActivity.this,"Click"+pos,Toast.LENGTH_SHORT).show();
            }
        }));

    }
    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int gap = getResources().getDimensionPixelOffset(R.dimen.rv_dimen1);
            outRect.set(gap,gap,gap,gap);
        }
    }
}