package com.cohelp.task_for_stu.biz;

import com.cohelp.task_for_stu.bean.Comment;
import com.cohelp.task_for_stu.bean.User;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.List;

public class UserBiz {
    public void login(String username, String password, CommonCallback<User> commonCallback){
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "login")
                .tag(this)
                .addParams("username",username)
                .addParams("password",password)
                .build()
                .execute(commonCallback);

    }
    public void onDestory(){
        OkHttpUtils.getInstance().cancelTag(this);
    }

    public void register(User user, CommonCallback<User> commonCallback){
        OkHttpUtils
                .postString()
                .tag(this)
                .url(Config.baseUrl + "userRegister")
                .content(new Gson().toJson(user))
                .build()
                .execute(commonCallback);
    }

    public void uploadImg(File file , CommonCallback<String> commonCallback){
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "uploadImage")
                .tag(this)
                .addFile("file",file.getName(),file)
                .build()
                .execute(commonCallback);
    }

    public void userFound(User user,CommonCallback<String> commonCallback){
        OkHttpUtils
                .postString()
                .tag(this)
                .url(Config.baseUrl + "userFound")
                .content(new Gson().toJson(user))
                .build()
                .execute(commonCallback);
    }

    public void userGet(Long id,CommonCallback<User> commonCallback){
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "getUserById")
                .tag(this)
                .addParams("id",id+"")
                .build()
                .execute(commonCallback);
    }

    public void updateUser(User user,CommonCallback<User> commonCallback){
        OkHttpUtils
                .postString()
                .tag(this)
                .url(Config.baseUrl + "updateUser")
                .content(new Gson().toJson(user))
                .build()
                .execute(commonCallback);
    }

    public void getAll(CommonCallback<List<User>> commonCallback){
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "getAllUsers")
                .tag(this)
                .build()
                .execute(commonCallback);
    }


    public void getAllOfAllUsers(CommonCallback<List<User>> listCommonCallback) {
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "getAllOfAllUsers")
                .tag(this)
                .build()
                .execute(listCommonCallback);
    }

    public void getAllManagers(CommonCallback<List<User>> listCommonCallback) {
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "getAllManagers")
                .tag(this)
                .build()
                .execute(listCommonCallback);
    }

    public void searchByName(String s, CommonCallback<List<User>> listCommonCallback) {
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "searchByName")
                .tag(this)
                .addParams("nickName",s)
                .build()
                .execute(listCommonCallback);
    }

    public void del(Long id,CommonCallback<String> commonCallback){
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "deleteUser")
                .tag(this)
                .addParams("id",id+"")
                .build()
                .execute(commonCallback);
    }

    public void getCommentByUid(Long id,CommonCallback<List<Comment>> commonCallback){
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "getCommentByUid")
                .tag(this)
                .addParams("id",id+"")
                .build()
                .execute(commonCallback);
    }
}
