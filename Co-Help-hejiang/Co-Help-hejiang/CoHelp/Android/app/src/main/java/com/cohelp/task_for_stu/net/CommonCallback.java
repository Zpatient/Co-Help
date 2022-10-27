package com.cohelp.task_for_stu.net;

import com.cohelp.task_for_stu.utils.GsonUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;

public abstract class CommonCallback<T> extends StringCallback {
    private Type eType;
    public CommonCallback(){
        Class<? extends CommonCallback> aClass = getClass();
        Type genericSuperclass = aClass.getGenericSuperclass();
        if(genericSuperclass instanceof Class){
            throw new RuntimeException("缺失泛型");
        }
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        eType = parameterizedType.getActualTypeArguments()[0];
    }
    @Override
    public void onError(Call call, Exception e, int i) {
        onError(e);
    }

    @Override
    public void onResponse(String s, int i) {
        try {
            JSONObject resp = new JSONObject(s);
            int resultCode = resp.getInt("resultCode");
            if(resultCode == 1){
                String data = resp.getString("data");
                onSuccess((T) GsonUtil.getGson().fromJson(data,eType));
            }else{
                onError(new RuntimeException(resp.getString("resultMessage")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            onError(e);
        }
    }
    public abstract void onError(Exception e);
    public abstract void onSuccess(T response);
}
