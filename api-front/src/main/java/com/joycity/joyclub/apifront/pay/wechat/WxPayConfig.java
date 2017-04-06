package com.joycity.joyclub.apifront.pay.wechat;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WxPayConfig {
	
	/**
	 * 公众账号ID
	 */
	@Value("${wxpay.appid}")
	private String appid;
	
	/**
	 * 商户号
	 */
	@Value("${wxpay.mchid}")
	private String mchid;
	
	/**
	 * 服务商appid
	 */
	@Value("${wxpay.subAppid}")
	private String subAppid;
	
	/**
	 * 服务商商户号
	 */
	@Value("${wxpay.subMchid}")
	private String subMchid;
	
	/**
	 * 签名
	 */
	@Value("${wxpay.sign}")
	private String sign;
	
	/**
	 * 证书
	 */
	@Value("${wxpay.cert}")
	private String cert;
	
	/**
	 * 通知地址
	 */
	@Value("${wxpay.notifyUrl}")
	private String notifyUrl;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMchid() {
		return mchid;
	}

	public void setMchid(String mchid) {
		this.mchid = mchid;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getCert() {
		return cert;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}

	public String getSubAppid() {
		return subAppid;
	}

	public void setSubAppid(String subAppid) {
		this.subAppid = subAppid;
	}

	public String getSubMchid() {
		return subMchid;
	}

	public void setSubMchid(String subMchid) {
		this.subMchid = subMchid;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	
}
