package com.joycity.joyclub.apifront.service.impl;

import com.joycity.joyclub.apifront.exception.BusinessException;
import com.joycity.joyclub.apifront.mapper.MsgAuthCodeMapper;
import com.joycity.joyclub.apifront.modal.MsgAuthCode;
import com.joycity.joyclub.apifront.service.MsgAuthCodeService;

import com.joycity.joyclub.commons.utils.MD5Util;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import static com.joycity.joyclub.apifront.constant.ResultCode.MSG_AUTH_CODE_ERROR;

@Service
public class MsgAuthCodeServiceImpl implements MsgAuthCodeService {

    private Logger logger = LoggerFactory.getLogger(MsgAuthCodeServiceImpl.class);

    //发送短信的url
    @Value("${message.send.url}")
    private String msgSendUrl;

    //发送短信的用户名
    @Value("${message.send.username}")
    private String msgUserName;

    //发送短信的密码
    @Value("${message.send.password}")
    private String msgPassword;

    //每天发送短信的次数
    @Value("${message.send.num}")
    private Integer msgDailyNum;

    //短信验证码的有效期
    @Value("${message.period}")
    private Integer msgPeriod;

    @Autowired
    MsgAuthCodeMapper msgAuthCodeMapper;

    @Override
    public String getAuthCode(String phone) {
        //今日已发次数
        int todayNum = msgAuthCodeMapper.getTodayNumByPhone(phone);
        if (todayNum >= msgDailyNum) {
            throw new BusinessException(MSG_AUTH_CODE_ERROR, "今日验证次数已达上限" + msgDailyNum + "次");
        }
        return String.valueOf(new Random().nextInt(899999) + 100000);
    }

    @Override
    public void saveAuthCode(String phone, String code) {
        msgAuthCodeMapper.addCode(phone, code);
    }

    @Override
    public void checkAuthCode(final String phone, final String code) {
        MsgAuthCode authCode = msgAuthCodeMapper.getLatestCodeByPhone(phone);
        if (authCode == null) {
            throw new BusinessException(MSG_AUTH_CODE_ERROR, "验证码未发送");
        }

        if ((System.currentTimeMillis() - authCode.getCreateTime().getTime()) / 1000 > msgPeriod) {
            throw new BusinessException(MSG_AUTH_CODE_ERROR, "验证码已过期");
        }

        if (!authCode.getCode().equals(code)) {
            throw new BusinessException(MSG_AUTH_CODE_ERROR, "验证码不正确");
        }

    }

    public void sendMessageCode(String content, List<String> phone) {
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("UTF-8");
        Element message = doc.addElement("response");
        Element account = message.addElement("account");
        account.setText(msgUserName);

        Element password = message.addElement("password");
        password.setText(MD5Util.MD5(msgPassword));

        Element phones = message.addElement("phones");
        phones.setText(StringUtils.join(phone, ","));

        Element contentEle = message.addElement("content");
        contentEle.setText(content);

        Element signEle = message.addElement("sign");
        signEle.setText("");

        List<String> params = new ArrayList<>();
        params.add("message=" + doc.asXML());
        params.add("sid=" + DigestUtils.sha1Hex("" + "&" + message));

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(msgSendUrl);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(StringUtils.join(params, "&"));
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(MSG_AUTH_CODE_ERROR, "发送短信验证码失败");
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        Document resultDoc = null;
        try {
            resultDoc = DocumentHelper.parseText(result);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = resultDoc.getRootElement();
        String resultText = root.selectSingleNode("/response/result").getText();
        if (!"0".equals(resultText)) {
            logger.error("验证码发送异常 " + resultText);
            throw new BusinessException(MSG_AUTH_CODE_ERROR, "验证码发送异常");
        }
    }

}
