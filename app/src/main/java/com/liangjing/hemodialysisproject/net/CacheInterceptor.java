package com.liangjing.hemodialysisproject.net;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liangjing on 2017/9/2.
 * <p>
 * function:拦截器--设置缓存机制
 */

public class CacheInterceptor implements Interceptor {

    private Context context;

    //指示客户端可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户端可以接收超出超时期指定值之内的响应消息。(set cache times is 3 days)
    int maxStale = 60 * 60 * 24 * 3;

    public CacheInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //截获网络请求,然后进行相关操作后推进请求，获取Response
        Request request = chain.request();

        //如果网络可用
        if (NetworkUtil.isNetworkAvailable(context)) {

            Response response = chain.proceed(request);

            // read from cache for 60 s
            int maxAge = 60;
            String cacheControl = request.cacheControl().toString();
            Log.d("jing", "60s load cache" + cacheControl);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public,max-age=" + maxAge)
                    .build();
        } else {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "当前无网络! 为你智能加载缓存", Toast.LENGTH_SHORT).show();
                }
            });
            Log.d("jing", " no network load cahe");
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public,only-if-cache,max-stale" + maxStale)
                    .build();
        }
    }
}
