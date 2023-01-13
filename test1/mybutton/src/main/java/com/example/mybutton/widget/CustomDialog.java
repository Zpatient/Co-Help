package com.example.mybutton.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.example.mybutton.R;
import androidx.annotation.NonNull;

public class CustomDialog extends Dialog implements View.OnClickListener{

    private TextView myTvTitle,myTvMessage,myTvCancel,myTvConfirm;

    private String title,message,cancel,confirm;

    private IOnCancelListener cancelListener;

    private IOnConfirmListener confirmListener;

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    //如果采用链式编程，则可以把void改成相对应的CustomDialog,然后返回就好了
    public CustomDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CustomDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public CustomDialog setCancel(String cancel,IOnCancelListener cancelListener) {
        this.cancel = cancel;
        this.cancelListener = cancelListener;
        return this;
    }

    public CustomDialog setConfirm(String confirm,IOnConfirmListener confirmListener) {

        this.confirm = confirm;
        this.confirmListener = confirmListener;
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog_item);

        //设置宽度-如果不设置默认为0.8
        WindowManager m = getWindow().getWindowManager();
        Display d= m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int) (size.x * 0.8);
        getWindow().setAttributes(p);

        myTvTitle = findViewById(com.example.mybutton.R.id.tv_cd_title);
        myTvMessage = findViewById(com.example.mybutton.R.id.tv_cd_message);
        myTvCancel = findViewById(com.example.mybutton.R.id.tv_cd_cancel);
        myTvConfirm = findViewById(com.example.mybutton.R.id.tv_cd_confirm);


        //设置内容信息
        if(!TextUtils.isEmpty(title)){
            myTvTitle.setText(title);
        }
        if(!TextUtils.isEmpty(message)){
            myTvMessage.setText(message);
        }
        if(!TextUtils.isEmpty(cancel)){
            myTvCancel.setText(cancel);
        }
        if(!TextUtils.isEmpty(confirm)){
            myTvConfirm.setText(confirm);
        }

        //已经实现了这个接口，因此可以用this代替,
        myTvCancel.setOnClickListener(CustomDialog.this);
        myTvConfirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.tv_cd_cancel:
               if(cancelListener != null)
                   cancelListener.onCancel(this);
               dismiss();
               break;
            case R.id.tv_cd_confirm:
                if(confirmListener != null)
                    confirmListener.onConfirm(this);
                dismiss();
                break;
        }
    }

    public interface IOnCancelListener{
        void onCancel(CustomDialog dialog);

    }
    public interface IOnConfirmListener{
        void onConfirm(CustomDialog dialog);
    }

}
