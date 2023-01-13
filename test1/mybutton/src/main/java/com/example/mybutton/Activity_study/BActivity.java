package com.example.mybutton.Activity_study;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mybutton.R;

public class BActivity extends AppCompatActivity {

    private TextView mTvb;
    private Button mBtnb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bactivity);

        //法一：通过bundle传递
        mTvb = findViewById(R.id.tv_b);
        mBtnb = findViewById(R.id.bt_b);

        //获取A传过来的内容
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        int age = bundle.getInt("age");

        mTvb.setText(name+" "+age);

        //返回给A的内容
        mBtnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle1 = new Bundle();
                bundle1.putString("message","我回来了");
                intent.putExtras(bundle1);
                setResult(AActivity.RESULT_OK,intent);
                finish();
            }
        });


        //法二：通过Serializable接口实现传递对象
        Intent intent =  getIntent();
        Student student = (Student) intent.getSerializableExtra("Student");
        Toast.makeText(this, "student.id" + student.id
                        + "student.name" + student.name
                        + "student.age" + student.age
                , Toast.LENGTH_SHORT).show();

        //法三：通过Parcelable接口实现传递对象
        Intent intent1 = getIntent();
        Student1 student1 = intent.getParcelableExtra("student1");
        Toast.makeText(this, "student_age" + student1.age + "student_namne" + student1.name, Toast.LENGTH_SHORT).show();


    }
}