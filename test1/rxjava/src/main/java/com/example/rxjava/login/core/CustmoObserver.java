package com.example.rxjava.login.core;

import com.example.rxjava.login.bean.ResponseResult;
import com.example.rxjava.login.bean.SuccessfulBean;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


//终点
public abstract class CustmoObserver implements Observer<ResponseResult> {

    public abstract void success(SuccessfulBean successfulBean);
    public abstract void error(String message);


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(ResponseResult responseResult) {
        if(responseResult.getData() == null){
            error(responseResult.getMessage() + "请求失败");
        }else {
            success(responseResult.getData());
        }
    }

    @Override
    public void onError(Throwable e) {
        error(e.getMessage() + "请检查日志");
    }

    @Override
    public void onComplete() {

    }
}
