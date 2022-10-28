package com.cohelp.task_for_stu.biz;

import com.cohelp.task_for_stu.bean.BaseTask;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.ui.vo.Question;
import com.cohelp.task_for_stu.ui.vo.RepeatForQuestion;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

public class QuestionBiz {
    public void getAll(CommonCallback<List<Question>> commonCallback){
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "getAllQuestions")
                .tag(this)
                .build()
                .execute(commonCallback);
    }
    public void save(Question question,CommonCallback<String> commonCallback){
        OkHttpUtils
                .postString()
                .url(Config.baseUrl + "saveQuestion")
                .tag(this)
                .content(new Gson().toJson(question))
                .build()
                .execute(commonCallback);
    }
    public void searchByTitle(String q,CommonCallback<List<Question>> commonCallback){
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "searchQuestionByTitle")
                .addParams("title",q)
                .tag(this)
                .build()
                .execute(commonCallback);
    }
    public void searchQuestionByTitleForManager(String q,CommonCallback<List<Question>> commonCallback){
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "searchQuestionByTitleForManager")
                .addParams("title",q)
                .tag(this)
                .build()
                .execute(commonCallback);
    }
    public void searchByState(String q,CommonCallback<List<Question>> commonCallback){
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "searchQuestionByState")
                .addParams("state",q)
                .tag(this)
                .build()
                .execute(commonCallback);
    }

    public void insertRepeat(BaseTask baseTask, CommonCallback<String> commonCallback){
        OkHttpUtils
                .postString()
                .url(Config.baseUrl + "insertRepeat")
                .tag(this)
                .content(new Gson().toJson(baseTask))
                .build()
                .execute(commonCallback);
    }

    public void getRepeatByRid(Long rid,CommonCallback<List<RepeatForQuestion>> commonCallback){
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "getQuestionRepeatByRid")
                .addParams("rid",rid+"")
                .tag(this)
                .build()
                .execute(commonCallback);
    }
    public void updateRepeat(RepeatForQuestion repeat, CommonCallback<List<RepeatForQuestion>> commonCallback){
        OkHttpUtils
                .postString()
                .tag(this)
                .url(Config.baseUrl + "saveRepeat")
                .content(new Gson().toJson(repeat))
                .build()
                .execute(commonCallback);
    }
    public void onDestory(){
        OkHttpUtils.getInstance().cancelTag(this);
    }

    public void getAllMyQuestion(Long id, CommonCallback<List<Question>> commonCallback) {
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "getAllMyQuestion")
                .addParams("id",id+"")
                .tag(this)
                .build()
                .execute(commonCallback);
    }

    public void getAllMyQuestionAsked(Long id, CommonCallback<List<Question>> commonCallback) {
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "getAllMyQuestionAsked")
                .addParams("id",id+"")
                .tag(this)
                .build()
                .execute(commonCallback);
    }

    public void getAllMyQuestionRepeated(Long id, CommonCallback<List<Question>> commonCallback) {
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "getAllMyQuestionRepeated")
                .addParams("id",id+"")
                .tag(this)
                .build()
                .execute(commonCallback);
    }

    public void deleteQuestion(Long id, CommonCallback<String> listCommonCallback) {
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "deleteQuestion")
                .addParams("id",id+"")
                .tag(this)
                .build()
                .execute(listCommonCallback);
    }

    public void getAllOfAllQuestions(CommonCallback<List<Question>> commonCallback){
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "getAllOfAllQuestions")
                .tag(this)
                .build()
                .execute(commonCallback);
    }

}
