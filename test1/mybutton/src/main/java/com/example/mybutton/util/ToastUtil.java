package com.example.mybutton.util;


import android.content.Context;
import android.widget.Toast;

public class ToastUtil  {
        public static Toast toast;
        public static void showMsg(Context context,String msg){
            if (toast == null){
                toast = Toast.makeText(context,msg,Toast.LENGTH_LONG);
            }else {
//                toast.cancel();
//                toast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
                toast.setText(msg);
//                toast.setDuration(Toast.LENGTH_SHORT);
            }
            toast.show();
        }


}
