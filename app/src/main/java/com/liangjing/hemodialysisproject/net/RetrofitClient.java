package com.liangjing.hemodialysisproject.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.reactivestreams.Publisher;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by liangjing on 2017/8/27.
 * <p>
 * function:创建以及管理Retrofit、调度分发请求(RetrofitClient客户端)--封装
 * 采用单例模式构建RetrofitClient客户端(重写多个构造函数--供外界传入参数)
 */

public class RetrofitClient {

    private static final int DEFAULT_TIMEOUT = 30;//默认的连接(读写 )超时时间
    private static OkHttpClient okHttpClient;
    private static Context mContext;
    public static String baseUrl = BaseApiService.BASE_URL; //默认的url地址
    private static Retrofit retrofit;
    private BaseApiService apiService; //网络请求接口类
    private static File httpCacheDirectory;
    private Cache cache = null;

    //创建该对象的目的是：外层可能需要对okHttpClient对象进行改造(修改)，对OkHttpClient对象改造完之后，就需要生成一个新的Retrofit对象，就需要利用到Retrofit.Builder
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(baseUrl);

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder()
                    .addNetworkInterceptor(
                            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);


    /*================== 单例模式获取RetrofitClient对象 begin ==================*/
    private static class SingleHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient(mContext);
    }

    public static RetrofitClient getInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return SingleHolder.INSTANCE;
    }


    public static RetrofitClient getInstance(Context context, String url) {
        if (context != null) {
            mContext = context;
        }
        return new RetrofitClient(context, url);
    }

    public static RetrofitClient getInstance(Context context, String url, Map<String, String> headers) {
        if (context != null) {
            mContext = context;
        }
        return new RetrofitClient(context, url, headers);
    }

    /*================== 单例模式获取RetrofitClient对象 end ==================*/


    /*================== 构造方法 begin ==================*/
    private RetrofitClient() {
    }

    private RetrofitClient(Context context) {
        this(context, baseUrl, null);
    }


    private RetrofitClient(Context context, String url) {
        this(context, url, null);
    }

    private RetrofitClient(Context context, String url, Map<String, String> headers) {
        //首先检验传入的url是否为空，若为空，则用baseUrl来代替
        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }

        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(mContext.getCacheDir(), "jing-cache");
        }

        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
            }
        } catch (Exception e) {
            Log.e("OKHttp", "Could not create http cache", e);
        }

        //创建OkHttpClient对象
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(cache)
                .addInterceptor(new CacheInterceptor(context))
                .addInterceptor(new AddCookiesInterceptor(context, "ch"))
                .addInterceptor(new ReceivedCookiesInterceptor(context))
                .addNetworkInterceptor(new CacheInterceptor(context))
                .addInterceptor(new BaseInterceptor(headers))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        //创建retrofit对象
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();

    }

    /*================== 构造方法 end ==================*/


    /**
     * function:创建BaseApiService的(默认的网络请求接口类--BaseApiService.class)
     *
     * @return
     */
    public RetrofitClient createBaseApi() {
        apiService = create(BaseApiService.class);
        //返回当前对象--以便实现链式操作
        return this;
    }


    /**
     * function:创建网络请求接口类的对象(传入BaseApiService接口类或者自己所自定义的接口类，然后可得到其对象，接着可利用该对象所拥有的各种网络请求方法)
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     *
     * @param service
     * @param <T>
     * @return
     */
    private <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api Service is null");
        }
        return retrofit.create(service);
    }


    /**
     * function:利用rxJava的转换器写一个Transformer,为每一个api进行指定线程(生产和消费线程)
     * Transformer--(在转换器中可以进行转换，将当前的Flowable转化为另外一个Flowable,通过compose()方法来运用转换器)，这和内联一系列操作符有着同等功效
     *
     * @return
     */
    FlowableTransformer schedulersTransformer() {
        return flowable -> flowable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io()) //开启io线程后要记得关掉它，防止内存泄漏
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * function:应用转换器，为Flowable指定线程(生产和消费线程)
     *
     * @param <T>
     * @return
     */
    <T> FlowableTransformer<T, T> applySchedulers() {
        return (FlowableTransformer<T, T>) schedulersTransformer();
    }

    /**
     * function:利用转换器--对Flowable进行转换操作(将Flowable<BaseResponse<T>>转换为Flowable<T>,即获取对应实体类的Flowable)
     *
     * @param <T>
     * @return
     */
    public <T> FlowableTransformer<BaseResponse<T>, T> transformer() {
        return new FlowableTransformer<BaseResponse<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<BaseResponse<T>> flowable) {
                return flowable.map(new HandleFuc<T>()).onErrorResumeNext(new HttpResponseFunc<T>());
            }
        };
    }


    /**
     * function:将基类(BaseResponse)的Observable转换为网络请求后返回的实体类数据的Observable
     *
     * @param <T>
     */
    public class HandleFuc<T> implements Function<BaseResponse<T>, T> {

        @Override
        public T apply(@NonNull BaseResponse<T> tBaseResponse) throws Exception {
            if (!tBaseResponse.isOk())
                throw new RuntimeException((tBaseResponse.getCode() + "" + tBaseResponse.getMsg()).equals("") ? "" : tBaseResponse.getMsg());
            return tBaseResponse.getData();
        }
    }


    private static class HttpResponseFunc<T> implements Function<Throwable, Flowable<T>> {
        @Override
        public Flowable<T> apply(@NonNull Throwable e) throws Exception {
            return Flowable.error(ExceptionHandle.handleException(e));
        }
    }


    /**
     * function:为传进来的Flowable指定事件生产以及事件消费所在的线程(当自己创建了一个新的ApiService来使用的时候，就可以用到该方法来方便地指定线程)
     *
     * @param flowable
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> switchSchedulers(Flowable<T> flowable) {
        return flowable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * function:下载文件--观察者
     *
     * @param <ResponseBody>
     */
    class DownSubscriber<ResponseBody> extends DisposableSubscriber<ResponseBody> {

        DownloadCallBack callBack;

        //创建该观察者的构造方法--传入callback--以便在相关地方设置回调接口
        public DownSubscriber(DownloadCallBack callBack) {
            this.callBack = callBack;
        }

        @Override
        protected void onStart() {
            super.onStart();

            if (callBack != null) {
                callBack.start();
            }
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            DownloadManager.getInstance(callBack).writeResponseBodyToDisk(mContext, (okhttp3.ResponseBody) responseBody);
        }

        @Override
        public void onError(Throwable e) {
            if (callBack != null) {
                callBack.onError(e);
            }
        }

        @Override
        public void onComplete() {
            if (callBack != null) {
                callBack.onCompleted();
            }
        }

    }

    /*================== ApiService接口方法构造(对原始的flowable进行一层外包装) begin ==================*/


    public Flowable get(String url, Map parameters) {
        return apiService.get(url, parameters)
                .compose(schedulersTransformer())
                .compose(transformer());
    }

    public Flowable post(String url, Map<String, String> parameters) {
        return apiService.post(url, parameters)
                .compose(schedulersTransformer())
                .compose(transformer());
    }

    public Flowable json(String url, RequestBody jsonStr) {
        return apiService.json(url, jsonStr)
                .compose(schedulersTransformer())
                .compose(transformer());
    }

    public Flowable upload(String url, RequestBody requestBody) {
        return apiService.uploadFile(url, requestBody)
                .compose(schedulersTransformer())
                .compose(transformer());
    }


    public Flowable getData(String ip) {
        return apiService.getData(ip)
                .compose(schedulersTransformer())
                .compose(transformer());
    }

    public void download(String url, final DownloadCallBack callBack) {
        apiService.downloadFile(url)
                .compose(schedulersTransformer())
                .compose(transformer())
                .subscribe(new DownSubscriber(callBack));
    }

    public Flowable getUserBean(){
        return apiService.getUserBean()
                .compose(schedulersTransformer())
                .compose(transformer());
    }

     /*================== ApiService接口方法构造(对原始的flowable进行一层外包装) end ==================*/


    /**
     * function:修改baseUrl(注意：修改完baseUrl后要记得重新创建Retrofit.Builder()对象)
     *
     * @param newApiBaseUrl
     */
    public static void changeApiBaseUrl(String newApiBaseUrl) {
        baseUrl = newApiBaseUrl;
        builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl);
    }


    /**
     * function:添加cookie(这里添加cookie使用的是另外一种方式-用到的是CookieManage--实现对cookie管理以及持久化)
     */
    public static void addCookie() {
        okHttpClient.newBuilder().cookieJar(new CookieManager(mContext)).build();
        retrofit = builder.client(okHttpClient).build(); //重新创建一个新的Retrofit对象(--更新该类中的实例retrofit)
    }

    /**
     * function:改变请求头(可以通过newBuilder()方法来对现有的(当前的)okHttpClient实例进行构造(改造)。)
     *
     * @param newApiHeaders
     */
    public static void changeApiHeader(Map<String, String> newApiHeaders) {

        //会覆盖之前设置的
        okHttpClient.newBuilder().addInterceptor(new BaseInterceptor(newApiHeaders)).build();
        retrofit = builder.client(okHttpClient).build(); //重新创建一个新的Retrofit对象(--更新该类中的实例retrofit)


//        retrofit = builder.client(httpClient.build()).build(); --也可以利用一个新的OkHttpClient.Builder对象
    }

}