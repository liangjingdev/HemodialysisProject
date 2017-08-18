package com.liangjing.hemodialysisproject.application;

import android.app.Application;

import com.liangjing.hemodialysisproject.db.DbCore;


/**
 * Created by liangjing on 2017/8/17.
 * <p>
 * function:自定义Application
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化数据库
        DbCore.getInstance().init(this);

    }
}
