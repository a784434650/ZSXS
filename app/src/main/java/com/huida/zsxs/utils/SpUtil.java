package com.huida.zsxs.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xiaojiu on 2017/4/8.
 * sp工具类
 */

public class SpUtil {
    private static final String SPNAME = "zsxs" ;
    private static SharedPreferences sp;

    //存布尔类型的值的方法
    public static void putBoolean(String key, boolean value, Context context){
        if (sp==null) {
            sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).commit();
    }
    //取布尔类型值
    public static boolean getBoolean(String key, Context context) {
        if (sp==null) {
            sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        }
        boolean b = sp.getBoolean(key, false);
        return b;
    }
    //存String类型的值的方法
    public static void putString(String key, String value, Context context){
        if (sp==null) {
            sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key,value).commit();
    }
    //取String类型值
    public static String getString(String key, Context context) {
        if (sp==null) {
            sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        }
        String b = sp.getString(key, "");
        return b;
    }
}
