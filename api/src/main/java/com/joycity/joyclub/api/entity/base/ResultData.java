package com.joycity.joyclub.api.entity.base;

import com.joycity.joyclub.api.constant.ResultCode;

import java.util.Objects;

/**
 * Created by CallMeXYZ on 2017/2/21.
 * http数据返回的基础类
 * code类型请看{@link ResultCode}
 * 如果正确返回结构为<code>{data:object,code:200,}</code>
 * 如果出错返回结构未<code>{error:stringMsg,code:errorCode,}</code>
 */
public class ResultData {
    private Integer code;
    private String msg;
    private Object data;

    public ResultData(Integer code) {
        this.code = code;
    }

    public ResultData(Integer code, String msg) {
        this(code);
        this.msg = msg;
    }

    public ResultData(Integer code, String msg, Object data) {
        this(code, msg);
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
