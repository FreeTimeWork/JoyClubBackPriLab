package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.client.mapper.ClientUserMapper;
import com.joycity.joyclub.client.modal.Client;
import com.joycity.joyclub.client.service.ClientService;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.recharge.modal.RTMResult;
import com.joycity.joyclub.recharge.modal.vo.RtmParamVo;
import com.joycity.joyclub.recharge.service.RtmRechargeOrderService;
import com.joycity.joyclub.recharge.util.RtmMD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;

/**
 * @auther fangchen.chai ON 2017/11/30
 */
@RestController
@RequestMapping(URL_API_FRONT+"/rtm")
public class RtmRechargeController {

    Logger logger = LoggerFactory.getLogger(RtmRechargeController.class);

    @Autowired
    private RtmMD5Util rtmMD5Util;
    @Autowired
    private RtmRechargeOrderService rtmRechargeOrderService;
    @Autowired
    private ClientUserMapper clientUserMapper;
    private static final String dev_url = "http://point.jd.com/unionaccount/bindNotice"; //开发地址
    private static final String real_url = "https://point.jd.com/unionaccount/bindNotice";//生产地址

    @GetMapping("/auth")
    public void rtmRuth(@RequestParam(required = false) String uid,
                             @RequestParam String clientId,
                             @RequestParam String timestamp,
                             @RequestParam(required = false) String description,
                             @RequestParam String sign,
                             @RequestParam(required = false) Boolean sys,
                             HttpServletResponse response) throws IOException {

        RtmParamVo vo = new RtmParamVo();
        vo.setClientId(clientId);
        if (uid != null) {
            vo.setUid(uid);
        }
        vo.setDescription(description);
        vo.setTimestamp(timestamp);
        vo.setSign(sign);
        logger.info("RtmRechargeController-rtm-auth-vo:{} - sys={}", vo,sys);
        RTMResult rtmResult = null;
        //本系统不需要验证签名
        if (sys) {
            rtmResult = checkSign(sign, rtmMD5Util.getRtmSign1(vo));
        }
        if (rtmResult.getStatus()) {
            Map<String, String> urlParams = new HashMap<>();
            urlParams.put("clientId", clientId);
            urlParams.put("timestamp", timestamp);
            urlParams.put("sign", sign);
            //没有uid返回到登录页面
            if (uid == null) {
                String loginUrl = "http://joy-cb.ykh-bj.com/login?clientId={clientId}&timestamp={timestamp}&sign={sign}";
                loginUrl = replaceUrl(loginUrl, urlParams);
                response.sendRedirect(loginUrl);
            } else { //回调京东
                Long clientUserId = clientUserMapper.getIdByTel(uid);
                if (clientUserId == null) {
                    urlParams.put("status", Boolean.FALSE.toString());
                    urlParams.put("code", ResultCode.DATA_NOT_EXIST + "");
                    urlParams.put("msg", "会员不存在");
                    urlParams.put("clientId", "");
                    urlParams.put("userStatus", "0");
                } else {
                    timestamp = System.currentTimeMillis() + "";
                    Client user = clientUserMapper.selectByPrimaryKey(clientUserId);
                    urlParams.put("status", Boolean.TRUE.toString());
                    urlParams.put("code", "200");
                    urlParams.put("msg", "成功");
                    urlParams.put("userStatus", "1");
                    urlParams.put("uid", user.getTel());
                    urlParams.put("timestamp", timestamp);
                    RtmParamVo rtmParamVo = new RtmParamVo();
                    rtmParamVo.setTimestamp(timestamp);
                    rtmParamVo.setUid(user.getTel());
                    rtmParamVo.setClientId(clientId);
                    urlParams.put("sign", rtmMD5Util.getRtmSign1(rtmParamVo));
                }
                String bindUrl = dev_url + "?status={status}&code={code}&msg={msg}&clientId={clientId}&uid={uid}&userStatus={userStatus}&timestamp={timestamp}&sign={sign}";
                bindUrl = replaceUrl(bindUrl, urlParams);
                response.sendRedirect(bindUrl);
            }

        }


    }

    @GetMapping("/point")
    public RTMResult getPoint(@RequestParam String uid,
                              @RequestParam String clientId,
                              @RequestParam String timestamp,
                              @RequestParam(required = false) String description,
                              @RequestParam String sign){
        RtmParamVo vo = new RtmParamVo();
        vo.setClientId(clientId);
        vo.setUid(uid);
        vo.setDescription(description);
        vo.setTimestamp(timestamp);
        vo.setSign(sign);
        logger.info("RtmRechargeController-rtm-point-vo:{}",vo);

//        RTMResult rtmResult = checkSign(sign, rtmMD5Util.getRtmSign1(vo));
        RTMResult rtmResult = new RTMResult();
        if (!rtmResult.getStatus()) {
            return rtmResult;
        }
        try {
            rtmResult = rtmRechargeOrderService.getPoint(uid);
        } catch (Exception e) {
            rtmResult.setStatus(false);
            rtmResult.setCode("500");
            rtmResult.setMsg(e.toString());
        }

        return rtmResult;
    }

    @GetMapping("/delete")
    public RTMResult deletePoint(@RequestParam String uid,
                                 @RequestParam String credits ,
                                 @RequestParam String clientId,
                                 @RequestParam String timestamp,
                                 @RequestParam(required = false) String description,
                                 @RequestParam String rtmOrderNum ,
                                 @RequestParam String pointType ,
                                 @RequestParam String sign){
        RtmParamVo vo = new RtmParamVo();
        vo.setClientId(clientId);
        vo.setUid(uid);
        vo.setDescription(description);
        vo.setTimestamp(timestamp);
        vo.setSign(sign);
        vo.setCredits(credits);
        vo.setRtmOrderNum(rtmOrderNum);
        vo.setPointType(pointType);
        logger.info("RtmRechargeController-rtm-delete-vo:{}",vo);

//        RTMResult rtmResult = checkSign(sign, rtmMD5Util.getRtmSign2(vo));
        RTMResult rtmResult = new RTMResult();
        if (!rtmResult.getStatus()) {
            return rtmResult;
        }

        try {
            rtmResult = rtmRechargeOrderService.deletePoint(uid,new BigDecimal(credits),pointType,rtmOrderNum);
        } catch (Exception e) {
            rtmResult.setStatus(false);
            rtmResult.setCode("500");
            rtmResult.setMsg(e.toString());
        }

        return rtmResult;
    }

    @GetMapping("/back")
    public RTMResult backPoint(@RequestParam String uid,
                               @RequestParam String credits ,
                               @RequestParam String clientId,
                               @RequestParam String timestamp,
                               @RequestParam(required = false) String description,
                               @RequestParam String rtmOrderNum ,
                               @RequestParam String pointType ,
                               @RequestParam String sign){

        RtmParamVo vo = createVo(uid, credits, clientId, timestamp, description, rtmOrderNum, pointType, sign);
        logger.info("RtmRechargeController-rtm-back-vo:{}",vo);

//        RTMResult rtmResult = checkSign(sign, rtmMD5Util.getRtmSign2(vo));
        RTMResult rtmResult = new RTMResult();
        if (!rtmResult.getStatus()) {
            return rtmResult;
        }
        try {
            rtmResult = rtmRechargeOrderService.backPoint(uid,new BigDecimal(credits),pointType,rtmOrderNum);
        } catch (Exception e) {
            rtmResult.setStatus(false);
            rtmResult.setCode("500");
            rtmResult.setMsg(e.toString());
        }

        return rtmResult;
    }

    @GetMapping("/add")
    public RTMResult addPoint(@RequestParam String uid,
                              @RequestParam String credits ,
                              @RequestParam String clientId,
                              @RequestParam String timestamp,
                              @RequestParam(required = false) String description,
                              @RequestParam String rtmOrderNum ,
                              @RequestParam String pointType ,
                              @RequestParam String sign){

        RtmParamVo vo = createVo(uid, credits, clientId, timestamp, description, rtmOrderNum, pointType, sign);
        logger.info("RtmRechargeController-rtm-add-vo:{}",vo);

        RTMResult rtmResult = checkSign(sign, rtmMD5Util.getRtmSign2(vo));
//        RTMResult rtmResult = new RTMResult();
        if (!rtmResult.getStatus()) {
            return rtmResult;
        }
        try {
            rtmResult = rtmRechargeOrderService.addPoint(uid, new BigDecimal(credits), pointType, rtmOrderNum);
        } catch (Exception e) {
            rtmResult.setStatus(false);
            rtmResult.setCode("500");
            rtmResult.setMsg(e.toString());
        }

        return rtmResult;
    }

    @GetMapping("/order/detail")
    public RTMResult rtmOrderDetail(@RequestParam String clientId,
                                    @RequestParam String timestamp,
                                    @RequestParam String rtmOrderNum ,
                                    @RequestParam String sign) {

        RtmParamVo vo = createVo(null, null, clientId, timestamp, rtmOrderNum, null, null, sign);
        logger.info("RtmRechargeController-rtm-orderDetail-vo:{}",vo);
//        RTMResult rtmResult = checkSign(sign, rtmMD5Util.getRtmSign3(vo));
        RTMResult rtmResult = new RTMResult();
        if (!rtmResult.getStatus()) {
            return rtmResult;
        }

        try {
            rtmResult = rtmRechargeOrderService.getRtmOrderDetail(rtmOrderNum);
        } catch (Exception e) {
            rtmResult.setStatus(false);
            rtmResult.setCode("500");
            rtmResult.setMsg(e.toString());
        }

        return rtmResult;
    }

    private RtmParamVo createVo( String uid,
                                String credits ,
                                String clientId,
                                String timestamp,
                                String description,
                                String rtmOrderNum ,
                                String pointType ,
                                String sign) {

        RtmParamVo vo = new RtmParamVo();
        vo.setClientId(clientId);
        vo.setUid(uid);
        vo.setDescription(description);
        vo.setTimestamp(timestamp);
        vo.setSign(sign);
        vo.setCredits(credits);
        vo.setRtmOrderNum(rtmOrderNum);
        vo.setPointType(pointType);
        return vo;
    }

    private RTMResult checkSign(String outSign, String mySign) {
        RTMResult result = new RTMResult();
        if (!outSign.toUpperCase().equals(mySign.toUpperCase())) {
            result.setMsg("sign校验失败");
            result.setCode("5002");
            result.setStatus(false);
        }
        return result;
    }


    public static void main(String[] args) {
        String url = "https://point.jd.com/unionaccount/bindNotice?status={status}&code={code}clientId={clientId}&uid={uid}&";
        url = url.replace("{code}", "200");
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("code", "200");
        urlParams.put("status", "1");
        urlParams.put("clientId", "123");
        url = replaceUrl(url,  urlParams);
        System.out.println(url);
    }


    private static String replaceUrl(String url,Map<String,String> urlParams) {
        for (Map.Entry<String,String> entry : urlParams.entrySet()) {
            String old = "{" + entry.getKey() + "}";
            url = url.replace(old, entry.getValue());
        }
        return url;
    }
    
}
