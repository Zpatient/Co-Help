package com.example.mybutton.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mybutton.R;

public class EditTextActivity extends AppCompatActivity {

    private Button mybtlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        mybtlogin = findViewById(R.id.et_3);
        mybtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditTextActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
            }
        });
    }
}