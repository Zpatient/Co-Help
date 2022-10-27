package com.cohelp.task_for_stu.biz;

import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.ui.vo.Task;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

public class TaskBiz {
    public void getAll(CommonCallback<List<Task>> commonCallback) {
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "getAllTasks")
                .tag(this)
                .build()
                .execute(commonCallback);
    }

    public void searchByState(String state, CommonCallback<List<Task>> commonCallback) {
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "searchTaskByState")
                .addParams("state",state)
                .tag(this)
                .build()
                .execute(commonCallback);
    }

    public void searchByTitle(String s, CommonCallback<List<Task>> commonCallback) {
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "searchTaskByTitle")
                .addParams("title",s)
                .tag(this)
                .build()
                .execute(commonCallback);
    }
    public void searchTaskByTitleForManager(String s, CommonCallback<List<Task>> commonCallback) {
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "searchByTitleForManager")
                .addParams("title",s)
                .tag(this)
                .build()
                .execute(commonCallback);
    }
    public void save(Task task, CommonCallback<String> commonCallback) {
        OkHttpUtils
                .postString()
                .url(Config.baseUrl + "saveTask")
                .tag(this)
                .content(new Gson().toJson(task))
                .build()
                .execute(commonCallback);
    }

    public void finishTask(Task task, CommonCallback<String> commonCallback) {
        OkHttpUtils
                .postString()
                .url(Config.baseUrl + "finishTask")
                .tag(this)
                .content(new Gson().toJson(task))
                .build()
                .execute(commonCallback);
    }

    public void getAllMyTask(Long id, CommonCallback<List<Task>> listCommonCallback) {
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "getAllMyTask")
                .addParams("id",id+"")
                .tag(this)
                .build()
                .execute(listCommonCallback);
    }

    public void getAllMyTaskSolved(Long id, CommonCallback<List<Task>> listCommonCallback) {
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "getAllMyTaskSolved")
                .addParams("id",id+"")
                .tag(this)
                .build()
                .execute(listCommonCallback);
    }

    public void getAllMyTaskPosted(Long id, CommonCallback<List<Task>> listCommonCallback) {
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "getAllMyTaskPosted")
                .addParams("id",id+"")
                .tag(this)
                .build()
                .execute(listCommonCallback);
    }

    public void getAllOfAllTasks(CommonCallback<List<Task>> listCommonCallback) {
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "getAllOfAllTasks")
                .tag(this)
                .build()
                .execute(listCommonCallback);
    }

    public void deleteTask(Long id, CommonCallback<String> listCommonCallback) {
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "deleteTask")
                .addParams("id",id+"")
                .tag(this)
                .build()
                .execute(listCommonCallback);
    }
}
