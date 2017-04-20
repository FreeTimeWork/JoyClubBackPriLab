package com.joycity.joyclub.apifront.pay.wechat;


import com.joycity.joyclub.apifront.exception.BusinessException;
import com.joycity.joyclub.apifront.util.HttpKit;
import com.joycity.joyclub.apifront.util.WechatXmlUtil;
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
import java.util.HashMap;
import java.util.Map;

import static com.joycity.joyclub.apifront.constant.ResultCode.WECHAT_PAY_REQUEST_ERROR;

@Service
public class WxPayService {
    private Log logger = LogFactory.getLog(WxPayService.class);
    @Autowired
    private WxPayConfig wxPayConfig;
    /**
     * 生成prepayid的网址
     */
    private static final String URL_PREPAY = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 退款网址
     */
    private static final String URL_REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    public String precreate(PreOrder precreateRequest) {
        Map<String, Object> params = getSubmitMap();
        params.put("out_trade_no", precreateRequest.getOutTradeNo());
        params.put("body", precreateRequest.getBody());
        //系统里的总金额都是精确到元，微信则需提供到分
        params.put("total_fee", String.valueOf(Math.round(precreateRequest.getTotalFee() * 100))); // 总金额
        params.put("spbill_create_ip", "127.0.0.1"); // ip
//		params.put("product_id", );
        params.put("notify_url", wxPayConfig.getNotifyUrl());
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

            System.out.println("executing request" + httppost.getRequestLine());

            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                StringBuffer sb = new StringBuffer();
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    System.out.println("Response content length: "
                            + entity.getContentLength());
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
