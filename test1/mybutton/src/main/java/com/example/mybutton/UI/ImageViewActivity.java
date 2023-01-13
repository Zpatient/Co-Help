package com.example.mybutton.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mybutton.R;

import java.io.File;


public class ImageViewActivity extends AppCompatActivity {

    private ImageView myIv3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        myIv3 = findViewById(R.id.iv_3);
        Glide.with(this).load("http://dss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2246271396,3843662332&fm=85&app=79&f=JPG?w=121&h=75&s=39C718720E8EBE011B398BAC0300F024").error(R.drawable.ic_baseline_check_box_24).into(myIv3);
      //  Glide.with(this).load(Uri.fromFile(new File("C:/Users/86176/Pictures/Saved Pictures/qianqian1.jgp"))).error(R.drawable.ic_baseline_check_box_24).into(myIv3);
    }
}