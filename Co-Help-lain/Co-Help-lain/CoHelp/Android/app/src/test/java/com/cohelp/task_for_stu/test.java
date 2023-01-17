package com.cohelp.task_for_stu;

import com.alibaba.fastjson.JSON;
import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.ToJsonString;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.HistoryAndCollectRequest;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.net.model.domain.IdAndTypeList;
import com.cohelp.task_for_stu.net.model.domain.LoginRequest;
import com.cohelp.task_for_stu.net.model.domain.PublishDeleteRequest;
import com.cohelp.task_for_stu.net.model.domain.RemarkRequest;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.domain.SearchPublishResponse;
import com.cohelp.task_for_stu.net.model.domain.SearchRequest;
import com.cohelp.task_for_stu.net.model.entity.Activity;
import com.cohelp.task_for_stu.net.model.entity.Collect;
import com.cohelp.task_for_stu.net.model.entity.History;
import com.cohelp.task_for_stu.net.model.entity.RemarkActivity;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

public class test {
    OKHttp okHttp  = new OKHttp();
    LoginRequest loginRequest = new LoginRequest();
    IdAndType idAndType = new IdAndType();
    SearchRequest searchRequest = new SearchRequest();
    RemarkRequest remarkRequest = new RemarkRequest();
    RemarkActivity remarkActivity = new RemarkActivity();
    HistoryAndCollectRequest historyAndCollectRequest = new HistoryAndCollectRequest();
    History history = new History();
    Collect collect = new Collect();
    PublishDeleteRequest publishDeleteRequest = new PublishDeleteRequest();
    public void login(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);


    }

    @Test
    public void getUserBase(){
        loginRequest.setUserAccount("1234567890");//debug
        loginRequest.setUserPassword( "1234567890");//debug
        String loginMessage = ToJsonString.toJson(loginRequest);

        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);

        okHttp.sendGetRequest("http://43.143.90.226:9090/user/current",cookieval);

        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Result<User> userResult = gson.fromJson(res, new TypeToken<Result<User>>(){}.getType());
        System.out.println(userResult.getData());
    }




    //有数据
    @Test
    public void searchPublish(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);
        okHttp.sendGetRequest("http://43.143.90.226:9090/user/searchpub/1234567890",cookieval);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Gson gson = new Gson();
//        Result<SearchPublishResponse> userResult = gson.fromJson(res,new TypeToken<Result<SearchPublishResponse>>(){}.getType());
        Result result = JSON.parseObject(res, new Result<SearchPublishResponse>().getClass());
        System.out.println("data:"+result.getData());
    }

    //返回null
    @Test
    public void delPublish(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);

        publishDeleteRequest.setId(13);
        publishDeleteRequest.setOwnerId(1);
        publishDeleteRequest.setTypeNumber(1);
        String string = JSON.toJSONString(publishDeleteRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/deletepub",string,cookieval);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Gson gson = new Gson();
//        Result<SearchPublishResponse> userResult = gson.fromJson(res,new TypeToken<Result<SearchPublishResponse>>(){}.getType());
        Result result = JSON.parseObject(res, new Result<Object>().getClass());
        System.out.println("data:"+result);
    }

    //返回成功
    @Test
    public void getDetail(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println("cokie: "+cookieval);
        idAndType.setType(1);
        idAndType.setId(1);
        String detailMessage = ToJsonString.toJson(idAndType);
        okHttp.sendRequest("http://43.143.90.226:9090/general/getdetail",detailMessage,cookieval);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Gson gson = new Gson();
//        Result<DetailResponse> userResult = gson.fromJson(res,new TypeToken<Result<Object>>(){}.getType());
//        System.out.println("data:"+userResult);
        Result<DetailResponse> parseObject = JSON.parseObject(res, new Result<DetailResponse>().getClass());
        System.out.println(parseObject.getData());
    }


    //有问题------------arraylist问题
    @Test
    public void search(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);

        searchRequest.setKey("不删测试");
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list);
        searchRequest.setTypes(list);
        String searchMessage = ToJsonString.toJson(searchRequest);
        System.out.println(searchMessage);
        okHttp.sendRequest("http://43.143.90.226:9090/general/search",searchMessage);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Gson gson = new Gson();
//        //这个类型写啥？list有1,2,3这里知识查了activity的
//        Result<IdAndTypeList> userResult = gson.fromJson(res,new TypeToken<Result<IdAndTypeList>>(){}.getType());
        Result<IdAndTypeList> parseObject = JSON.parseObject(res, new Result<IdAndTypeList>().getClass());
        System.out.println("data:"+parseObject);


    }

    @Test
    public void topicLike(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);

        okHttp.sendRequest("http://43.143.90.226:9090/topic/like/1/1","",cookieval);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
            System.out.println("res:"+res);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Gson gson = new Gson();
//        Result<History> userResult = gson.fromJson(res,new TypeToken<Result<History>>(){}.getType());
        Result parseObject = JSON.parseObject(res, new Result<Object>().getClass());
        System.out.println(parseObject);

    }


    //没问题
    @Test
    public void insertRemark(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);

        remarkActivity.setId(1);
        remarkActivity.setRemarkTargetId(1);
        remarkActivity.setRemarkActivityId(1);
        remarkActivity.setRemarkLike(0);
        remarkActivity.setRemarkContent("我来测试评论了");
        remarkActivity.setRemarkOwnerId(1);
        remarkActivity.setTopId(1);
        remarkActivity.setTargetIsActivity(1);
        LocalDateTime localDateTime = LocalDateTime.now();
        remarkActivity.setRemarkTime(localDateTime);
        remarkRequest.setRemarkActivity(remarkActivity);
        remarkRequest.setType(1);
//        remarkRequest.setRemarkHelp(null);
//        remarkRequest.setRemarkHole(null);
//        String remarkMessage = ToJsonString.toJson(remarkRequest);
        String s = JSON.toJSONStringWithDateFormat(remarkRequest, "yyyy-MM-dd HH:mm:ss");
        System.out.println(s);

        okHttp.sendRequest("http://43.143.90.226:9090/general/insertremark",s,cookieval);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Gson gson = new Gson();
//        Result userResult = gson.fromJson(res,new TypeToken<Result<Activity>>(){}.getType());
        Result result = JSON.parseObject(res, new Result<Object>().getClass());
        System.out.println("data:"+result);
    }

    //返回成功
    @Test
    public void delRemark(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);

        idAndType.setId(4);
        idAndType.setType(1);
        String remarkMessage = ToJsonString.toJson(idAndType);

        System.out.println(remarkMessage);
        okHttp.sendRequest("http://43.143.90.226:9090/general/deleteremark",remarkMessage,cookieval);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Result userResult = gson.fromJson(res,new TypeToken<Result<Object>>(){}.getType());
        System.out.println("data:"+userResult);
    }

    //返回成功
    @Test
    public void getRemark(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);

        idAndType.setType(1);
        idAndType.setId(1);
        String getmark = ToJsonString.toJson(idAndType);
        okHttp.sendRequest("http://43.143.90.226:9090/general/getremarklist",getmark,cookieval);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
            System.out.println("res:"+res);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Gson gson = new Gson();
//        Result<List<RemarkActivity>> userResult = gson.fromJson(res,new TypeToken<Result<List<RemarkActivity>>>(){}.getType());
        Result result = JSON.parseObject(res, new Result<List<RemarkActivity>>().getClass());
        System.out.println("data:"+result);
    }


    //返回成功
    @Test
    public void getCollect(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);

        historyAndCollectRequest.setUserId(1);
        historyAndCollectRequest.setPageNum(1);
        historyAndCollectRequest.setRecordMaxNum(1);
        String getcollect = ToJsonString.toJson(historyAndCollectRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/collect/getcollectlist",getcollect,cookieval);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
            System.out.println("res:"+res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
//        Result<List<Collect>> userResult = gson.fromJson(res,new TypeToken<Result<List<Collect>>>(){}.getType());
        Result result = JSON.parseObject(res, new Result<Collect>().getClass());
        System.out.println("data:"+result);
    }

    //没问题
    @Test
    public void insertCollect(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);
        LocalDateTime localDateTime = LocalDateTime.now();
        collect.setId(null);
        collect.setTopicId(1);
        collect.setUserId(1);
        collect.setTopicType(1);
        collect.setCollectTime(localDateTime);

//        String insertcollect = ToJsonString.toJson(history);
       String insertcollect = JSON.toJSONStringWithDateFormat(collect, "yyyy-MM-dd HH:mm:ss");
        okHttp.sendRequest("http://43.143.90.226:9090/collect/insertcollectrecord",insertcollect,cookieval);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
            System.out.println("res:"+res);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Gson gson = new Gson();
//        Result<History> userResult = gson.fromJson(res,new TypeToken<Result<History>>(){}.getType());
        Result parseObject = JSON.parseObject(res, new Result<Collect>().getClass());

        System.out.println(parseObject);

//        System.out.println("data:"+userResult);

    }


    //获取成功

    @Test
    public void getHistory(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);

        historyAndCollectRequest.setUserId(1);
        historyAndCollectRequest.setPageNum(1);
        historyAndCollectRequest.setRecordMaxNum(1);
        String getcollect = ToJsonString.toJson(historyAndCollectRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/history/gethistorylist",getcollect,cookieval);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
            System.out.println("res:"+res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
//        Result<History> userResult = gson.fromJson(res,new TypeToken<Result<History>>(){}.getType());
        Result parseObject = JSON.parseObject(res, new Result<History>().getClass());
        System.out.println(parseObject);

//        System.out.println("data:"+userResult);
    }

    //时间格式问题-badrequest
    @Test
    public void insertHistory(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);

        LocalDateTime localDateTime = LocalDateTime.now();
        history.setId(null);
        history.setTopicId(1);
        history.setTopicType(2);
        history.setUserId(1);
        history.setViewTime(localDateTime);

        String inserthistory = JSON.toJSONStringWithDateFormat(history, "yyyy-MM-dd HH:mm:ss");
//        String inserthistory = ToJsonString.toJson(history);
        okHttp.sendRequest("http://43.143.90.226:9090/history/inserthistoryrecord",inserthistory,cookieval);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
            System.out.println("res:"+res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
//        Result<History> userResult = gson.fromJson(res,new TypeToken<Result<History>>(){}.getType());
        Result parseObject = JSON.parseObject(res, new Result<History>().getClass());
        System.out.println(parseObject);

    }

    //返回空
    @Test
    public void delhistory(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);

        okHttp.sendRequest("http://43.143.90.226:9090/history/deletehistoryrecord?id=9","",cookieval);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
            System.out.println("res:"+res);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Gson gson = new Gson();
//        Result<History> userResult = gson.fromJson(res,new TypeToken<Result<History>>(){}.getType());
        Result parseObject = JSON.parseObject(res, new Result<Object>().getClass());
        System.out.println(parseObject);
    }

    //返回空
    @Test
    public void remark(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);

        okHttp.sendRequest("http://43.143.90.226:9090/remark/like/1/1","",cookieval);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
            System.out.println("res:"+res);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Gson gson = new Gson();
//        Result<History> userResult = gson.fromJson(res,new TypeToken<Result<History>>(){}.getType());
        Result parseObject = JSON.parseObject(res, new Result<Object>().getClass());
        System.out.println(parseObject);

    }
}
