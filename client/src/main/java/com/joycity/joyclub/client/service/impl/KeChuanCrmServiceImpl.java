package com.joycity.joyclub.client.service.impl;

import com.joycity.joyclub.client.modal.Client;
import com.joycity.joyclub.client.service.KeChuanCrmService;
import com.joycity.joyclub.client.util.KeChuanEncryption;
import com.joycity.joyclub.commons.constant.Global;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.utils.Check;
import com.joycity.joyclub.commons.utils.DateTimeUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


@Service
public class KeChuanCrmServiceImpl implements KeChuanCrmService {

    private static final Log logger = LogFactory.getLog(KeChuanCrmServiceImpl.class);

    private static final String FORMAT_SS = "yyyy-MM-dd HH:mm:ss";
    private static final String FORMAT_DD = "yyyyMMdd";
    /**
     * 科传api返回值的data标签
     */
    private static final String RESULT_TAG_DATA = "DATA";
    /**
     * Header
     */
    private static final String RESULT_TAG_HEADER = "Header";
    private static final String RESULT_TAG_ERROR_CODE = "ERRCODE";
    private static final String RESULT_TAG_ERROR_MSG = "ERRMSG";
    @Value("${crm.tech.url}")
    private String url;

    @Value("${crm.tech.secretKey}")
    private String secretKey;

    @Value("${crm.tech.signKey}")
    private String signKey;

    @Value("${crm.tech.user}")
    private String user;

    @Value("${crm.tech.xml}")
    private String xml;

    public static String getMapString(Map map, Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                return answer.toString();
            }
        }

        return null;
    }

    @Override
    public void getBaseInfo() {
        Element data = postCrm("GetBasicInfo", "", "");
    }

    @Override
    public String createMember(String cardNo, String tel, String group13, String vipgrade, String creditCardProject) {
        Date now = new Date();
        String date = DateFormatUtils.format(now, "yyyyMMdd");
        String time = DateFormatUtils.format(now, "HHmmss");
        String sign = DigestUtils.md5Hex(date + time + tel + signKey);

        StringBuffer header = new StringBuffer();
        header.append("<SIGN>" + sign + "</SIGN>");
        header.append("<REQDATE>" + date + "</REQDATE>");
        header.append("<REQTIME>" + time + "</REQTIME>");
        header.append("<USER>" + this.user + "</USER>");

        StringBuffer request = new StringBuffer();
        request.append("<vip>");
        request.append("<xf_vipcode>" + KeChuanEncryption.aesEncrypt(cardNo, secretKey) + "</xf_vipcode>");
        request.append("<mobile>" + KeChuanEncryption.aesEncrypt(tel, secretKey) + "</mobile>");
        request.append("<xf_groupid13>" + group13 + "</xf_groupid13>");
        request.append("<vipgrade>" + vipgrade + "</vipgrade>");
        request.append("<xf_vipcodeprefix>" + creditCardProject + "</xf_vipcodeprefix>");
        request.append("</vip>");
        Element data = postCrm("VipCreate_YKH", header.toString(), request.toString());
        try {
            String vipCode = ((Element) data.elements().get(0)).getText();
            return KeChuanEncryption.aesDecrypt(vipCode, secretKey);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResultCode.KECHUAN_INFO_ERROR, "解析crm返回的数据失败");
        }
    }

    /**
     * cardNo,  tel,  group13,  vipgrade,  creditCardProject 不能为空
     *
     * @param user
     * @return
     */
    @Override
    public String createMember(Client user) {
        if (Check.checkNull(user.getCardNo(), user.getTel(), user.getGroup13(), user.getVipCardGrade(), user.getCreditCardProject())) {
            throw new BusinessException(ResultCode.KECHUAN_INFO_ERROR, "创建科传会员失败，有值为空");
        }
        return createMember(user.getCardNo(), user.getTel(), user.getGroup13(), user.getVipCardGrade(), user.getCreditCardProject());
    }

    @Override
    public void updateMember(Client member) {
//		if(StringUtils.isBlank(member.getGroup13())) {
//			throw new BusinessException(-1, "group 13不存在不能编辑资料");
//		}
        if (StringUtils.isBlank(member.getCreditCardProject())) {
            throw new BusinessException(ResultCode.KECHUAN_INFO_ERROR, "发卡项目不存在");
        }

        Date now = new Date();
        String date = DateFormatUtils.format(now, "yyyyMMdd");
        String time = DateFormatUtils.format(now, "HHmmss");
        String sign = DigestUtils.md5Hex(date + time + member.getVipCode() + signKey);

        StringBuffer header = new StringBuffer();
        header.append("<SIGN>" + sign + "</SIGN>");
        header.append("<REQDATE>" + date + "</REQDATE>");
        header.append("<REQTIME>" + time + "</REQTIME>");
        header.append("<USER>" + this.user + "</USER>");

        StringBuffer request = new StringBuffer();
        request.append("<vip>");
        request.append("<xf_vipcode>" + KeChuanEncryption.aesEncrypt(member.getVipCode(), secretKey) + "</xf_vipcode>");
        request.append("<mobile>" + KeChuanEncryption.aesEncrypt(member.getTel(), secretKey) + "</mobile>");
        request.append("<xf_groupid13>" + member.getGroup13() + "</xf_groupid13>");
        //发卡项目
        request.append("<xf_vipcodeprefix>" + member.getCreditCardProject() + "</xf_vipcodeprefix>");
        if (StringUtils.isNotBlank(member.getRealName())) {
            request.append("<surname>" + KeChuanEncryption.aesEncrypt(member.getRealName(), secretKey) + "</surname>");
        }
        if (StringUtils.isNotBlank(member.getVipCardGrade())) {
            request.append("<vipgrade>" + member.getVipCardGrade() + "</vipgrade>");
        }
        if (StringUtils.isNotBlank(member.getIdCard())) {
            request.append("<idcardtype>0</idcardtype>");
            request.append("<idcardno>" + KeChuanEncryption.aesEncrypt(member.getIdCard(), secretKey) + "</idcardno>");
        }
        if (StringUtils.isNotBlank(member.getSex())) {
            if (Global.SEX_MALE.equals(member.getSex())) {
                request.append("<sex>M</sex>");
            } else {
                request.append("<sex>F</sex>");
            }
        }
        if (member.getBirthday() != null) {
            request.append("<birthday>" + DateFormatUtils.format(member.getBirthday(), FORMAT_SS) + "</birthday>");
        }

        if (member.getHomeAddress() != null) {
            request.append("<address>" + member.getHomeAddress() + "</address>");
        }
        request.append("</vip>");
        Element data = postCrm("VipModifyAll", header.toString(), request.toString());
    }

    @Override
    public Client getMemberByTel(String tel) {
        return getMemberByTelOrVipCode(tel);
    }

    @Override
    public Client getMemberByVipCode(String code) {
        return getMemberByTelOrVipCode(code);
    }

    /**
     * 测试100个请求，平均耗时583ms,
     *
     * @param code
     * @return
     */
    public Client getMemberByTelOrVipCode(String code) {
        Date now = new Date();
        String date = DateFormatUtils.format(now, "yyyyMMdd");
        String time = DateFormatUtils.format(now, "HHmmss");
        String sign = DigestUtils.md5Hex(date + time + signKey);

        String param = null;

        param = KeChuanEncryption.aesEncrypt(code, secretKey);


        StringBuffer header = new StringBuffer();
        header.append("<REQDATE>" + date + "</REQDATE>");
        header.append("<REQTIME>" + time + "</REQTIME>");
        header.append("<SIGN>" + sign + "</SIGN>");
        header.append("<USER>" + user + "</USER>");

        StringBuffer request = new StringBuffer();
        request.append("<vipcode>" + param + "</vipcode>");

        Element result = postCrm("GetVipInfo", header.toString(), request.toString(), false);
        Element head = result.element(RESULT_TAG_HEADER);
        //获取会员信息失败有可能是会员不存在
        if (!checkApiResult(head)) {
            // TODO: 2017/4/11 根据字符串 “无效的会员号/手机号” 来判断会员不存在很危险
            if ("无效的会员号/手机号".equals(getApiErrorMsg(head)) || "100".equals(head.element(RESULT_TAG_ERROR_CODE).getText())) {
                return null;
            } else {
                throw new BusinessException(ResultCode.KECHUAN_INFO_ERROR, "获取会员信息失败");
            }
        }
        Element data = result.element(RESULT_TAG_DATA);
        Map<String, String> map = new HashMap<String, String>();
        Iterator<Element> i = data.element("VIP").elementIterator();
        while (i.hasNext()) {
            Element element = i.next();
            map.put(element.getName(), element.getText());
        }
        logger.debug(map);
        Client member = new Client();
        try {
            //会员号
            member.setVipCode(KeChuanEncryption.aesDecrypt(getMapString(map, "xf_vipcode"), secretKey));
            //会员卡面编号
            member.setCardNo(getMapString(map, "xf_bankcardno"));
            //积分
            String scoreStr = KeChuanEncryption.aesDecrypt(getMapString(map, "xf_bonus"), secretKey);
            if (StringUtils.isNoneBlank(scoreStr)) {
                scoreStr = scoreStr.replaceAll(",", "");
            }
            Double score = Double.parseDouble(scoreStr);
            member.setVipPoint(score.intValue());
            //真实姓名
            member.setRealName(KeChuanEncryption.aesDecrypt(getMapString(map, "xf_surname"), secretKey));
            //有效期
//			member.setBeginDate(DateUtils.parseDate(getMapString(map, "xf_jointdate"), new String[]{FORMAT_SS}).getTime());
//            member.setEffectDate(DateUtils.parseDate(getMapString(map, "xf_expirydate"), new String[]{FORMAT_SS}).getTime());
            //证件类型
//			member.setIdCardType(getMapString(map, "idcardtype"));
            //证件编号
            member.setIdCard(KeChuanEncryption.aesDecrypt(getMapString(map, "xf_vipid"), secretKey));
            //地址
            member.setHomeAddress(getMapString(map, "address"));
            member.setHomePostCode(getMapString(map, "xf_postal"));
            //微信号
//			member.setOpenId(getMapString(map, "weixin"));
            //email
//			member.setEmail(getMapString(map, "email"));
            member.setCreditCardProject(getMapString(map, "xf_vipcodeprefix"));
            //会员卡等级
            member.setVipCardGrade(getMapString(map, "xf_grade"));
            member.setGroup13(getMapString(map, "xf_groupid13"));
            member.setTel((KeChuanEncryption.aesDecrypt(getMapString(map, "xf_telephone"), secretKey)));
            member.setJoinDate(DateTimeUtil.parseYYYYMMDDHHMMSS(getMapString(map, "xf_jointdate")));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResultCode.KECHUAN_INFO_ERROR, "获取会员信息失败");
        }

        try {//生日
            String year = getMapString(map, "xf_birthdayyyyy");
            String month = StringUtils.leftPad(getMapString(map, "xf_birthdaymm"), 2, "0");
            String day = StringUtils.leftPad(getMapString(map, "xf_birthdaydd"), 2, "0");
            String birthday = year + month + day;
            if (StringUtils.isNotBlank(birthday) && year.length() == 4) {
                member.setBirthday(DateUtils.parseDate(birthday, new String[]{FORMAT_DD}).getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sex = getMapString(map, "sex");
        if (StringUtils.isNotBlank(sex)) {
            // TODO: 2017/4/11  科传性别
            if ("M".equals(sex)) {
                member.setSex(Global.SEX_MALE);
            } else if ("F".equals(sex)) {
                member.setSex(Global.SEX_FEMALE);
            } else {
                member.setSex(sex);
            }
        }

        return member;
    }

    @Override
    public void updateCardNo(String vipCode, String newCardNo) {
        Date now = new Date();
        String date = DateFormatUtils.format(now, "yyyyMMdd");
        String time = DateFormatUtils.format(now, "HHmmss");
        String sign = DigestUtils.md5Hex(date + time + vipCode + this.signKey);

        StringBuffer header = new StringBuffer();
        header.append("<SIGN>" + sign + "</SIGN>");
        header.append("<REQDATE>" + date + "</REQDATE>");
        header.append("<REQTIME>" + time + "</REQTIME>");
        header.append("<USER>" + this.user + "</USER>");

        StringBuffer request = new StringBuffer();
        request.append("<vipcode>" + KeChuanEncryption.aesEncrypt(vipCode, secretKey) + "</vipcode>");
        request.append("<newcardno>" + newCardNo + "</newcardno>");

        Element data = postCrm("UpdateVIPCardNO", header.toString(), request.toString());
    }

    @Override
    public void updateLevel(String vipCode, String level) {
        Date now = new Date();
        String date = DateFormatUtils.format(now, "yyyyMMdd");
        String time = DateFormatUtils.format(now, "HHmmss");
        String sign = DigestUtils.md5Hex(date + time + vipCode + this.signKey);

        StringBuffer header = new StringBuffer();
        header.append("<SIGN>" + sign + "</SIGN>");
        header.append("<REQDATE>" + date + "</REQDATE>");
        header.append("<REQTIME>" + time + "</REQTIME>");
        header.append("<USER>" + this.user + "</USER>");
        logger.debug("vipCode:" + vipCode + "; newgrade:" + level);
        StringBuffer request = new StringBuffer();
        request.append("<vipcode>" + KeChuanEncryption.aesEncrypt(vipCode, secretKey) + "</vipcode>");
        request.append("<newgrade>" + level + "</newgrade>");

        Element data = postCrm("UpdateVIPGrade", header.toString(), request.toString());
    }

    @Override
    public Integer changePoint(String vipCode, Double changedValue) {
        // System.out.println("会员减积分，vipcode：" + vipCode + "score:" + score.intValue());
        Date now = new Date();
        String date = DateFormatUtils.format(now, "yyyyMMdd");
        String time = DateFormatUtils.format(now, "HHmmss");
        String sign = DigestUtils.md5Hex(date + time + String.valueOf(changedValue.intValue()) + signKey);

        StringBuffer header = new StringBuffer();
        header.append("<REQDATE>" + date + "</REQDATE>");
        header.append("<REQTIME>" + time + "</REQTIME>");
        header.append("<SIGN>" + sign + "</SIGN>");
        header.append("<USER>" + user + "</USER>");

        StringBuffer request = new StringBuffer();
        request.append("<vipcode>" + KeChuanEncryption.aesEncrypt(vipCode, secretKey) + "</vipcode>");
        request.append("<expdate>9999-12-31</expdate>");
        request.append("<bonus>" + KeChuanEncryption.aesEncrypt(String.valueOf(changedValue.intValue()), secretKey) + "</bonus>");
        request.append("<reasoncode>00004</reasoncode>");
//		request.append("<remark>" + param + "</remark>");

        Element data = postCrm("BonusChange", header.toString(), request.toString());

        String scoreText = data.element("currentbonus").getText();
        Integer score = null;
        if (StringUtils.isNotBlank(scoreText)) {
            score = Integer.parseInt(KeChuanEncryption.aesDecrypt(scoreText, secretKey));
        }
        return score;
    }

    @Override
    public List<Map<String, Object>> getScoreRecord(String vipCode) {
        return getRecords("GetBonusledgerRecord", vipCode);
    }

    @Override
    public List<Map<String, Object>> getSaleRecord(String vipCode) {
        return getRecords("GetVipSalesRecord", vipCode);

    }

    /**
     * @param crmMethodName 科传调用的相对api地址
     * @param vipCode
     * @return
     */
    private List<Map<String, Object>> getRecords(String crmMethodName, String vipCode) {
        Date now = new Date();
        String date = DateFormatUtils.format(now, "yyyyMMdd");
        String time = DateFormatUtils.format(now, "HHmmss");
        String sign = DigestUtils.md5Hex(date + time + vipCode + signKey);

        StringBuffer header = new StringBuffer();
        header.append("<REQDATE>" + date + "</REQDATE>");
        header.append("<REQTIME>" + time + "</REQTIME>");
        header.append("<SIGN>" + sign + "</SIGN>");
        header.append("<USER>" + user + "</USER>");

        StringBuffer request = new StringBuffer();
        request.append("<vipcode>" + KeChuanEncryption.aesEncrypt(vipCode, secretKey) + "</vipcode>");
        request.append("<action></action>");

        Element data = null;
        try {
            data = postCrm(crmMethodName, header.toString(), request.toString());
        } catch (BusinessException e) {
            //科传会把无记录当成错误，这里拦截后正常返回
            if (ResultCode.KECHUAN_INFO_ERROR_BUT_RIGHT == e.getCode()) {
                return new ArrayList<>();
            }
            throw e;
        }

        List<Element> list = data.elements();
        List<Map<String, Object>> restul = new ArrayList<>();
        for (Element element : list) {
            Map<String, Object> map = new HashMap<>();
            List<Element> children = element.elements();
            for (Element child : children) {
                map.put(child.getName(), child.getText());
            }
            restul.add(map);
        }
        return restul;
    }

    private Element postCrm(String method, String header, String data) {
        return postCrm(method, header, data, true);
    }

    /**
     * @param method
     * @param header
     * @param data
     * @param checkResultBeforeReturn 是否在返回值前检查api访问是否正确，并抛出异常.
     *                                如果为true，不检查，返回result(包含DATA,Header)
     *                                如果为false,检查并抛出异常，返回result.DATA
     * @return
     */
    private Element postCrm(String method, String header, String data, Boolean checkResultBeforeReturn) {
        StringBuffer s = new StringBuffer();
        byte[] b = new byte[1024];

        HttpURLConnection conn = null;
        OutputStream os = null;
        InputStream is = null;
        try {
            URL wsUrl = new URL(url);

            conn = (HttpURLConnection) wsUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");

            os = conn.getOutputStream();

            // 请求体
            String soap = this.xml.replaceAll("method", method).replaceAll("header-parma", header).replaceAll("data-parma", data);
            logger.debug("请求参数：" + soap);
            os.write(soap.toString().getBytes());

            is = conn.getInputStream();

            int len = 0;
            while ((len = is.read(b)) != -1) {
                s.append(new String(b, 0, len, "UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        if (s.length() == 0) {
            return null;
        }
        Document document;
        try {
            document = DocumentHelper.parseText(s.toString());
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new BusinessException(ResultCode.KECHUAN_INFO_ERROR, "解析CRM数据失败");
        }
        Element root = document.getRootElement();
        Element allBody = (Element) root.elements().get(0);
        Element response = (Element) allBody.elements().get(0);
        Element result = (Element) response.elements().get(0);
        Element head = result.element(RESULT_TAG_HEADER);
        Element dataElement = result.element(RESULT_TAG_DATA);
        if (checkResultBeforeReturn && !checkApiResult(head)) {
            String apiErrorMsg = getApiErrorMsg(head);
            if ("会员无积分记录".equals(apiErrorMsg) || "会员无消费记录".equals(apiErrorMsg)) {
                //会员无积分记录会被科传当成错误，我们要在上层中拦截这个KECHUAN_INFO_ERROR_BUT_RIGHT
                throw new BusinessException(ResultCode.KECHUAN_INFO_ERROR_BUT_RIGHT);
            } else {
                logger.error("科传api请求失败：errorMsg:" + apiErrorMsg + " method:" + method + " header:" + header + " data:" + data);
                throw new BusinessException(ResultCode.KECHUAN_INFO_ERROR, "会员操作失败");
            }
        }
        return checkResultBeforeReturn ? dataElement : result;
    }

    /**
     * 根据科传api调用返回值的Header标签判断api调用是否成功
     *
     * @param head
     * @return
     */
    private boolean checkApiResult(Element head) {
        return "0".equals(head.element(RESULT_TAG_ERROR_CODE).getText());
    }

    private String getApiErrorMsg(Element head) {
        return head.element(RESULT_TAG_ERROR_MSG).getText();
    }
}