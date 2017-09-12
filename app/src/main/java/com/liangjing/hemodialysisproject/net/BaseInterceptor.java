package com.liangjing.hemodialysisproject.net;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liangjing on 2017/8/27.
 * <p>
 * function:构建基础拦截器--用来设置请求头header，这里是通过MAP键值对来构建，将header加入到Request中。
 *          使用addHeader()不会覆盖之前设置的header,若使用header()则会覆盖之前的header
 */

public class BaseInterceptor implements Interceptor {

    private Map<String, String> headers;

    public BaseInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();

        if (headers != null && headers.size() > 0) {

            //拿到所有键值对的key
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, headers.get(headerKey));
            }
        }
        return chain.proceed(builder.build());
    }
}
