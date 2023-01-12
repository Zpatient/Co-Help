package com.cohelp.task_for_stu;

import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.model.domain.ActivityListRequest;
import com.cohelp.task_for_stu.net.model.domain.HelpListRequest;
import com.cohelp.task_for_stu.net.model.domain.HelpTagRequest;
import com.cohelp.task_for_stu.net.model.entity.Activity;
import com.cohelp.task_for_stu.net.model.entity.Help;
import com.google.gson.Gson;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

public class HelpTest {
    OKHttp okHttp  = new OKHttp();
    //    LoginRequest loginRequest = new LoginRequest();
    Gson gson = new Gson();
    Help help;
    @Test
    public void helpPublish(){
        help = new Help(78,1,"nice","wow", 0,0,0,0,"friend",0, new Date());
        String act = gson.toJson(help);
        okHttp.sendRequest("http://43.143.90.226:9090/help/publish",act);
        String res = okHttp.getResponse().toString();
        System.out.println(res);
    }
    @Test
    public void helpUpdate(){
        help = new Help(7,1,"nice","wow", 0,0,0,0,"friend",0, new Date());
        String act = gson.toJson(help);
        okHttp.sendRequest("http://43.143.90.226:9090/help/update",act);
        String res = okHttp.getResponse().toString();
        System.out.println(res);


    }
    @Test
    public void helpList(){
        HelpListRequest helpListRequest = new HelpListRequest();
        helpListRequest.setConditionType(1);
        String req = gson.toJson(helpListRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/help/list",req);
        String res = okHttp.getResponse().toString();
        System.out.println(res);

    }
    @Test
    public void helpListTag(){
        HelpTagRequest helpTagRequest = new HelpTagRequest();
        helpTagRequest.setTag("学习");
        String req = gson.toJson(helpTagRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/help/list/tag",req);
        String res = okHttp.getResponse().toString();
        System.out.println(res);

    }
}
