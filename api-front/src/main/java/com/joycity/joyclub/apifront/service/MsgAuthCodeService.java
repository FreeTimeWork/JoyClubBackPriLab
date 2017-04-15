package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.commons.modal.base.ResultData;

import java.util.List;

public interface MsgAuthCodeService {
    /**
     * 获取验证码
     * 会抛出今日已达上线次数的BusinessException
     *
     * @param phone
     * @return
     */
    ResultData getAndSendAuthCode(String phone);

    /**
     * 获取验证码
     * 会抛出今日已达上线次数的BusinessException
     *
     * @param phone
     * @return
     */
    String getAuthCode(String phone);

    /**
     * 存到数据库
     *
     * @param phone
     * @param code
     */
    void saveAuthCode(String phone, String code);

    /**
     * 验证有效性
     * 会抛出验证码未发送，验证码已过期，验证码不正确的的BusinessException
     *
     * @param phone
     * @param code
     * @return
     */
    void checkAuthCode(String phone, String code);


    /**
     * 发送短信
     * 会抛出验证码发送异常，发送短信验证码失败的的BusinessException
     *
     * @param content
     * @param phone
     */
    void sendMessageCode(String content, List<String> phone);
}
