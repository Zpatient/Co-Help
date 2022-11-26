package com.cohelp.task_for_stu.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionUtils {
        public static final String LOGIN = "login";
        public static final String COOKIEVAL = "cookieval";

        public static void saveCookiePreference(Context context, String value) {
            SharedPreferences preference = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preference.edit();
            editor.putString(COOKIEVAL, value);
            editor.apply();
        }

        public static String getCookiePreference(Context context) {
            SharedPreferences preference = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
            String cookieval = preference.getString(COOKIEVAL, "");
            return cookieval;
        }
}
