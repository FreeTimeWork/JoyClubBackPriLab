package com.joycity.joyclub.commons.modal.base;


/**
 * Created by CallMeXYZ on 2017/2/21.
 * api业务失败时，http数据返回的基础类
 * code类型请看所属模块的ResultCode
 * <p>
 * 返回结构：<code>{code:errorCode,msg:stringMsg,error:object}</code>
 */
public class ResultError {
    private Integer code;
    /**
     * 错误信息
     */
    private String msg;
    /**
     * 错误对象
     */
    private Object error;

    public ResultError(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultError(Integer code, String msg, Object error) {
        this(code, msg);
        this.error = error;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }
}
