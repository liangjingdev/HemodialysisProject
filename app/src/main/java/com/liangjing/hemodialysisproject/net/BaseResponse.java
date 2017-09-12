package com.liangjing.hemodialysisproject.net;

/**
 * Created by liangjing on 2017/8/27.
 * <p>
 * function:网络请求返回的基类--支持泛型
 */

public class BaseResponse<T> {

    private int code; //响应码
    private String msg; //响应信息
    private T data; //网络请求返回的数据

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * function:根据项目情况来进行指定,用于判断网络请求是否成功
     * @return
     */
    public boolean isOk() {
        return code == 0;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
