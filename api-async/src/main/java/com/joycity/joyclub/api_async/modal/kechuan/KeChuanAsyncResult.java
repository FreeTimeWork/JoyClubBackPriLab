package com.joycity.joyclub.api_async.modal.kechuan;

/**
 * KechuanAsyncResult
 * 科传接口的返回数据格式
 *
 * @author CallMeXYZ
 * @date 2017/8/11
 */
public class KeChuanAsyncResult {
    /* 请求唯一标识 */
    private String uuid;
    /* 0 错误 1 成功 */
    private Integer status;
    /* 错误信息 */
    private String message;

    public KeChuanAsyncResult() {
    }

    public KeChuanAsyncResult(String uuid) {
        this.uuid = uuid;
        this.status = 1;
    }

    public void setError(String msg) {
        setStatus(0);
        setMessage(msg);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
