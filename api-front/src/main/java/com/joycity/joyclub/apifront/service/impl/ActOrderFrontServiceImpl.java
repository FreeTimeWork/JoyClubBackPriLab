package com.joycity.joyclub.apifront.service.impl;

import com.joycity.joyclub.act.mapper.ActAttrMapper;
import com.joycity.joyclub.act.mapper.ActMapper;
import com.joycity.joyclub.act.mapper.ActOrderMapper;
import com.joycity.joyclub.act.mapper.ActPriceMapper;
import com.joycity.joyclub.act.modal.ActInfoForOrder;
import com.joycity.joyclub.act.modal.MyActOrder;
import com.joycity.joyclub.act.modal.generated.SaleActOrder;
import com.joycity.joyclub.act.modal.generated.SaleActOrderExample;
import com.joycity.joyclub.act.modal.generated.SaleActPrice;
import com.joycity.joyclub.alipay.service.constant.AliPayConfig;
import com.joycity.joyclub.alipay.service.service.AliPayService;
import com.joycity.joyclub.apifront.mapper.manual.cart.PostAddressMapper;
import com.joycity.joyclub.apifront.service.ActOrderFrontService;
import com.joycity.joyclub.apifront.service.CartFrontService;
import com.joycity.joyclub.client.mapper.ClientUserMapper;
import com.joycity.joyclub.client.service.ClientService;
import com.joycity.joyclub.client.service.KeChuanCrmService;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.constant.ActOrderConst;
import com.joycity.joyclub.commons.constant.LogConst;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.order.PreOrderResult;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.commons.utils.ThrowBusinessExceptionUtil;
import com.joycity.joyclub.product.mapper.ProductAttrMapper;
import com.joycity.joyclub.product.mapper.ProductMapper;
import com.joycity.joyclub.we_chat.pay.wechat.WxPayConfig;
import com.joycity.joyclub.we_chat.service.WxPayService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.joycity.joyclub.apifront.service.impl.ProductOrderFrontServiceImpl.CANCEL_BY_CLIENT;
import static com.joycity.joyclub.apifront.service.impl.ProductOrderFrontServiceImpl.CANCEL_BY_SYSTEM;
import static com.joycity.joyclub.commons.constant.ActOrderConst.STATUS_NOT_PAYED;

/**
 * Created by Administrator on 2017/4/20.
 */
@Service
public class ActOrderFrontServiceImpl implements ActOrderFrontService {
    /**
     * 微信支付
     */
    public static final Byte PAY_TYPE_WECHAT = 0;
    /**
     * 支付宝支付
     */
    public static final Byte PAY_TYPE_ALI = 1;

    @Value("${wxpay.notifyUrl}")
    private String WX_PAY_NOTIFY_URL;
    @Value("${alipay.notifyUrl}")
    private String ALI_PAY_NOTIFY_URL;
    @Autowired
    KeChuanCrmService keChuanCrmService;
    @Autowired
    ClientService clientService;
    @Autowired
    ClientUserMapper clientMapper;
    @Autowired
    PostAddressMapper postAddressMapper;
    @Autowired
    ProductAttrMapper productAttrMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CartFrontService cartService;
    @Autowired
    WxPayService wxPayService;
    @Autowired
    WxPayConfig wxpayConfig;
    @Autowired
    AliPayConfig aliPayConfig;
    @Autowired
    AliPayService aliPayService;
    @Autowired
    ActPriceMapper actPriceMapper;
    @Autowired
    ActMapper actMapper;
    @Autowired
    ActOrderMapper actOrderMapper;
    @Autowired
    ActAttrMapper actAttrMapper;
    private Log logger = LogFactory.getLog(ActOrderFrontServiceImpl.class);
    private Log taskLogger = LogFactory.getLog(LogConst.LOG_TASK);

    @Override
    public ResultData getList(final Long projectId, final Long clientId,final Byte status, final PageUtil pageUtil) {
        return new AbstractGetListData<MyActOrder>() {
            @Override
            public Long countByFilter() {
                return actOrderMapper.countMyActOrders(projectId,clientId,status);
            }

            @Override
            public List<MyActOrder> selectByFilter() {
                return actOrderMapper.selectMyActOrders(projectId,clientId,status,pageUtil);
            }
        }.getList(pageUtil);
    }

    /*  @Override
          public ResultData getList(Long projectId, Long clientId, String type, PageUtil pageUtil) {
              ResultData resultData = new ResultData();
              ListResult listResult = new ListResult();
              List list;
              if (LIST_TYPE_ALL.equals(type)) {
                  list = orderMapper.selectMyOrders(projectId, clientId, null, pageUtil);
              } else if (LIST_TYPE_NOT_PAYED.equals(type)) {
                  list = orderMapper.selectMyOrders(projectId, clientId, MAIN_ORDER_STATUS_NOT_PAYED, pageUtil);
              } else {

                  //注意这里前端要的订单状态 对应后端的前一状态。比如未发货对应数据库里的已付款（默认）

                  if (LIST_TYPE_NOT_SENT.equals(type)) {
                      //未发货快递与其他不太一样 需要是已付款并且主订单是快递的订单
                      list = orderMapper.selectMyStoreOrders(projectId, clientId, OrderStatus.STORE_ORDER_STATUS_PAYED, OrderStatus.RECEIVE_TYPE_POST, pageUtil);

                  } else if (LIST_TYPE_NOT_RECEIVED.equals(type)) {
                      //未收货快递与其他不太一样 需要是（主订单是快递+商户订单已发货）+（主订单是自提+商户订单已付款）的订单
                      list = orderMapper.selectMyNotReceivedStoreOrders(projectId, clientId, OrderStatus.STORE_ORDER_STATUS_PAYED, OrderStatus.RECEIVE_TYPE_POST, pageUtil);

                  } else {
                      Byte storeStatus = null;
                      if (LIST_TYPE_NOT_RECEIVED.equals(type)) {
                          storeStatus = STORE_ORDER_STATUS_SENT;
                      } else if (LIST_TYPE_DONE.equals(type)) {
                          storeStatus = OrderStatus.STORE_ORDER_STATUS_RECEIVED;
                      } else {
                          throw new BusinessException(ResultCode.REQUEST_PARAM_ERROR);
                      }
                      list = orderMapper.selectMyStoreOrders(projectId, clientId, storeStatus, null, pageUtil);

                  }
              }
              listResult.setList(list);
              resultData.setData(listResult);
              return resultData;
          }
      */
    @Override
    public ResultData getFormData(Long clientId, Long attrId) {

        //此处的积分从科传获取

        Map<String, Object> result = new HashMap<>();
        ActInfoForOrder info = actMapper.getActInfoForOrderByAttr(attrId);
        if(info!=null) {
          SaleActPrice price=  actPriceMapper.getNowPrice(info.getActId());
            ThrowBusinessExceptionUtil.checkNull(price,"该活动未上架或已下架");

            info.setPointRate(price.getPointRate());
            info.setPrice(price.getPrice());
            info.setBuyType(price.getBuyType());
        }
        result.put("act", info);
        result.put("point", clientService.getPoint(clientId));
        return new ResultData(result);
    }

    @Override
    public ResultData orderForWechat(Long projectId, Long subProjectId, Long clientId, Long attrId, Boolean moneyOrPoint,Integer num) {
        return clientOrder(PAY_TYPE_WECHAT, projectId, subProjectId, clientId, attrId, moneyOrPoint,num);

    }

    @Override
    public ResultData orderForAli(Long projectId, Long subProjectId, Long clientId, Long attrId, Boolean moneyOrPoint,Integer num) {

        return clientOrder(PAY_TYPE_ALI, projectId, subProjectId, clientId, attrId, moneyOrPoint,num);
    }

    @Override
    public ResultData reorderForWechat(Long orderId) {
        return clientReOrder(orderId, PAY_TYPE_WECHAT);
    }

    @Override
    public ResultData reorderForAli(Long orderId) {
        return clientReOrder(orderId, PAY_TYPE_ALI);
    }


    private ResultData clientReOrder(Long orderId, Byte payType) {
        //纯积分业务不会在这里发生
        SaleActOrder order = actOrderMapper.selectByPrimaryKey(orderId);
        ThrowBusinessExceptionUtil.checkNull(order, "订单不存在");
        PreOrderResult preOrderResult = null;
        if (payType.equals(PAY_TYPE_WECHAT)) {
            preOrderResult = wxPayService.getWechatPreOrderResult(order.getProjectId(), order.getClientId(), order.getMoneySum(), order.getCode(),WX_PAY_NOTIFY_URL);
        } else if (payType.equals(PAY_TYPE_ALI)) {
            preOrderResult = aliPayService.getAliPreOrderResult(order.getProjectId(), order.getMoneySum(), order.getCode(),ALI_PAY_NOTIFY_URL);

        }

        return new ResultData(preOrderResult);

    }

    //todo 优化

    /**
     * 生成主订单、次订单、店铺订单、
     *
     * @param subProjectId 可空
     */


    /**
     * @param payType      支付方式
     * @param clientId
     * @param subProjectId 可空
     * @return
     */
    private ResultData clientOrder(Byte payType, Long projectId, Long subProjectId, Long clientId, Long attrId, Boolean moneyOrPoint,Integer num) {

        SaleActOrder order = createOrder(payType, projectId, subProjectId, clientId, attrId, moneyOrPoint,num);
        PreOrderResult preOrderResult = null;
        //金钱业务
        if (order.getMoneySum() > 0) {
            if (payType.equals(PAY_TYPE_WECHAT)) {
                preOrderResult = wxPayService.getWechatPreOrderResult(projectId, clientId, order.getMoneySum(), order.getCode(),WX_PAY_NOTIFY_URL);
            } else if (payType.equals(PAY_TYPE_ALI)) {
                preOrderResult = aliPayService.getAliPreOrderResult(projectId, order.getMoneySum(), order.getCode(), ALI_PAY_NOTIFY_URL);
            }
        } else {
            //总金钱为0，直接积分处理，支付成功
            clientService.addPoint(clientId, (double) -order.getPointSum());
            setOrderPayed(order.getCode(), null, payType);
            // 订单已经算支付完成了
            preOrderResult = new PreOrderResult();
            preOrderResult.setIfUseMoney(false);
        }
        //减库存
        actAttrMapper.addNum(attrId, -order.getNum());
        return new ResultData(preOrderResult);
    }

    private SaleActOrder createOrder(Byte payType, Long projectId, Long subProjectId, Long clientId, Long attrId, Boolean moneyOrPoint,Integer num) {
        if (num < 0) {
            throw new BusinessException(ResultCode.REQUEST_PARAM_ERROR, "数量不能为负数");
        }
        ActInfoForOrder actInfo = actMapper.getActInfoForOrderByAttr(attrId);
        ThrowBusinessExceptionUtil.checkNull(actInfo, "该活动不存在");
        if ((actInfo.getNum() - num) < 0) {
            throw new BusinessException(ResultCode.REQUEST_PARAM_ERROR, "该活动属性已售罄");
        }

        SaleActPrice actPrice = actPriceMapper.getNowPrice(actInfo.getActId());
        ThrowBusinessExceptionUtil.checkNull(actPrice, "该活动未上架或者已下架");
        //检查购买方式是否符合价格里规定的
        if ((moneyOrPoint && actPrice.getBuyType().equals(ActOrderConst.BUY_TYPE_ONLY_POINT)) || (!moneyOrPoint && actPrice.getBuyType().equals(ActOrderConst.BUY_TYPE_ONLY_MONEY))) {
            throw new BusinessException(ResultCode.REQUEST_PARAM_ERROR, "活动购买方式选择错误");
        }
        Float moneySum = 0f;
        Integer pointSum = 0;
        if (moneyOrPoint) {
            moneySum = actPrice.getPrice() * num;
        } else {
            pointSum = ((int) Math.ceil(actPrice.getPrice() * num * (actPrice.getPointRate() == null ? actInfo.getBasePointRate() : actPrice.getPointRate())));
        }
        //积分
        Integer nowPoint = clientMapper.getPoint(clientId);
        //积分不足，提前判断，避免生成订单
        if (pointSum > nowPoint) {
            throw new BusinessException(ResultCode.VIP_POINT_NOT_ENOUGH, "积分不足");
        }


        SaleActOrder mainOrder = new SaleActOrder();
        mainOrder.setProjectId(projectId);
        mainOrder.setClientId(clientId);
        mainOrder.setAttrId(attrId);
        mainOrder.setCode(createTradeNo());
        mainOrder.setStatus(STATUS_NOT_PAYED);
        mainOrder.setPayType(payType);
        mainOrder.setSubProjectId(subProjectId);
        mainOrder.setNum(num);


        //再统计主订单的sum
        mainOrder.setMoneySum(moneySum);
        mainOrder.setPointSum(pointSum);
        //插入主订单
        actOrderMapper.insertSelective(mainOrder);

        return mainOrder;

    }


    /**
     * 支付成功返回回调，进行订单支付成功业务处理
     *
     * @param code
     * @param outCode
     */

    @Override
    public Boolean notifyPayed(String code, String outCode, Byte payType) {
        SaleActOrderExample example = new SaleActOrderExample();
        SaleActOrderExample.Criteria criteria = example.createCriteria();
        criteria.andCodeEqualTo(code);
        List<SaleActOrder> list = actOrderMapper.selectByExample(example);
        if (list.size() == 0) {
            logger.error("活动订单回调时发现我方单号不存在，返回值为：我方单号：" + code + " 对方单号：" + outCode + " payType:" + payType);
            return false;
        }
        SaleActOrder order = list.get(0);
        // TODO: 2017/4/20 用户可能在下订单时积分够了,在回调时积分用了
        clientMapper.setPoint(order.getClientId(), -order.getPointSum());
        setOrderPayed(code, outCode, payType);
        return true;
    }


    @Override
    public void cancelOverOneHourOrder() {
        List<Long> orderIds = actOrderMapper.getOneHourNotPayedOrder();
        for (Long orderId : orderIds) {
            actOrderMapper.cancelOrder(orderId, CANCEL_BY_SYSTEM);
            //恢复库存
            restoreCancelledOrderAttrNum(orderId);
        }
        if (orderIds.size() > 0) {
            taskLogger.info("取消超时未支付活动订单 " + orderIds.size() + " 个");
        }
    }

    @Override
    public ResultData clientCancelOrder(Long orderId) {
        actOrderMapper.cancelOrder(orderId, CANCEL_BY_CLIENT);
        restoreCancelledOrderAttrNum(orderId);
        return new ResultData();
    }

    @Override
    public ResultData checkOrder(Long orderId) {
        actOrderMapper.checkOrder(orderId);
        return new ResultData();
    }


    private void restoreCancelledOrderAttrNum(Long orderId) {
        SaleActOrder order = actOrderMapper.selectByPrimaryKey(orderId);
        if (order != null) {
            actAttrMapper.addNum(order.getAttrId(), order.getNum());
        }
    }

    /**
     * 一开始下单时的支付方式已经定过了，这里再次赋值payType,主要是重新下单时的支付方式可能会变化
     *
     * @param orderCode
     * @param outCode
     */
    private void setOrderPayed(String orderCode, String outCode, Byte payType) {
        Long orderId = actOrderMapper.getIdByCode(orderCode);
        ThrowBusinessExceptionUtil.checkNull(orderId, "订单号不存在");
        actOrderMapper.setPayedById(orderId);
        if (outCode != null) {
            actOrderMapper.setOutPayCodeById(orderId, outCode, payType);
        }

    }

    // TODO: 2017/4/28 优化
    private String createTradeNo() {
        String pix = String.valueOf(System.nanoTime());
        return pix + RandomStringUtils.random(8, "1234567890");
    }


}
