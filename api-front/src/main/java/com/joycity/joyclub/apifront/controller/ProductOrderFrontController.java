package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.service.ProductOrderFrontService;
import com.joycity.joyclub.apifront.util.WechatXmlUtil;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.joycity.joyclub.apifront.constant.Global.URL_API_FRONT;
import static com.joycity.joyclub.commons.constants.Global.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Administrator on 2017/4/20.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class ProductOrderFrontController {
    @Autowired
    ProductOrderFrontService productOrderFrontService;

    /**
     * 提供商品属性相关数据
     *
     * @return
     */
    @RequestMapping(value = "/order/formdata", method = GET)
    public ResultData getListForPreOrder(@RequestParam Long clientId, @RequestParam String formData) {

        return productOrderFrontService.getFormData(clientId, formData);
    }

    /**
     * @param projectId
     * @param clientId
     * @param attrsJson [{attrId,num,moneyOrPoint}]的json串
     * @param pickUpOrPost
     * @param postAddressId
     * @return
     */
    @RequestMapping(value = "/order/wechat", method = POST)
    public ResultData orderToGetPayParams(
            @RequestParam(defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId,
            @RequestParam Long clientId,
            @RequestParam String attrsJson,
            @RequestParam Boolean pickupOrPost,
            @RequestParam(required = false) Long postAddressId) {
        return productOrderFrontService.orderToGetPayParams(projectId, clientId, attrsJson, pickupOrPost, postAddressId);
    }

    @RequestMapping(value = "/order/pay/wechat/notify", method = {POST, GET})
    public void wechatPayNotify(HttpServletRequest request, HttpServletResponse response) {
        try {

            Map<String, String> params = WechatXmlUtil.xmlToMap(request
                    .getInputStream());

            String tradeStatus = params.get("result_code");
            if (tradeStatus != null && tradeStatus.equals("SUCCESS")) {
                String tradeNo = params.get("transaction_id");
                String out_trade_no = params.get("out_trade_no");

            }
            Map<String, Object> map = new HashMap<>();
            map.put("return_code", "SUCCESS");
            map.put("return_msg", "OK");
            response.getWriter().write(WechatXmlUtil.mapToXml(map));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw e;
        }
    }
}
