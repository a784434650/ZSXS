package com.huida.zsxs.activity;

import android.app.Application;

import org.xutils.x;

/**
 * Created by xiaojiu on 2017/6/23.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        // 设置是否输出debug
        x.Ext.setDebug(true);

    }
}
