package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.service.WechatService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.joycity.joyclub.apifront.constant.Global.URL_API_FRONT;
import static com.joycity.joyclub.commons.constants.Global.PLATFORM_ID_REQUEST_PARAM;

/**
 * Created by CallMeXYZ on 2017/4/14.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class WechatController {
    @Autowired
    WechatService wechatService;

    //todo token写死了
    @RequestMapping("/wechat/userinfo/{openId}")
    public ResultData getUserInfoForProject(@PathVariable String openId) {
        return new ResultData(wechatService.getUserInfo(openId, "http://bjmall.stcl365.com:20000/wechat/public/getToken"));
    }
// TODO: 2017/4/18  openId小写 同时修改前端的路径
    @RequestMapping("/wechat/openId")
    public ResultData getAuthCode(@RequestParam String code, @RequestParam(defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId) {
        return new ResultData(wechatService.getAccessTokenAndOpenId(code,projectId));
    }

/*    @RequestMapping("/wechat/pay/notify")
    public void wechatPayNotify(HttpServletRequest request, HttpServletResponse response) {
        try {

            Map<String, String> params = WechatXmlUtil.xmlToMap(request
                    .getInputStream());

            String tradeStatus = params.get("result_code");
            if (tradeStatus != null && tradeStatus.equals("SUCCESS")) {
                String tradeNo = params.get("transaction_id");
                String out_trade_no = params.get("out_trade_no");
                System.out.println(out_trade_no);
                System.out.println(tradeNo);
                *//*List<Trade> ts =  tradeService.getTradeByOutTradeNo(out_trade_no);
                for(Trade t : ts) {
                    if(t.getPlatTradeNo() != null) {
                        throw new BusinessException(BusinessException.PARAM_ILLEGAL);
                    }
                }
                logger.info("wxpay notify,out_trade_no is... ... ... : " + out_trade_no);
                logger.info("wxpay notify,trade_no is ... ..." + tradeNo);
                SaleCalculDTO saleCalculDTO = new SaleCalculDTO();
                saleCalculDTO.setOutTradeNo(out_trade_no);
                saleService.saleHandler(saleCalculDTO,tradeNo,Trade.ORDER_PAY);*//*
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("return_code", "SUCCESS");
            map.put("return_msg", "OK");
            response.getWriter().write(WechatXmlUtil.mapToXml(map));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw e;
        }
    }*/

}
