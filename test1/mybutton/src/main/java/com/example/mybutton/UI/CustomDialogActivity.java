package com.example.mybutton.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mybutton.R;
import com.example.mybutton.util.ToastUtil;
import com.example.mybutton.widget.CustomDialog;

public class CustomDialogActivity extends AppCompatActivity {

    private Button myCD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog);
        myCD = findViewById(R.id.bt_cd);
        myCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog customDialog = new CustomDialog(CustomDialogActivity.this,R.style.CustomDialog);
                customDialog.setTitle("提示").setMessage("确认删除此项？").setCancel("取消", new CustomDialog.IOnCancelListener() {
                    @Override
                    public void onCancel(CustomDialog dialog) {
                        ToastUtil.showMsg(CustomDialogActivity.this,"cancel...");
                    }
                }).setConfirm("确认", new CustomDialog.IOnConfirmListener() {
                    @Override
                    public void onConfirm(CustomDialog dialog) {
                        ToastUtil.showMsg(CustomDialogActivity.this,"confirm...");
                    }
                }).show();

            }
        });
    }
}