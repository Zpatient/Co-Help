package com.example.glidedemo;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.iv_1);

        //过渡显示
        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();


        Glide.with(this)
                .load("https://img.wxcha.com/m00/b0/94/6b74cf0e11b9ede2c213b48eefe062f0.jpg")
                .placeholder(R.drawable.ic_baseline_loop_24)
                .error(R.drawable.ic_baseline_error_outline_24)
                .fallback(R.drawable.ic_baseline_done_24)
                .override(200,200)
                .transition(DrawableTransitionOptions.withCrossFade(factory))
                .transform(new CircleCrop())
                .into(imageView);
    }
}