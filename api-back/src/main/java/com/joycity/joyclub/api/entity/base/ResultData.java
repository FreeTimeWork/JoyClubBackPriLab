package com.joycity.joyclub.api.entity.base;

import com.joycity.joyclub.api.constant.ResultCode;

import java.util.Objects;

import static com.joycity.joyclub.api.constant.ResultCode.SUCCESS;

/**
 * Created by CallMeXYZ on 2017/2/21.
 * api业务正确时，http数据返回的基础类
 * code类型请看{@link ResultCode}
 * 如果正确返回结构为<code>{data:object,code:200,}</code>
 * 如果出错返回结构未<code>{error:stringMsg,code:errorCode,}</code>
 */
public class ResultData {
    private Integer code;
    private Object data;

    public ResultData() {
        this.code = SUCCESS;
    }

    public ResultData(Object data) {
        this();
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
