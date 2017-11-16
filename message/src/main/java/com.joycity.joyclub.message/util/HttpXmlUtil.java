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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @Author: qiaohuizhong
 * @Description:
 * @Date: Created in 18:04 2017/11/15
 */
public class HttpXmlUtil {
    // ############################此部分参数需要修改############################
    public static String url_submit="http://new.yxuntong.com/emmpdata/sms/Submit";//提交短信地址
    public static String url_deliver="http://new.yxuntong.com/emmpdata/sms/Deliver";//下行地址
    public static String url_report="http://new.yxuntong.com/emmpdata/sms/Report";//状态报告地址
    public static String url_balance="http://new.yxuntong.com/emmpdata/sms/Balance";//余额提醒地址
    public static String token = "111111";//安全验证key
    public static String phone = "13111111111,13222222222,13333333333";//手机号码
    public static String userName = "1003"; // 用户名
    public static String password = "qazwsx123"; // 密码
    public static String content = "你好，测试短信"; // 短信内容
    public static String sign = ""; // 短信内容



    public static void main(String[] args) throws Exception {
        testNewJk();
    }

    private static void testNewJk() throws Exception {

        String resp = null;

        System.out.println("*************发送短信*************");
        resp = send(userName,password,url_submit,phone,content,sign,"","");
        System.out.println(resp);

        System.out.println("*************获取状态报告*************");
        resp = getReport(userName,password, url_report);
        System.out.println(resp);


        System.out.println("*************获取上行*************");
        resp = getSms(userName,password,url_deliver);
        System.out.println(resp);

        System.out.println("*************获取金额*************");
        resp = getBalance(userName,password,url_balance);
        System.out.println(resp);
    }

    /**
     * 方法描述
     * 发送短信方法使用document 对象方法封装XML字符串
     * @param userName
     * @param password
     * @param url_submit
     * @param phones
     * @param content
     * @param sign
     * @param subcode
     * @param sendtime
     * @return
     */
    public static String send(String userName,String password,String url_submit,String phones,String content,String sign,String subcode,String sendtime) {
        Map<String, String> params = new LinkedHashMap<String, String>();
        String message=DocXml(userName, password,phones,content,sign,subcode,sendtime);
        params.put("message", message);
        String sid= new SHA1().getDigestOfString((token+"&"+message).getBytes());
        params.put("sid", sid);
        System.out.println(params);
        String resp = doPost(url_submit, params);
        return resp;
    }

    /**
     * 方法描述
     * 状态报告
     */
    public static String getReport(String userName,String password,String url_report) {
        Map<String, String> params = new LinkedHashMap<String, String>();
        String message = "<?xml version='1.0' encoding='UTF-8'?><message>"
                + "<account>" + userName + "</account>" + "<password>"
                + MD5Encode(password) + "</password>"
                + "<msgid></msgid><phone></phone></message>";
        params.put("message", message);
        String sid= new SHA1().getDigestOfString((token+"&"+message).getBytes());
        System.out.println(sid);
        params.put("sid", sid);
        String resp = doPost(url_report, params);
        return resp;
    }

    /**
     * 方法描述
     * 查询余额
     */
    public static String getBalance(String userName,String password,String url_balance) {
        Map<String, String> params = new LinkedHashMap<String, String>();
        String message = "<?xml version='1.0' encoding='UTF-8'?><message><account>"
                + userName
                + "</account>"
                + "<password>"
                + MD5Encode(password)
                + "</password></message>";
        params.put("message", message);
        String resp = doPost(url_balance, params);
        return resp;
    }

    /**
     * 方法描述
     * 获取上行
     */
    public static String getSms(String userName,String password,String url_deliver) {
        Map<String, String> params = new LinkedHashMap<String, String>();
        String message = "<?xml version='1.0' encoding='UTF-8'?><message><account>"
                + userName
                + "</account>"
                + "<password>"
                + MD5Encode(password)
                + "</password></message>";
        params.put("message", message);
        String sid= new SHA1().getDigestOfString((token+"&"+message).getBytes());
        params.put("sid", sid);
        String resp = doPost(url_deliver, params);
        return resp;
    }
    /**
     * 使用document 对象封装XML
     * @param userName
     * @param pwd
     * @param id
     * @param phone
     * @param contents
     * @param sign
     * @param subcode
     * @param sendtime
     * @return
     */
    private static String DocXml(String userName,String pwd,String  phone,String contents,String msgid,String subcode,String sendtime) {
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("UTF-8");
        Element message = doc.addElement("response");
        Element account = message.addElement("account");
        account.setText(userName);
        Element password = message.addElement("password");
        password.setText(MD5Encode(pwd));
        Element phones = message.addElement("phones");
        phones.setText(phone);
        Element content = message.addElement("content");
        content.setText(contents);
        Element sign1 = message.addElement("sign");
        sign1.setText("");
        Element subcode1 = message.addElement("subcode");
        subcode1.setText(subcode);
        Element sendtime1 = message.addElement("sendtime");
        sendtime1.setText(sendtime);
        return message.asXML();
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
