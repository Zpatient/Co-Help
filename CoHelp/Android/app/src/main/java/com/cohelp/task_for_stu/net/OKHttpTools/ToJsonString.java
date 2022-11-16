package com.cohelp.task_for_stu.net.OKHttpTools;

import com.google.gson.Gson;

public class ToJsonString {
    Gson gson;
    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}
