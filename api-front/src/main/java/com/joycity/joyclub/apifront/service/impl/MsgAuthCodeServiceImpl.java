package com.joycity.joyclub.apifront.service.impl;

import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.apifront.mapper.manual.MsgAuthCodeMapper;
import com.joycity.joyclub.apifront.modal.MsgAuthCode;
import com.joycity.joyclub.apifront.service.MsgAuthCodeService;
import com.joycity.joyclub.apifront.util.SHA1ForMsgSend;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.MD5Util;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.joycity.joyclub.commons.constant.ResultCode.MSG_AUTH_CODE_ERROR;

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
    @Value("${message.send.period}")
    private Integer msgPeriod;

    @Autowired
    MsgAuthCodeMapper msgAuthCodeMapper;

    @Override
    public ResultData getAndSendAuthCode(String phone) {
        String code = getAuthCode(phone);
        sendMessageCode("您的验证码为：" + code + "，有效时间" + (msgPeriod / 60) + "分钟。", phone);
        saveAuthCode(phone, code);
        return new ResultData("发送成功");
    }

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
        // TODO: 2017/4/17 过期无法判定 
        //这里忽视了数据库里有个创建日期是未来值的情况，如果是未来值，始终有效
        if ((System.currentTimeMillis() - authCode.getCreateTime().getTime()) / 1000 > msgPeriod) {
            throw new BusinessException(MSG_AUTH_CODE_ERROR, "验证码已过期");
        }

        if (!authCode.getCode().equals(code)) {
            throw new BusinessException(MSG_AUTH_CODE_ERROR, "验证码不正确");
        }

    }

    public void sendMessageCode(String content, String phone) {
        sendMessageCode(content, Arrays.asList(phone));
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
        params.add("sid=" + new SHA1ForMsgSend().getDigestOfString(("" + "&" + message).getBytes()));

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
            resultXmlError(e);
        }
        Element root = resultDoc.getRootElement();
        //检查返回码
        if (!"0".equals(root.elementText("result"))) {
            resultXmlError();
        }
    }

    private void resultXmlError() {
        resultXmlError(null);
    }

    private void resultXmlError(DocumentException e) {
        if (e == null) {
            logger.error("短信验证码发送失败");
        } else {
            logger.error("短信验证码返回值解析异常", e);
        }
        throw new BusinessException(MSG_AUTH_CODE_ERROR, "验证码发送失败");

    }
}
