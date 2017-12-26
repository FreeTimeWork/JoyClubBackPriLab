package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.modal.MitenoResult;
import com.joycity.joyclub.client.service.ClientService;
import com.joycity.joyclub.client_token.service.ClientTokenService;
import com.joycity.joyclub.commons.constant.Global;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.DateTimeUtil;
import com.joycity.joyclub.recharge.RechargeFluxConfig;
import com.joycity.joyclub.recharge.constants.TelOperator;
import com.joycity.joyclub.recharge.mapper.XiangfuRechargeDetailMapper;
import com.joycity.joyclub.recharge.modal.generated.XiangfuRechargeDetail;
import com.joycity.joyclub.recharge.modal.vo.FluxTemp;
import com.joycity.joyclub.recharge.modal.vo.RechargeVO;
import com.joycity.joyclub.recharge.modal.vo.SpecListModel;
import com.joycity.joyclub.recharge.modal.vo.TelOperatorResult;
import com.joycity.joyclub.recharge.service.RechargeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;
import static com.joycity.joyclub.commons.constant.ResultCode.REQUEST_PARAM_ERROR;

/**
 * @auther fangchen.chai ON 2017/11/21
 */
@RestController
@RequestMapping(URL_API_FRONT + "/xiangfu")
public class XiangfuController {

    private Log logger = LogFactory.getLog(XiangfuController.class);

    @Autowired
    private RechargeService rechargeService;
    @Autowired
    private ClientTokenService clientTokenService;
    @Autowired
    XiangfuRechargeDetailMapper xiangfuRechargeDetailMapper;
    @Autowired
    private ClientService clientService;

    @PostMapping("/exchange")
    public ResultData xiangfuRecharge(@CookieValue(Global.COOKIE_TOKEN)String token,
                                      @RequestBody RechargeVO vo) throws Exception {
        Long clienId = clientTokenService.getIdOrThrow(token);
//        Long clienId = 1L;
        Boolean result = false;
        if (vo.getType() == null) {
            throw new BusinessException(REQUEST_PARAM_ERROR, "没有充值类型");
        }
        if (vo.getType().equals("rechargecard")) {
            result = rechargeService.rechargeMoney(vo, clienId);
        } else if (vo.getType().equals("flowcard")) {
            result = rechargeService.rechargeFlux(vo, clienId);
        }
        if (!result) {
            throw new BusinessException(ResultCode.REQUEST_PARAMS_ERROR, "充值失败!");
        }
        return new ResultData(result);
    }

    @PostMapping("/callback")
    public void xiangfuCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String agentid = request.getParameter("agentid");
        String userToken = request.getParameter("userToken");
        String orderId = request.getParameter("orderId");
        String amount = request.getParameter("amount");
        String trxamount = request.getParameter("trxamount");
        String Sid = request.getParameter("Sid");
        String returncode = request.getParameter("returncode");
        String sign = request.getParameter("sign");
        MitenoResult result = new MitenoResult();
        result.setAgentid(agentid);
        result.setAmount(amount);
        result.setOrderId(orderId);
        result.setReturncode(returncode);
        result.setSid(Sid);
        result.setSign(sign);
        result.setTrxamount(trxamount);
        result.setUserToken(userToken);
        logger.info("XiangfuController - callback-result=" + result);
        Long id = xiangfuRechargeDetailMapper.selectIdByXiangfuOrderCode(result.getOrderId());
        XiangfuRechargeDetail detail = xiangfuRechargeDetailMapper.selectByPrimaryKey(id);
        detail.setId(id);
        detail.setOutOrder(result.getSid());
        detail.setStatus(Integer.valueOf(result.getReturncode()));
        detail.setPayment(new BigDecimal(result.getTrxamount()));
        detail.setLastUpdate(null);
        //如果充值失败，积分补回
        if (detail.getStatus().equals(5)) {
            clientService.addPoint(detail.getClientId(), detail.getPoint().doubleValue());
        }
        xiangfuRechargeDetailMapper.updateByPrimaryKeySelective(detail);
        System.out.println(result);
        response.getWriter().write("success");
    }


    @PostMapping("/flux/callback")
    public void xiangfuFluxCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String customerOrderId = request.getParameter("customerOrderId");
        String operatorType = request.getParameter("operatorType");
        String phoneNo = request.getParameter("phoneNo");
        String spec = request.getParameter("spec");
        String scope = request.getParameter("scope");
        String outStatus = request.getParameter("status");
        String signature = request.getParameter("signature");
        String sign = request.getParameter("sign");
        logger.info("XiangfuController-flux-result-outStatus =" + outStatus + " customerOrderId = "+customerOrderId);
        Long id = xiangfuRechargeDetailMapper.selectIdByXiangfuOrderCode(customerOrderId);
        XiangfuRechargeDetail detail = new XiangfuRechargeDetail();
        detail.setId(id);
        if (outStatus.equals("success")) {
            detail.setStatus(4);
            xiangfuRechargeDetailMapper.updateByPrimaryKeySelective(detail);
            response.getWriter().write("success");
        } else {
            detail.setStatus(5);
            response.getWriter().write("fail");
        }
        xiangfuRechargeDetailMapper.updateByPrimaryKeySelective(detail);
    }

    @GetMapping("/tel/type")
    public ResultData getTelType(@RequestParam String tel) throws Exception {
        TelOperatorResult result = rechargeService.getTelOperator(tel);
        Map<String, String> map = new HashMap<>();
        TelOperator telOperator = TelOperator.mapNameKey.get(result.getCatName());
        if (telOperator != null) {
            map.put("type", telOperator.getCode());
        } else {
            throw new BusinessException(ResultCode.REQUEST_PARAMS_ERROR, "不支持该手机号！");
        }
        return new ResultData(map);
    }
}
