package com.joycity.joyclub.we_chat.pay.wechat;


import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 统一下单内容,预支付交易单
 */
public class PreOrder {
	/**
	 * 商品描述
	 */
	private String body;
	
	/**
	 * 商户订单号
	 */
	private String outTradeNo;
	
	/**
	 * 总金额 精确到分
	 */
	private Integer totalFee;
	
	/**
	 * 终端ip 可以null
	 */
	private String spbillCreateIp;
	
	/**
	 * 商品id
	 */
	private String productId;
	
	/**
	 * 描述
	 * 用来填写使用积分
	 */
	private String detail;
	
	private String openId;
	
	private String notifyUrl;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
