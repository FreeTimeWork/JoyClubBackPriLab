package com.joycity.joyclub.recharge.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.joycity.joyclub.client.service.ClientService;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.utils.DateTimeUtil;
import com.joycity.joyclub.commons.utils.MD5Util;
import com.joycity.joyclub.recharge.RechargeFluxConfig;
import com.joycity.joyclub.recharge.RechargeMoneyConfig;
import com.joycity.joyclub.recharge.constants.RFluxMoney;
import com.joycity.joyclub.recharge.constants.XiangfuRechargeType;
import com.joycity.joyclub.recharge.mapper.XiangfuRechargeDetailMapper;
import com.joycity.joyclub.recharge.modal.generated.XiangfuRechargeDetail;
import com.joycity.joyclub.recharge.modal.vo.FluxTemp;
import com.joycity.joyclub.recharge.modal.vo.RechargeVO;
import com.joycity.joyclub.recharge.modal.vo.SpecListModel;
import com.joycity.joyclub.recharge.service.RechargeService;
import com.joycity.joyclub.recharge.util.RSAUtil;
import com.joycity.joyclub.we_chat.pay.wechat.SignUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CallMeXYZ on 2017/6/14.
 */
@Service
public class RechargeServiceImpl implements RechargeService {
    private Log logger = LogFactory.getLog(RechargeServiceImpl.class);

    private final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddhhmmssSSS");
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RechargeMoneyConfig rechargeMoneyConfig;
    @Autowired
    private RechargeFluxConfig rechargeFluxConfig;
    @Autowired
    private ClientService clientService;
    @Autowired
    private XiangfuRechargeDetailMapper xiangfuRechargeDetailMapper;

//    /**
//     * 参考文档 http://wiki.madaikr.com/flow-api-doc/
//     *
//     * @param phone
//     * @param flowSize 流量包大小，以M为单位
//     */
//    @Override
//    public void rechargeFlow(String phone, int flowSize, boolean ifNationFlow) {
//        Map<String, Object> businessBody = new HashedMap();
//        businessBody.put("action", "CZ");
//        businessBody.put("orderId", createOrderCode());
//        businessBody.put("chargeAcct", phone);
//        businessBody.put("chargeType", 1);
//        businessBody.put("flowPackageType", ifNationFlow ? 0 : 1);
//        businessBody.put("flowPackageSize", flowSize);
//        // TODO: 2017/6/14 回调地址
////        businessBody.put("retUrl", URLEncoder.encode("http://www.baidu.com?a=1&b=张三","UTF-8"));
//
//        System.out.println(rechargeConfig.getUrl());
//        System.out.println(JSON.toJSONString(getPostData("CZ", businessBody)));
//        String result = restTemplate.postForObject(rechargeConfig.getUrl(), getPostData("CZ", businessBody), String.class);
//        JSON.parseObject(result, RechargeFlowResult.class);
//
//
//    }

    @Override
    public String rechargeMoney(RechargeVO vo,Long clientId) throws UnsupportedEncodingException {
        XiangfuRechargeDetail detail = recharge(vo, clientId);
        String result = receiverToMiteno(detail);
        logger.info("RechargeServiceImpl-rechargeMoney-result = "+result);
        return result;
    }

    private XiangfuRechargeDetail recharge(RechargeVO vo,Long clientId){
        Integer point = clientService.getPoint(clientId);
        if (point < vo.getPoint().intValue()) {
            new BusinessException(ResultCode.VIP_POINT_NOT_ENOUGH);
        }
        clientService.addPoint(clientId, -vo.getPoint().doubleValue());
        return  createXiangfuOrder(vo, clientId);
    }

    @Override
    public String rechargeFlux(RechargeVO vo, Long clientId) throws Exception {
        XiangfuRechargeDetail detail = recharge(vo, clientId);
        FluxTemp temp = new FluxTemp();
        temp.setProvince("110");
        temp.setTimeStamp(DateTimeUtil.formatYYYYMMDDHHMMSS(new Date()));
        SpecListModel model = getSpecList(vo.getTel(), temp);
        temp.setOperatorType(model.getMo());
        temp.setScope("province");
        String result = buyQuota(detail, temp);
        logger.info("RechargeServiceImpl-rechargeFlux"+result);
        return result;
    }

    @Override
    public SpecListModel getSpecList(String tel,FluxTemp temp) throws Exception {
        String url = rechargeFluxConfig.getUrl() + "/manage/services/getSpecList?appId={appId}&phoneNo={tel}&province={province}&timeStamp={timeStamp}&signature={sign}";
        Map<String, Object> params= new HashMap<>();
        String callback = "http://joy-cb.ykh-bj.com/api/front/xiangfu/callback";
        params.put("appId", rechargeFluxConfig.getAppId());
        params.put("phoneNo", tel);
        params.put("province", temp.getProvince());
        params.put("timeStamp", temp.getTimeStamp());
        params.put("signature", createFluxSign(params));
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, params);
        SpecListModel model = JSONObject.parseObject(responseEntity.getBody(), SpecListModel.class);
        return model;
    }

    /**
     * 2.1.	缴费接口
     */
    private String receiverToMiteno(XiangfuRechargeDetail detail) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, String> body= new LinkedMultiValueMap<String, String>();

        String callback = "http://joy-cb.ykh-bj.com/api/front/xiangfu/callback";
        callback = URLEncoder.encode(callback, "utf-8");
        body.add("agentid", rechargeMoneyConfig.getMchid());
        body.add("orderId", detail.getOrderCode());
        body.add("amount", detail.getPayable().toString());
        body.add("userToken", detail.getTel());
        body.add("callbackurl", callback);
        body.add("extendinfo", "joycity");
        body.add("sign", createMitenoSign(detail));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(rechargeMoneyConfig.getUrl()+"/Receiver", HttpMethod.POST,request,String.class);
        logger.info("mitenoResponse = " + response.getBody());
        return response.getBody();
    }

    /**
     * 1.	流量充值
     */
    private String buyQuota(XiangfuRechargeDetail detail, FluxTemp temp) throws Exception {
        String baseUrl = rechargeFluxConfig.getUrl();

        Map<String, Object> body= new HashMap<>();
        String callback = "http://joy-cb.ykh-bj.com/api/front/xiangfu/callback";
        callback = URLEncoder.encode(callback, "utf-8");
        body.put("appId", rechargeFluxConfig.getAppId());
        body.put("customerOrderId", detail.getOrderCode());
        body.put("operatorType", temp.getOperatorType());
        body.put("phoneNo", detail.getTel());
        body.put("province", temp.getProvince());
        body.put("spec", detail.getAmount());
        body.put("scope", temp.getScope());
        body.put("callbackUrl", callback);
        body.put("timeStamp", temp.getTimeStamp());
        body.put("signature", createFluxSign(body));
        String params = SignUtils.basicSign(body,false);
        String url = baseUrl +"?"+ params;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        return responseEntity.getBody();
    }




    private XiangfuRechargeDetail createXiangfuOrder(RechargeVO vo, Long clientId) {
        XiangfuRechargeDetail detail = new XiangfuRechargeDetail();
        detail.setAmount(vo.getAmount());
        detail.setClientId(clientId);
        detail.setOrderCode(createOrderCode());
        if (vo.getType().equals(XiangfuRechargeType.RECHARGECARD.getCode())) {
            detail.setPayable(vo.getAmount());
        } else if (vo.getType().equals(XiangfuRechargeType.FLOWCARD.getCode())) {
            RFluxMoney fluxMoney = RFluxMoney.getMoneyByFlux(vo.getAmount().intValue());
            if (fluxMoney == null) {
                throw new BusinessException(ResultCode.REQUEST_PARAMS_ERROR, "不支持该档位充值");
            }
            detail.setPayable(fluxMoney.getMoney());
        } else {
            detail.setPayable(BigDecimal.ZERO);
        }
        detail.setPoint(new BigDecimal(vo.getPoint()));
        detail.setTel(vo.getTel());
        detail.setType(vo.getType());
        xiangfuRechargeDetailMapper.insertSelective(detail);
        return detail;
    }

    /**
     * 生成20位订单号
     * 当前日期+随机三位数
     *
     * @return
     */
    private String createOrderCode() {
        return SDF.format(new Date()) + RandomStringUtils.random(3, "123456789");
    }

    private String createMitenoSign(XiangfuRechargeDetail detail) {
        String sign = MD5Util.MD5(rechargeMoneyConfig.getMchid() + detail.getOrderCode() + detail.getPayable() + detail.getTel() + rechargeMoneyConfig.getKey());
        return sign.toLowerCase();
    }

    private String createFluxSign(Map<String,Object> params) throws Exception {
        String sign = SignUtils.basicSign(params, false);
        sign = MD5Util.MD5(sign).toLowerCase();
        String finalSign = RSAUtil.encodeStringPrivate(sign.getBytes(), RSAUtil.getPrivateKey(rechargeFluxConfig.getPrivateKey()));
        return finalSign;
    }

//    private RechargePostData getPostData(String action, Map businessBody) {
//        RechargePostData postData = new RechargePostData();
//        postData.setAgentAccount(rechargeConfig.getUser());
//        postData.setBusiBody(businessBody);
//        postData.setSign(DigestUtils.md5Hex(JSON.toJSONString(businessBody) + rechargeConfig.getKey()));
//        return postData;
//    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String callback = "http://joy-cb.ykh-bj.com/api/front/xiangfu/callback";
        callback = URLEncoder.encode(callback, "utf-8");
        System.out.println(callback);
    }
}
