package com.liangjing.hemodialysisproject.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liangjing on 2017/9/2.
 * <p>
 * function:拦截器--添加cookie到请求头中
 */

public class AddCookiesInterceptor implements Interceptor {

    private Context context;
    private String lang;

    public AddCookiesInterceptor(Context context, String language) {
        super();
        this.context = context;
        this.lang = language;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain == null)
            Log.d("RetrofitClent", "Addchain == null");

        final Request.Builder builder = chain.request().newBuilder();
        //利用sharedPreferences将cookie存储
        SharedPreferences sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
        Flowable.just(sharedPreferences.getString("cookie", ""))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String cookie) throws Exception {
                        if (cookie.contains("lang=ch")) {
                            cookie = cookie.replace("lang=ch", "lang=" + lang);
                        }
                        if (cookie.contains("lang=en")) {
                            cookie = cookie.replace("lang=en", "lang=" + lang);
                        }
                        Log.d("RetrofitClent", "AddCookiesInterceptor: " + cookie);
                        builder.addHeader("cookie", cookie);
                    }
                });
        return chain.proceed(builder.build());
    }
}
