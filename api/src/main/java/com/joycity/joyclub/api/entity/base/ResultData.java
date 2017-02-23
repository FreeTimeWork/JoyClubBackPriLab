package com.joycity.joyclub.api.entity.base;

import com.joycity.joyclub.api.constant.ResultCode;

import java.util.Objects;

/**
 * Created by CallMeXYZ on 2017/2/21.
 * http数据返回的基础类
 * code类型请看{@link ResultCode}
 */
public class ResultData {
    private Integer code;
    private String msg;
    private Objects data;

    public ResultData(Integer code) {
        this.code = code;
    }

    public ResultData(Integer code, String msg) {
        this(code);
        this.msg = msg;
    }

    public ResultData(Integer code, String msg, Objects data) {
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

    public Objects getData() {
        return data;
    }

    public void setData(Objects data) {
        this.data = data;
    }
}
