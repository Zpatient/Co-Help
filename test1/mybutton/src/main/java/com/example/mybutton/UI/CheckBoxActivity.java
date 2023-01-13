package com.example.mybutton.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.mybutton.R;

public class CheckBoxActivity extends AppCompatActivity {

    private CheckBox mycb4,mycb5,mycb6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box);
        mycb4 = findViewById(R.id.cb_4);
        mycb5 = findViewById(R.id.cb_5);
        mycb6 = findViewById(R.id.cb_6);
        mycb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(CheckBoxActivity.this,b?"4选中":"4未选中",Toast.LENGTH_SHORT).show();

            }
        });
        mycb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(CheckBoxActivity.this,b?"5选中":"5未选中",Toast.LENGTH_SHORT).show();

            }
        });
        mycb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(CheckBoxActivity.this,b?"6选中":"6未选中",Toast.LENGTH_SHORT).show();

            }
        });
    }
}