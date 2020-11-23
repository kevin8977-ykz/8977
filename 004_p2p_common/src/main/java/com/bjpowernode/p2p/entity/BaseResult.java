package com.bjpowernode.p2p.entity;

import java.io.Serializable;

/**
 *  以前都是返回一个Map集合
 *      转换成json：
 *      {
 *          "success":true/false,
 *          "msg":xxx,
 *          "data":{}/[]
 *      }
 *  公共的返回结果集
 */
public class BaseResult<T> implements Serializable {

    private boolean success;

    private String msg;

    private T data;


    public static BaseResult success() {
        BaseResult baseResult = new BaseResult();
        baseResult.setSuccess(true);
        baseResult.setMsg("请求成功");
        return baseResult;
    }

    public static BaseResult error() {
        BaseResult baseResult = new BaseResult();
        baseResult.setSuccess(false);
        baseResult.setMsg("请求失败");
        return baseResult;
    }

    public static BaseResult<Object> success(Object data) {
        BaseResult<Object> baseResult = new BaseResult<>();
        baseResult.setSuccess(true);
        baseResult.setMsg("请求成功");
        baseResult.setData(data);
        return baseResult;
    }

    public static BaseResult error(String msg) {
        BaseResult baseResult = new BaseResult();
        baseResult.setSuccess(false);
        baseResult.setMsg(msg);
        return baseResult;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    @Override
    public String toString() {
        return "BaseResult{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
