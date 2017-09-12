package com.liangjing.hemodialysisproject.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by liangjing on 2017/9/1.
 *
 * function:对subscriber的一层外包装(预先处理一些事件－－如检查网络是否可用，在错误回调中进行检验错误原因并且打印错误日志等等、、)
 * 很多时候我们需要借用RxJava开启多个observable去读取网络，这是我们对不同Subscriber处理起来比较麻烦，因此统一对Subscriber对网络返回进行处理和， 有无网络做判断，甚至可以根据需求显示加载进度等
 * 构建抽象的BaseSubscribe类，只处理start()和onCompleted（） ，上层处理时只处理onError（）和onNext（）
 */

public abstract class BaseSubscriber<T> extends DisposableSubscriber<T> {

    private Context context;
    private ProgressDialog dialog; //加载进度对话框

    public BaseSubscriber(Context context){
        this.context = context;
        dialog = new ProgressDialog(context);
        dialog.setMessage("拼命加载中...");
    }



    /**
     * function:当订阅后，会首先调用这个方法
     */
    @Override
    protected void onStart() {
        super.onStart();

        Toast.makeText(context,"http is start",Toast.LENGTH_SHORT).show();

        //判断网络是否可用
        if (!NetworkUtil.isNetworkAvailable(context)){
            Toast.makeText(context,"无网络,读取缓存数据",Toast.LENGTH_SHORT).show();
            // **一定要主动调用下面这一句**
            onComplete();
        }

        //若网络可用，则显示"正在加载"对话框
        if (dialog != null){
            if (dialog.isShowing()){
                dialog.dismiss();
            }
            dialog.show();
        }

    }


    /**
     * function:事件队列异常。在事件处理过程中出异常时，onError() 会被触发，同时队列自动终止，不允许再有事件发出。
     * @param e 异常
     */
    @Override
    public void onError(Throwable e) {

        if (dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }

        //判断抛出的异常是否存在于ExceptionHandle所列出的那些异常当中,若存在，则将异常信息(导致异常的原因)返回给调用层
        if (e instanceof ExceptionHandle.ResponseThrowable){
            onError((ExceptionHandle.ResponseThrowable)e);
        }else {
            //若不存在，则告知调用层该异常的原因没有判断出来
            onError(new ExceptionHandle.ResponseThrowable(e,ExceptionHandle.ERROR.UNKNOWN));
        }

    }

    /**
     * function:事件队列完结时调用该方法。RxJava 不仅把每个事件单独处理，还会把它们看做一个队列。
     */
    @Override
    public void onComplete() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        Toast.makeText(context, "http is Complete", Toast.LENGTH_SHORT).show();
    }


    //将错误信息回调给上层(调用层)
    public abstract void onError(ExceptionHandle.ResponseThrowable e);

}
