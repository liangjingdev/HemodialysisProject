package com.liangjing.hemodialysisproject.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.facebook.stetho.Stetho;
import com.liangjing.hemodialysisproject.db.DbCore;
import com.liangjing.hemodialysisproject.loader.UniImageLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;


/**
 * Created by liangjing on 2017/8/17.
 * <p>
 * function:自定义Application
 */

public class MyApplication extends Application {

    //以下属性应用于整个应用程序，合理利用资源，减少资源浪费
    private static Context mContext;//上下文
    private static Thread mMainThread;//主线程
    private static long mMainThreadId;//主线程id
    private static Looper mMainLooper;//循环队列
    private static Handler mHandler;//主线程Handler

    @Override
    public void onCreate() {
        super.onCreate();


        //初始化数据库
        DbCore.getInstance().init(this);

        //初始化ImagePicker
        initImagePicker();

        //初始化stetho工具
        Stetho.initializeWithDefaults(this);
    }

    /**
     * function:初始化仿微信图片选择器ImagePicker
     */
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        //需要将图片加载器设置进去
        imagePicker.setImageLoader(new UniImageLoader());
        imagePicker.setShowCamera(true);
        imagePicker.setCrop(true);
        imagePicker.setSaveRectangle(true);
        imagePicker.setMultiMode(false);
        imagePicker.setStyle(CropImageView.Style.CIRCLE);
        imagePicker.setFocusWidth(500);
        imagePicker.setFocusHeight(500);
        imagePicker.setOutPutX(700);
        imagePicker.setOutPutY(700);
    }


    /**
     * function:获取Application上下文
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        MyApplication.mContext = mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static void setMainThread(Thread mMainThread) {
        MyApplication.mMainThread = mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static void setMainThreadId(long mMainThreadId) {
        MyApplication.mMainThreadId = mMainThreadId;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static void setMainThreadLooper(Looper mMainLooper) {
        MyApplication.mMainLooper = mMainLooper;
    }

    /**
     * function:得到主线程Handler
     *
     * @return
     */
    public static Handler getMainHandler() {
        return mHandler;
    }

    public static void setMainHandler(Handler mHandler) {
        MyApplication.mHandler = mHandler;
    }

}
