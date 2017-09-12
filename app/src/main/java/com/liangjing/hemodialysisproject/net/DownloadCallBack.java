package com.liangjing.hemodialysisproject.net;

/**
 * Created by liangjing on 2017/9/2.
 * <p>
 * function:下载文件时的回调接口(没有以abstract开头定义的方法，调用层可根据自身需要来决定是否要调用，但是以abstract开头的方法(接口)，
 * 当你在调用层new该callback的时候一定会回调--下载失败和下载成功，这两个都是有必要进行处理的)
 * 这就是抽象类的好处，可以对某些东西进行一层外包装(处理)再返回给外层
 */

public abstract class DownloadCallBack {

    //下载开始时的回调接口
    public void start() {
    }

    //下载完成后的回调接口
    public void onCompleted() {
    }

    //下载过程中的回调接口
    public void onProgress(long fileSizeDownloaded) {
    }

    //下载文件发生错误时的回调接口
    abstract public void onError(Throwable e);

    //下载文件成功时的回调接口
    abstract public void onSuccess(String path, String name, long fileSize);
}
