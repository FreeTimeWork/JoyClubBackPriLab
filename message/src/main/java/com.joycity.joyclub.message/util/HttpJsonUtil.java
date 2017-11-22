package com.joycity.joyclub.message.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * @Author: qiaohuizhong
 * @Name: HttpJsonUtil.java
 * @Type: JAVA
 * @Description: json格式传送
 * @Date: Created in 18:03 2017/11/15
 */
public class HttpJsonUtil {
    // ############################此部分参数需要修改############################
    public static String url_submit="http://new.yxuntong.com/emmpdata/sms/Submit";//提交短信地址
    public static String url_deliver="http://new.yxuntong.com/emmpdata/sms/Deliver";//下行地址
    public static String url_report="http://new.yxuntong.com/emmpdata/sms/Report";//状态报告地址
    public static String url_balance="http://new.yxuntong.com/emmpdata/sms/Balance";//余额提醒地址
    public static String token = "111111";//安全验证key
    public static String phone = "";//手机号码
//    public static String userName = "200110"; // 用户名
//    public static String password = "jy&yxt1219"; // 密码
    public static String userName = "2002541"; // 用户名
    public static String password = "4w$sw+'F"; // 密码
    public static String content = "你好，测试短信"; // 短信内容
    public static String sign = "【朝阳大悦城】"; // 短信内容



    public static void main(String[] args) throws Exception {
        testNewJk();
    }

    private static void testNewJk() throws Exception {
        String resp = null;

        System.out.println("*************发送短信*************");
        resp = sendJson(userName,password,url_submit,phone,content,sign,"","");
        System.out.println(resp);

        System.out.println("*************获取状态报告*************");
        resp = getReportJson(userName,password, url_report);
        System.out.println(resp);


        System.out.println("*************获取上行*************");
        resp = getSmsJson(userName,password,url_deliver);
        System.out.println(resp);

        System.out.println("*************获取金额*************");
        resp = getBalanceJson(userName,password,url_balance);
        System.out.println(resp);
    }
    /**
     * 方法描述
     * 发送短信
     */
    public static String sendJson(String userName,String password,String url_submit,String phones,String content,String sign,String subcode,String sendtime) {
        Map<String, String> params = new LinkedHashMap<String, String>();
        String message = "{'account':'"+userName+"','password':'"+MD5Encode(password)+"','phones':'"+phones+"','content':'"+content+"','sign':'"+sign+"'}";
        params.put("message", message);

        String sid= new SHA1().getDigestOfString((token+"&"+message).getBytes());
        params.put("sid", sid);
        System.out.println(params);
        params.put("type", "json");
        String resp = doPost(url_submit, params);
        return resp;
    }

    /**
     * 方法描述
     * 状态报告
     */
    public static String getReportJson(String userName,String password,String url_report) throws Exception {
        String message = "{'account':'"+userName+"','password':'"+MD5Encode(password)+"'}";
        String sid= new SHA1().getDigestOfString((token+"&"+message).getBytes());
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("message", message);
        params.put("sid", sid);
        params.put("type", "json");
        String resp = doPost(url_report, params);
        return resp;
    }

    /**
     * 方法描述
     * 查询余额
     */
    public static String getBalanceJson(String userName,String password,String url_balance) throws Exception {
        String message = "{'account':'"+userName+"','password':'"+MD5Encode(password)+"'}";
        String sid= new SHA1().getDigestOfString((token+"&"+message).getBytes());
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("message", message);
        params.put("sid", sid);
        params.put("type", "json");
        String resp = doPost(url_balance, params);
        return resp;
    }

    /**
     * 方法描述
     * 获取上行
     */
    public static String getSmsJson(String userName,String password,String url_deliver) throws Exception {
        String message = "{'account':'"+userName+"','password':'"+MD5Encode(password)+"'}";
        String sid= new SHA1().getDigestOfString((token+"&"+message).getBytes());
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("message", message);
        params.put("sid", sid);
        params.put("type", "json");
        String resp = doPost(url_deliver, params);
        return resp;
    }

    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param url
     *            请求的URL地址
     * @param params
     *            请求的查询参数,可以为null
     * @return 返回请求响应的HTML
     */
    private static String doPost(String url, Map<String, String> params) {
        String response = null;
        HttpClient client = new HttpClient();
        //设置超时时间
        HttpConnectionManagerParams httpConnectionManagerParams = client.getHttpConnectionManager().getParams();
        httpConnectionManagerParams.setConnectionTimeout(30 * 1000);
        httpConnectionManagerParams.setSoTimeout(30 * 1000);

        PostMethod postMethod = new PostMethod(url);
        postMethod.getParams().setParameter(
                HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");

        // 设置Post数据
        if (!params.isEmpty()) {
            int i = 0;
            NameValuePair[] data = new NameValuePair[params.size()];
            for (Map.Entry<String, String> entry : params.entrySet()) {
                data[i] = new NameValuePair(entry.getKey(), entry.getValue());
                i++;
            }

            postMethod.setRequestBody(data);

        }
        try {
            client.executeMethod(postMethod);
            if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String str = null;
                while((str = reader.readLine())!=null){
                    stringBuffer.append(str);
                }
                response = stringBuffer.toString();
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
            client.getHttpConnectionManager().closeIdleConnections(0);
        }
        return response;
    }

    // MD5加密函数
    public static String MD5Encode(String sourceString) {
        String resultString = null;
        try {
            resultString = new String(sourceString);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byte2hexString(md.digest(resultString.getBytes()));
        } catch (Exception ex) {
        }
        return resultString;
    }

    private static final String byte2hexString(byte[] bytes) {
        StringBuffer bf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if ((bytes[i] & 0xff) < 0x10) {
                bf.append("0");
            }
            bf.append(Long.toString(bytes[i] & 0xff, 16));
        }
        return bf.toString();
    }
}
