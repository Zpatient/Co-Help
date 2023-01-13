package com.example.mybutton.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mybutton.R;
import com.example.mybutton.util.ToastUtil;

public class DialogActivity extends AppCompatActivity {

    private Button myDialog1,myDialog2,myDialog3,myDialog4,myDialog5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);



        myDialog1 = findViewById(R.id.bt_dialog1);
        myDialog2 = findViewById(R.id.bt_dialog2);
        myDialog3 = findViewById(R.id.bt_dialog3);
        myDialog4 = findViewById(R.id.bt_dialog4);
        myDialog5 = findViewById(R.id.bt_dialog5);
        SetOnClickListener();
    }

    public  void SetOnClickListener(){
        OnClick onClick = new OnClick();
        myDialog1.setOnClickListener(onClick);
        myDialog2.setOnClickListener(onClick);
        myDialog3.setOnClickListener(onClick);
        myDialog4.setOnClickListener(onClick);
        myDialog5.setOnClickListener(onClick);

    }

    class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_dialog1:
//                    Toast.makeText(DialogActivity.this, "江江", Toast.LENGTH_SHORT).show();
                    //链式调用
                    AlertDialog.Builder builder = new AlertDialog.Builder(DialogActivity.this);
                    builder.setTitle("请回答").setMessage("倩倩喜欢江江吗？").setIcon(R.drawable.ic_baseline_account_circle_24)
                            .setPositiveButton("喜欢", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ToastUtil.showMsg(DialogActivity.this,"获得江江的香吻~");
                        }
                    }).setNeutralButton("一般", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ToastUtil.showMsg(DialogActivity.this,"获得江江双拳警告！");
                        }
                    }).setNegativeButton("不喜欢", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(DialogActivity.this, "获得江江一个大嘴巴子！！！", Toast.LENGTH_SHORT);
                            ToastUtil.showMsg(DialogActivity.this,"获得江江一个大嘴巴子！");
                        }
                    }).show();
                    break;
                case R.id.bt_dialog2:
                    final String[] array2 = new String[]{"男","女"};
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(DialogActivity.this);
                    builder2.setTitle("选择性别");
                    builder2.setItems(array2, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(DialogActivity.this, array2[which],Toast.LENGTH_LONG).show();
//                            ToastUtil.showMsg(DialogActivity.this,array2[which]);
                        }

                    }).show();
                    break;
                case R.id.bt_dialog3:
                    final String[] array3 = new String[]{"男","女"};
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(DialogActivity.this);
                    builder3.setTitle("选择性别").setSingleChoiceItems(array3, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(DialogActivity.this, array3[which], Toast.LENGTH_SHORT).show();
                            //选取后让对话框消失
                            dialog.dismiss();
                        }
                    }).setCancelable(false).show();  //setCancelable-点击旁边灰色背景不会让对话框消失
                    break;
                case R.id.bt_dialog4:
                    final String[] array4 = new String[]{"唱歌","跳舞","rap","篮球"};
                    boolean[] isSelected = new boolean[]{false,false,false,false};
                    AlertDialog.Builder builder4 = new AlertDialog.Builder(DialogActivity.this);
                    builder4.setTitle("兴趣选择").setMultiChoiceItems(array4, isSelected, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            ToastUtil.showMsg(DialogActivity.this,array4[which]+":"+isChecked);
                        }
                    }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //加逻辑
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //加逻辑
                        }
                    }).show();
                    break;
                case R.id.bt_dialog5:
                    AlertDialog.Builder builder5 = new AlertDialog.Builder(DialogActivity.this);
                    View view = LayoutInflater.from(DialogActivity.this).inflate(R.layout.activity_dialog_item,null);
                    EditText etUserName = view.findViewById(R.id.et_username);
                    EditText etPassWord = view.findViewById(R.id.et_password);
                    Button btlogin = view.findViewById(R.id.bt_login);
                    btlogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //加逻辑

                        }
                    });
                    builder5.setTitle("登录界面").setView(view).show();

                    break;
            }
        }
    }
}