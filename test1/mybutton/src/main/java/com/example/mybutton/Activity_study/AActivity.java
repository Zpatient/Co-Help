package com.example.mybutton.Activity_study;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mybutton.R;





@SuppressWarnings("deprecation")
public class AActivity extends AppCompatActivity {

    private Button mBtnA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aactivity);
        mBtnA = findViewById(R.id.bt_a);
        mBtnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AActivity.this,BActivity.class);


                //法一通过bundle传递一些数据
                Bundle bundle = new Bundle();
                bundle.putString("name","何江");
                bundle.putInt("age",22);
                intent.putExtras(bundle);


                //法二通过Serializable传对象
                Student student = new Student();
                student.id = 123;
                student.name = "hejiang";
                student.age = 22;
                intent.putExtra("Student",student);


                //法三Parcelable传对象
                Student1 student1 = new Student1();
                student1.age = 21;
                student1.name = "zqw";
                intent.putExtra("student1",student1);

//                startActivity(intent);
                startActivityForResult(intent,0);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(AActivity.this,data.getExtras().getString("message"),Toast.LENGTH_SHORT).show();
    }
}