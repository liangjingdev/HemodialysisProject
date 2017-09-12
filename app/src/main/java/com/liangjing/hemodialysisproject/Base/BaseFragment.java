package com.liangjing.hemodialysisproject.Base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liangjing on 2017/8/8.
 * function:封装底层Fragment--增加扩展性、灵活性
 * 可以在该底层Fragment中实现一些每个Fragment的共性操作，然后让每个Fragment都继承自该底层Fragment。--简洁
 */

public abstract class BaseFragment extends Fragment {

    //创建一些被继承的Fragment所可能需要用到的变量。在该底层Fragment中通过一些方法将其返回。
    private Context mContext;
    private View mContentView;//需要通过mContentView来获取Fragment布局里面的控件


    /**
     * function:重写Fragment的onCreateView()方法，然后可以在这个方法中通过LayoutInflater的inflate()方法
     * 将相对应的布局动态加载进来。(通过用户所设置的相对应的布局id来获取布局)
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(setLayoutResourceID(), container, false);
        mContext = getContext();
        init();
        setUpView();
        initEvents();
        return mContentView;
    }


    /**
     * function:初始化一些(必要的)事件
     */
    protected void initEvents() {
    }


    /**
     * function:初始化数据
     */
    protected void setUpData() {
    }


    /**
     * function:初始化view
     */
    protected void setUpView() {
    }

    /**
     * function:初始化操作
     */
    protected void init() {

    }

    /**
     * function:设置视图id
     *
     * @return
     */
    protected abstract int setLayoutResourceID();


    /**
     * function:供子类调用获取view视图
     *
     * @return
     */
    protected View getContentView() {
        return mContentView;
    }


    /**
     * function:供子类调用获取context
     *
     * @return
     */
    public Context getmContext() {
        return mContext;
    }

    /**
     * function:绑定View视图
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T $(int id) {
        return (T) mContentView.findViewById(id);
    }


    /**
     * function:跳转到另外一个Activity(不携带额外的信息)--供子类调用
     *
     * @param clazz
     */
    protected void startActivityWithoutExtras(Class<?> clazz) {

        Intent intent = new Intent(getActivity(), clazz);
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
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtras(extras);
        startActivity(intent);
    }


    /**
     * function:携带数据
     * @param clazz
     * @param extras
     * @param requestCode
     */
    protected void startActivityForResult(Class<?> clazz, Bundle extras, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtras(extras);
        startActivityForResult(intent, requestCode);
    }

    /**
     * function:不携带数据
     * @param clazz
     * @param requestCode
     */
    protected void startActivityForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivityForResult(intent, requestCode);
    }


}



