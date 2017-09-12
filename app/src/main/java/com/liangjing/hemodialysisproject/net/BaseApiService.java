package com.liangjing.hemodialysisproject.net;


import com.liangjing.hemodialysisproject.bean.IpResult;
import com.liangjing.hemodialysisproject.bean.UserBean;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by liangjing on 2017/8/27.
 * <p>
 * function:请求网络的API接口类，在这里可以添加加你需要的请求接口，也可复用已经实现了的几个方法。
 */

public interface BaseApiService {

    String BASE_URL = "http://ip.taobao.com/";

    /**
     * function:发起get请求、
     * @param url 地址
     * @param maps 参数-键值对
     * @return
     */
    @GET
    Flowable<BaseResponse<Object>> get(@Url String url, @QueryMap Map<String,String> maps);


    /**
     * function:发起post请求，发送表单内容至服务器
     * @param url 地址
     * @param maps 表单内容键值对
     * @return ResponseBody--响应体--服务端返回的内容
     */
    @FormUrlEncoded
    @POST
    Flowable<ResponseBody> post(@Url String url, @FieldMap Map<String,String> maps);


    /**
     * function:发起post请求，发送文件至服务器(比如用户头像，此处为单文件上传)
     * @param url
     * @param requestBody
     * @return
     */
    @Multipart
    @POST
    Flowable<ResponseBody> uploadFile(@Url String url,@Body RequestBody requestBody);


    /**
     * function:下载文件
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    Flowable<ResponseBody> downloadFile(@Url String fileUrl);

    /**
     *普通写法
     */
    @GET("service/getIpInfo.php")
    Flowable<BaseResponse<IpResult>> getData(@Query("ip") String ip);


    /**
     * function:获取用户个人信息
     * @return
     */
    @GET("SSM/patient/getPreviousIntro.do")
    Flowable<BaseResponse<UserBean>> getUserBean();


    /**
     * function:发起post请求，上传json数据(用户个人资料)至服务器
     * @param url
     * @param jsonStr 请求体
     * @return
     */
    @POST
    Flowable<ResponseBody> json(@Url String url,@Body RequestBody jsonStr);



}
