package com.cohelp.task_for_stu;

import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.model.domain.HelpListRequest;
import com.cohelp.task_for_stu.net.model.entity.Help;
import com.cohelp.task_for_stu.net.model.entity.Hole;
import com.google.gson.Gson;

import org.junit.Test;

import java.util.Date;

public class HoleTest {
    OKHttp okHttp  = new OKHttp();
    //    LoginRequest loginRequest = new LoginRequest();
    Gson gson = new Gson();
    Hole hole;
    @Test
    public void holePublish(){
////        hole = new Hole(78,1,"nice","wow", 0,0,0,0,"friend",0, new Date());
//        String act = gson.toJson(hele);
//        okHttp.sendRequest("http://43.143.90.226:9090/help/publish",act);
//        String res = okHttp.getResponse().toString();
//        System.out.println(res);
//    }
//    @Test
//    public void holeUpdate(){
//        help = new Help(7,1,"nice","wow", 0,0,0,0,"friend",0, new Date());
//        String act = gson.toJson(hele);
//        okHttp.sendRequest("http://43.143.90.226:9090/help/update",act);
//        String res = okHttp.getResponse().toString();
//        System.out.println(res);
//
//
//    }
//    @Test
//    public void holeList(){
//        HelpListRequest helpListRequest = new HelpListRequest();
//        helpListRequest.setConditionType(1);
//        String req = gson.toJson(helpListRequest);
//        okHttp.sendRequest("http://43.143.90.226:9090/help/list",req);
//        String res = okHttp.getResponse().toString();
//        System.out.println(res);
//
    }
}
