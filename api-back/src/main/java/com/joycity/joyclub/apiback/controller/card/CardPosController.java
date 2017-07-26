package com.joycity.joyclub.apiback.controller.card;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.vo.pos.PosCheckVO;
import com.joycity.joyclub.card_coupon.service.CardPosService;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

/**
 * Created by fangchen.chai on 2017/7/24
 */
@RestController
@RequestMapping(URL_API_BACK)
public class CardPosController extends BaseUserSessionController {

    @Autowired
    private CardPosService cardPosService;

    /**
     * 得到某个会员在某个商户当前可用券
     */
    @RequestMapping(value = "/card/pos/current/coupons", method = RequestMethod.GET)
    public ResultData getCardCoupons(@RequestParam(required = false) String tel,
                                     @RequestParam(required = false) String vipCode,
                                     @RequestParam String shopCode) {

        //TODO: cfc  projectId根据秘钥对应，得到到底是哪个项目的商家
        Long projectId = 1L;

        if (tel == null && vipCode == null) {
            throw new BusinessException(ResultCode.REQUEST_PARAMS_ERROR, "手机号和vip号至少有一个！");
        }

        return cardPosService.getCurrentCoupons(projectId, shopCode, tel, vipCode);
    }

    /**
     * pos核销
     */

    @RequestMapping(value = "/card/pos/check", method = RequestMethod.POST)
    public ResultData posCheck(@Validated @RequestBody PosCheckVO vo) {

        //TODO: cfc  projectId根据秘钥对应，得到到底是哪个项目的商家
        Long projectId = 1L;
        if (vo.getCouponCodes() == null || vo.getCouponCodes().size() > 2) {

            throw new BusinessException(ResultCode.REQUEST_PARAMS_ERROR, "卡券不能为空，并且不能大于2");
        }

        return cardPosService.posCheck(projectId, vo.getCouponCodes(), vo.getOrderCode(), vo.getOrderAmount(), vo.getShopCode());
    }

    /**
     * 验证是否可以退货
     */
    @RequestMapping(value = "/card/pos/refund/verify", method = RequestMethod.GET)
    public ResultData refundVerification(@RequestParam String orderCode, @RequestParam(required = false) String shopCode) {

        return cardPosService.refundVerification(orderCode);
    }

    /**
     * pos退货通知后处理
     */
    @RequestMapping(value = "/card/pos/refund", method = RequestMethod.GET)
    public ResultData refund(@RequestParam String orderCode, @RequestParam(required = false) String shopCode) {

        return cardPosService.refund(orderCode);
    }

}
