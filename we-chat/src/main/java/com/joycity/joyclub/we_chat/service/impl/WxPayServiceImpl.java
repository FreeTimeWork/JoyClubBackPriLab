package com.joycity.joyclub.we_chat.service.impl;




import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.order.PreOrderResult;
import com.joycity.joyclub.we_chat.pay.wechat.PreOrder;
import com.joycity.joyclub.we_chat.pay.wechat.SignUtils;
import com.joycity.joyclub.we_chat.pay.wechat.WxPayConfig;
import com.joycity.joyclub.we_chat.pay.wechat.WxRefundRequest;
import com.joycity.joyclub.we_chat.service.WechatOpenIdService;
import com.joycity.joyclub.we_chat.service.WxPayService;
import com.joycity.joyclub.we_chat.util.HttpKit;
import com.joycity.joyclub.we_chat.util.WechatXmlUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.joycity.joyclub.commons.constant.ResultCode.DATA_NOT_EXIST;
import static com.joycity.joyclub.commons.constant.ResultCode.WECHAT_PAY_REQUEST_ERROR;

@Service
public class WxPayServiceImpl implements WxPayService{
    /**
     * 生成prepayid的网址
     */
    private static final String URL_PREPAY = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 退款网址
     */
    private static final String URL_REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    private Log logger = LogFactory.getLog(WxPayServiceImpl.class);
    @Autowired
    private WxPayConfig wxPayConfig;
    @Autowired
    private WechatOpenIdService wechatOpenIdService;
    // TODO: 2017/5/11  增加项目的微信支付传参传参

    @Override
    public PreOrderResult getWechatPreOrderResult(Long projectId, Long clientId, Float moneySum, String code, String wxPayNotifyUrl) {
        //先判断openid存在性
        //getOpneId需要的所在的微信项目id,如果是平台悦客会或者购物中心，微信项目id就是项目id，如果是商业或者地产悦客会，微信项目id对应的是商业悦客会的项目id
        String openId = wechatOpenIdService.getOpenId(projectId, clientId);
        if (openId == null) {
            throw new BusinessException(DATA_NOT_EXIST, "会员微信openId获取失败");
        }
        PreOrderResult preOrderResult = new PreOrderResult();
        //涉及金钱，应该等微信支付回调在处理积分
        preOrderResult.setIfUseMoney(true);
        preOrderResult.setPayParam(getWechatPayParams(projectId, openId, moneySum, code,wxPayNotifyUrl));
        return preOrderResult;
    }

    private Map<String, Object> getWechatPayParams(Long projectId, String openId, float moneySum, String sysOrderCode, String wxPayNotifyUrl) {
        //涉及金钱，应该等微信支付回调在处理积分
        PreOrder preOrder = new PreOrder();
        preOrder.setBody("悦客会");
        preOrder.setOpenId(openId);
        //转成分
        preOrder.setTotalFee((int) Math.ceil(moneySum * 100));
        preOrder.setOutTradeNo(sysOrderCode);

        // TODO: 2017/5/11  增加项目的微信支付传参传参
        preOrder.setNotifyUrl(wxPayNotifyUrl);
        String prepayResultStr = precreate(preOrder);

        Map<String, String> prepayResult = WechatXmlUtil.xmlToMap(prepayResultStr);
        String prepay_id = prepayResult.get("prepay_id");
        if (prepay_id == null) {
            logger.error("微信统一下单失败,错误返回值为 " + prepayResultStr);
            throw new BusinessException(ResultCode.WECHAT_PAY_REQUEST_ERROR, "微信统一下单失败");
        }
        return getWechatPayParams(prepay_id);
    }

    private Map<String, Object> getWechatPayParams(String prepayId) {
        Map<String, Object> param = new HashMap<>();
        param.put("appId", wxPayConfig.getAppid());
        param.put("timeStamp", String.valueOf(new Date().getTime()));
        param.put("nonceStr", "12345678");
        param.put("package", "prepay_id=" + prepayId);
        param.put("signType", "MD5");
        String sign = SignUtils.paySign(param, wxPayConfig.getSign());
        param.put("paySign", sign.toUpperCase());
        return param;
    }

    /**
     * @param precreateRequest
     * @return
     */
    private String precreate(PreOrder precreateRequest) {
        Map<String, Object> params = getSubmitMap();
        params.put("out_trade_no", precreateRequest.getOutTradeNo());
        params.put("body", precreateRequest.getBody());
        //微信则需提供到分
        params.put("total_fee", String.valueOf(Math.round(precreateRequest.getTotalFee() ))); // 总金额
        params.put("spbill_create_ip", "127.0.0.1"); // ip
//		params.put("product_id", );
        params.put("notify_url", precreateRequest.getNotifyUrl());
        params.put("trade_type", "JSAPI");
        params.put("openid", precreateRequest.getOpenId());
        sign(params);
//
        String out = null;
        try {
            out = HttpKit.post(
                    URL_PREPAY,
                    WechatXmlUtil.mapToXml(params));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (out == null) {
            throw new BusinessException(WECHAT_PAY_REQUEST_ERROR);
        }
        return out;
    }

    public String refund(WxRefundRequest refundRequest) {
        Map<String, Object> params = getSubmitMap();
        if (!StringUtils.isBlank(refundRequest.getTransactionId())) {
            params.put("transaction_id", refundRequest.getTransactionId());
        }
        if (!StringUtils.isBlank(refundRequest.getOutTradeNo())) {
            params.put("out_trade_no", refundRequest.getOutTradeNo());
        }
        params.put("out_refund_no", refundRequest.getOutRefundNo());
        Double totolFee = Double.valueOf(refundRequest.getTotalFee());
        params.put("total_fee", String.valueOf(Math.round(totolFee * 100)));
        Double refundFee = Double.valueOf(refundRequest.getRefundFee());
        params.put("refund_fee", String.valueOf(Math.round(refundFee * 100)));
        params.put("op_user_id", refundRequest.getOpUserId());
        sign(params);
        String in = WechatXmlUtil.mapToXml(params);
        String resultXml = null;
        try {
            resultXml = cert(in,
                    URL_REFUND);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("微信退款异常", e);
        }
        if (resultXml == null) {
            throw new RuntimeException("微信支付异常......... wxpay error");

        }
        return resultXml;
    }

    /**
     * 微信请求数据的前几个
     *
     * @return
     */
    private Map<String, Object> getSubmitMap() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appid", wxPayConfig.getAppid());
        params.put("mch_id", wxPayConfig.getMchid());
        params.put("nonce_str", RandomStringUtils.random(8, "123456789"));
//		if(StringUtils.isNotEmpty(wxPayConfig.getSubAppid())) {
//			params.put("sub_appid", wxPayConfig.getSubAppid());
//		}
//		if(StringUtils.isNotEmpty(wxPayConfig.getSubMchid())) {
//			params.put("sub_mch_id", wxPayConfig.getSubMchid());
//		}
        return params;
    }

    private String cert(String xml, String url) throws Exception {
        String certPath = wxPayConfig.getCert();
        String certPassword = wxPayConfig.getMchid();
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(certPath));
        try {
            keyStore.load(instream, certPassword.toCharArray());
        } finally {
            instream.close();
        }
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, certPassword.toCharArray()).build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext, new String[]{"TLSv1"}, null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf).build();
        try {
            HttpPost httppost = new HttpPost(url);
            HttpEntity requestEntity = new StringEntity(xml,
                    ContentType.create("text/html", "GBK"));
            httppost.setEntity(requestEntity);


            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                StringBuffer sb = new StringBuffer();
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(entity.getContent()));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        sb.append(text);
                    }
                }
                EntityUtils.consume(requestEntity);
                EntityUtils.consume(entity);
                String result = new String(sb.toString().getBytes(), "UTf-8");
                return result;
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }


    private void sign(Map<String, Object> params) {
        String sign = SignUtils.paySign(params, wxPayConfig.getSign());
        params.put("sign", sign.toUpperCase());
    }


}
