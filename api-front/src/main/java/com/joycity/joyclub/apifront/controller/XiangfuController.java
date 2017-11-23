package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.modal.MitenoResult;
import com.joycity.joyclub.client_token.service.ClientTokenService;
import com.joycity.joyclub.commons.constant.Global;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.DateTimeUtil;
import com.joycity.joyclub.recharge.RechargeFluxConfig;
import com.joycity.joyclub.recharge.mapper.XiangfuRechargeDetailMapper;
import com.joycity.joyclub.recharge.modal.generated.XiangfuRechargeDetail;
import com.joycity.joyclub.recharge.modal.vo.FluxTemp;
import com.joycity.joyclub.recharge.modal.vo.RechargeVO;
import com.joycity.joyclub.recharge.modal.vo.SpecListModel;
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

    @PostMapping("/recharge")
    public ResultData xiangfuRecharge(/*@CookieValue(Global.COOKIE_TOKEN)String token,*/
                                      @RequestBody RechargeVO vo) throws Exception {
//        Long clienId = clientTokenService.getIdOrThrow(token);
        Long clienId = 1L;
        String result = null;
        if (vo.getType() == null) {
            new BusinessException(REQUEST_PARAM_ERROR, "没有充值类型");
        }
        if (vo.getType().equals("rechargecard")) {
            result = rechargeService.rechargeMoney(vo, clienId);
        } else if (vo.getType().equals("flowcard")) {
            result = rechargeService.rechargeFlux(vo, clienId);
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
        Long id = xiangfuRechargeDetailMapper.selectIdByXiangfuOrderCode(result.getOrderId());
        XiangfuRechargeDetail detail = new XiangfuRechargeDetail();
        detail.setId(id);
        detail.setOutOrder(result.getSid());
        detail.setPayment(new BigDecimal(result.getTrxamount()));
        xiangfuRechargeDetailMapper.updateByPrimaryKeySelective(detail);
        System.out.println(result);
        logger.info("XiangfuController - callback-result=" + result);
        response.getWriter().write("success");
    }


//    @PostMapping("/flux/callback")
//    public void xiangfuFluxCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String customerOrderId = request.getParameter("customerOrderId");
//        String operatorType = request.getParameter("operatorType");
//        String phoneNo = request.getParameter("phoneNo");
//        String spec = request.getParameter("spec");
//        String scope = request.getParameter("scope");
//        String Status = request.getParameter("Status");
//        String signature = request.getParameter("signature");
//        String sign = request.getParameter("sign");
//        Long id = xiangfuRechargeDetailMapper.selectIdByXiangfuOrderCode(customerOrderId);
//        XiangfuRechargeDetail detail = new XiangfuRechargeDetail();
//        detail.setId(id);
//        detail.setOutOrder(result.getSid());
//        detail.setPayment(new BigDecimal(result.getTrxamount()));
//        xiangfuRechargeDetailMapper.updateByPrimaryKeySelective(detail);
//        System.out.println(result);
//        logger.info("XiangfuController - callback-result=" + result);
//        response.getWriter().write("success");
//    }

    @GetMapping("/tel/type")
    public ResultData getTelType(@RequestParam String tel) throws Exception {
        FluxTemp temp = new FluxTemp();
        temp.setProvince("110");
        temp.setTimeStamp(DateTimeUtil.formatYYYYMMDDHHMMSS(new Date()));
        SpecListModel model = rechargeService.getSpecList(tel, temp);
        return new ResultData(model.getMo());
    }
}
