package com.joycity.joyclub.commons.modal.base;


import static com.joycity.joyclub.commons.constants.ResultCode.SUCCESS;

/**
 * Created by CallMeXYZ on 2017/2/21.
 * api业务正确时，http数据返回的基础类
 * code类型请看{@link com.joycity.joyclub.commons.constants.ResultCode}
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
