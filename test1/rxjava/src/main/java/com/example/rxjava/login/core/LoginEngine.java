package com.example.rxjava.login.core;

import com.example.rxjava.login.bean.ResponseResult;
import com.example.rxjava.login.bean.SuccessfulBean;

import io.reactivex.Observable;

public class LoginEngine {



    //返回起点
    public static Observable<ResponseResult> login(String name,String pwd){

        //返回总bean
        ResponseResult responseResult = new ResponseResult();


        //登陆成功
        if ("hejiang".equals(name) && "123456".equals(pwd)){
            SuccessfulBean successfulBean = new SuccessfulBean();
            successfulBean.setId(987654);
            successfulBean.setName("hj登陆成功");

            responseResult.setData(successfulBean);
            responseResult.setCode(200);
            responseResult.setMessage("登陆成功");

        //登陆失败
        }else {
            responseResult.setData(null);
            responseResult.setCode(404);
            responseResult.setMessage("登陆失败");

        }

        //返回起点
        return Observable.just(responseResult);

    }


}
