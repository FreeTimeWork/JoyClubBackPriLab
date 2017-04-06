package com.joycity.joyclub.apifront.exception;



/**
 * Created by CallMeXYZ on 2017/3/20.
 * 业务逻辑的异常,应该自己实现{@link #getMessageByCode()},实现ExceptionHandler捕获并返回｛code:{},error:{}｝
 */
public abstract class BaseBusinessException extends RuntimeException {
    private String message;
    private Integer code;

    public BaseBusinessException(Integer code) {
        super();
        this.code = code;
    }
    public BaseBusinessException(Integer code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }



    public abstract String getMessageByCode();

    @Override
    public String getMessage() {
        return message == null ? getMessageByCode() : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
