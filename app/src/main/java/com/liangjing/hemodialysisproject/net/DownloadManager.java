package com.liangjing.hemodialysisproject.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Created by liangjing on 2017/9/2.
 * <p>
 * function:下载管理类
 */

public class DownloadManager {

    private DownloadCallBack callBack;

    private static final String TAG = "DownLoadManager";

    private static DownloadManager sInstance;

    private Handler handler;

    //三种下载内容类型
    private static String APK_CONTENTTYPE = "application/vnd.android.package-archive";
    private static String PNG_CONTENTTYPE = "image/png";
    private static String JPG_CONTENTTYPE = "image/jpg";

    //所需要下载的文件的后缀
    private static String fileSuffix = "";

    private DownloadManager(DownloadCallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * function:单例模式构建下载文件管理器
     *
     * @param callBack 回调接口类
     * @return
     */
    public static synchronized DownloadManager getInstance(DownloadCallBack callBack) {
        if (sInstance == null) {
            sInstance = new DownloadManager(callBack);
        }
        return sInstance;
    }


    /**
     * function：将文件写入到硬盘中
     *
     * @param context
     * @param body
     * @return
     */
    public boolean writeResponseBodyToDisk(Context context, ResponseBody body) {
        Log.d(TAG, "contentType:>>>>" + body.contentType().toString());

        //首先判断服务器返回来的文件(ResponseBody的内容)的类型
        String type = body.contentType().toString();

        if (type.equals(APK_CONTENTTYPE)) {
            fileSuffix = ".apk";
        } else if (type.equals(PNG_CONTENTTYPE)) {
            fileSuffix = ".png";
        } else if (type.equals(JPG_CONTENTTYPE)) {
            fileSuffix = ".jpg";
        }

        //其它同上，可以自行添加

        //命名下载内容的文件名
        final String fileName = System.currentTimeMillis() + fileSuffix;
        //指定下载路径
        final String filePath = context.getExternalFilesDir(null) + File.separator + fileName;

        Log.d(TAG, "path:>>>>" + filePath);

        try {
            //创建File对象
            File file = new File(filePath);

            if (file.exists()) {
                file.delete();
            }

            InputStream in = null;
            OutputStream out = null;
            byte[] fileReader = new byte[0];
            final long fileSize;
            long fileSizeDownloaded = 0;

            try {
                //容器
                fileReader = new byte[4096];

                //所下载文件内容的长度
                fileSize = body.contentLength();

                //已下载内容的长度
                fileSizeDownloaded = 0;
                Log.d(TAG, "file length: " + fileSize);

                in = body.byteStream();
                out = new FileOutputStream(file);

                while (true) {
                    //读取数据
                    int read = in.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    //写入到File中
                    out.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);

                    //回调到外层
                    if (callBack != null) {
                        //注意:要在主线程当中进行回调，更新进度UI
                        handler = new Handler(Looper.getMainLooper());
                        final long finalFileSizeDownloaded = fileSizeDownloaded;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onProgress(finalFileSizeDownloaded);
                            }
                        });
                    }
                }

                out.flush();
                Log.d(TAG, "file downloaded: " + fileSizeDownloaded + " of " + fileSize);
                if (callBack != null) {
                    handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSuccess(filePath, fileName, fileSize);
                        }
                    });
                    Log.d(TAG, "file downloaded: " + fileSizeDownloaded + " of " + fileSize);
                }

                return true;
            } catch (IOException e) {
                if (callBack != null) {
                    callBack.onError(e);
                }
                return false;
            } finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }

        } catch (IOException e) {
            if (callBack != null) {
                callBack.onError(e);
            }
            return false;
        }

    }
}
