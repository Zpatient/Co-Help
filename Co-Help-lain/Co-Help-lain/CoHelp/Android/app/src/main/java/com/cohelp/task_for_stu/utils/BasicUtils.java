package com.cohelp.task_for_stu.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.cohelp.task_for_stu.bean.User;
import com.leon.lfilepickerlibrary.utils.StringUtils;

public class BasicUtils {
    private static ProgressDialog eLoadingDialog;

    public static String UserInfoLegal(User user, String rePasword){
        boolean flag = true;
        /**
         * 判断手机是否合法
         */
        if(!StringUtils.isEmpty(user.getPhone())){
            flag = user.getEmail().length() == 11;
            if(!flag) return "手机号码不合法";
        }else
            flag = false;

        if(!flag) return "手机号码不合法";

        if(StringUtils.isEmpty(user.getNickName())) return "昵称不可为空哦~";
        if(StringUtils.isEmpty(user.getUserName())) return "账号不可为空哦~";
        if(StringUtils.isEmpty(user.getPassword())) return "密码不可为空哦~";
        if(StringUtils.isEmpty(user.getRealName())) return "真实姓名不可为空哦~";
        if(StringUtils.isEmpty(user.getPhone())) return "手机号不可为空哦~";
        if(StringUtils.isEmpty(rePasword)) return "请再次输入密码哦~";
        if(!rePasword.equals(user.getPassword())) return "两次密码不相同哦~";
        return "合法";
    }

    public static boolean StringArrayEmpty(String [] set){
        for(String s : set){
            if(StringUtils.isEmpty(s)) return false;
        }
        return true;
    }

    public static void stopLoadingProgress() {
        if(eLoadingDialog != null && eLoadingDialog.isShowing()) eLoadingDialog.dismiss();
    }
    public static void startLoadingProgress(Context context) {
        eLoadingDialog = new ProgressDialog(context);
        eLoadingDialog.setMessage("加载中......");
        eLoadingDialog.show();
    }
}
