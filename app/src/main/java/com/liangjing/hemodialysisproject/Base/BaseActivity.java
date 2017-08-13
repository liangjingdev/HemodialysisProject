package com.liangjing.hemodialysisproject.Base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by liangjing on 2017/8/8.
 * function:封装底层Activity--增加扩展性、灵活性
 * 可以在该底层Activity中实现一些每个Activity的共性操作，然后让每个Activity都继承自该底层Activity。--简洁
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //根据id来设置视图view
        setContentView(setLayoutResourceID());
        init();
        setUpView();
        initEvents();
    }


    /**
     * function:初始化一些(需要处理的)事件
     */
    protected void initEvents() {
    }


    /**
     * function:设置将要显示的视图的id
     *
     * @return
     */
    protected abstract int setLayoutResourceID();


    /**
     * function:初始化view
     */
    protected abstract void setUpView();


    /**
     * function:初始化操作
     */
    protected void init() {

    }


    /**
     * function:绑定view视图
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T $(int id) {
        return (T) super.findViewById(id);
    }


    /**
     * function:跳转到另外一个Activity(不携带额外的信息)--供子类调用
     *
     * @param clazz
     */
    protected void startActivityWithoutExtras(Class<?> clazz) {

        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }


    /**
     * function:跳转到另外一个Activity(携带额外的信息)--供子类调用
     * Bundle--携带数据(Activity之间跳转时需要传递的数据就存在Bundle里面)
     *
     * @param clazz
     * @param extras
     */
    protected void startActivityWithExtras(Class<?> clazz, Bundle extras) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(extras);
        startActivity(intent);
    }

}