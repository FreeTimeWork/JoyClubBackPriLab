package com.joycity.joyclub.apifront.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.client.mapper.ClientUserMapper;
import com.joycity.joyclub.apifront.mapper.manual.cart.CartFrontMapper;
import com.joycity.joyclub.apifront.mapper.manual.cart.PostAddressMapper;
import com.joycity.joyclub.apifront.mapper.manual.order.ProductOrderDetailMapper;
import com.joycity.joyclub.apifront.mapper.manual.order.ProductOrderMapper;
import com.joycity.joyclub.apifront.mapper.manual.order.ProductOrderStoreMapper;
import com.joycity.joyclub.apifront.mapper.manual.product.ProductAttrFrontMapper;
import com.joycity.joyclub.apifront.modal.cart.ClientPostAddress;
import com.joycity.joyclub.client.modal.Client;
import com.joycity.joyclub.apifront.modal.order.*;
import com.joycity.joyclub.apifront.pay.wechat.PreOrder;
import com.joycity.joyclub.apifront.pay.wechat.SignUtils;
import com.joycity.joyclub.apifront.pay.wechat.WxPayConfig;
import com.joycity.joyclub.apifront.pay.wechat.WxPayService;
import com.joycity.joyclub.client.service.KeChuanCrmService;
import com.joycity.joyclub.apifront.service.ProductOrderFrontService;
import com.joycity.joyclub.apifront.service.WechatOpenIdService;
import com.joycity.joyclub.commons.utils.ThrowBusinessExceptionUtil;
import com.joycity.joyclub.apifront.util.WechatXmlUtil;
import com.joycity.joyclub.commons.constant.OrderStatus;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.joycity.joyclub.commons.constant.ResultCode.DATA_NOT_EXIST;
import static com.joycity.joyclub.commons.constant.ResultCode.REQUEST_PARAM_ERROR;
import static com.joycity.joyclub.commons.constant.OrderStatus.MAIN_ORDER_STATUS_NOT_PAYED;
import static com.joycity.joyclub.commons.constant.OrderStatus.RECEIVE_TYPE_PICKUP;
import static com.joycity.joyclub.commons.constant.OrderStatus.RECEIVE_TYPE_POST;

/**
 * Created by Administrator on 2017/4/20.
 */
@Service
public class ProductOrderFrontServiceImpl implements ProductOrderFrontService {
    private Log logger = LogFactory.getLog(ProductOrderFrontServiceImpl.class);
    @Autowired
    WechatOpenIdService wechatOpenIdService;
    @Autowired
    ProductOrderMapper orderMapper;
    @Autowired
    ProductOrderStoreMapper orderStoreMapper;
    @Autowired
    ProductOrderDetailMapper orderDetailMapper;
    @Autowired
    KeChuanCrmService keChuanCrmService;
    @Autowired
    ClientUserMapper clientMapper;
    @Autowired
    PostAddressMapper postAddressMapper;
    @Autowired
    ProductAttrFrontMapper productAttrMapper;
    @Autowired
    CartFrontMapper cartMapper;
    @Autowired
    WxPayService wxPayService;
    @Autowired
    WxPayConfig wxpayConfig;
    public final String LIST_TYPE_ALL = "all";
    public final String LIST_TYPE_NOT_PAYED = "notPayed";
    public final String LIST_TYPE_NOT_SENT = "notSent";
    public final String LIST_TYPE_NOT_RECEIVED = "notReceived";
    public final String LIST_TYPE_DONE = "done";

    @Override
    public ResultData getList(Long projectId, Long clientId, String type, PageUtil pageUtil) {
        ResultData resultData = new ResultData();
        ListResult listResult = new ListResult();
        List list = null;
        if (LIST_TYPE_ALL.equals(type)) {
            list = orderMapper.selectMyOrders(projectId, clientId, null, pageUtil);
        } else if (LIST_TYPE_NOT_PAYED.equals(type)) {
            list = orderMapper.selectMyOrders(projectId, clientId, MAIN_ORDER_STATUS_NOT_PAYED, pageUtil);
        } else {

            //注意这里前端要的订单状态 对应后端的前一状态。比如未发货对应数据库里的已付款（默认）

            if (LIST_TYPE_NOT_SENT.equals(type)) {
                //未发货快递与其他不太一样 需要是已付款并且主订单是快递的订单
                list = orderMapper.selectMyStoreOrders(projectId, clientId, OrderStatus.STORE_ORDER_STATUS_PAYED, OrderStatus.RECEIVE_TYPE_POST, pageUtil);

            } else {
                Byte storeStatus = null;
                if (LIST_TYPE_NOT_RECEIVED.equals(type)) {
                    storeStatus = OrderStatus.STORE_ORDER_STATUS_SENT;
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

    @Override
    public ResultData getFormData(Long clientId, String rawData) {
        List<ProductOrderFormDataItem> list = getFormItemList(rawData);
        //此处的积分从科传获取

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("point", clientMapper.getPoint(clientId));
        return new ResultData(result);
    }

    private List<ProductOrderFormDataItem> getFormItemList(String rawData) {
        List<Long> attrIds;
        try {
            attrIds = JSON.parseArray(rawData, Long.class);
        } catch (JSONException e) {
            throw new BusinessException(REQUEST_PARAM_ERROR, "订单初始数据错误");

        }
        if (attrIds.size() == 0) {
            throw new BusinessException(REQUEST_PARAM_ERROR, "订单商品为空");
        }
        List<ProductOrderFormDataItem> list = new ArrayList<>();
        for (Long attrId : attrIds) {
            ProductOrderFormDataItem rawDataItem = orderMapper.getOrderRawDataItem(attrId);
            if (rawDataItem != null) {
                list.add(rawDataItem);
            }
        }
        if (list.size() == 0) {
            throw new BusinessException(DATA_NOT_EXIST, "订单里所有商品都已不可用");
        }
        return list;
    }

/*    */
    /**
     * @param clientId
     * @param telParam 可以为null
     * @return
     *//*
    private Integer getVipPoint(Long clientId, String telParam) {
        String tel = telParam != null ? telParam : clientMapper.getTel(clientId);
        if (tel == null) {
            throw new BusinessException(DATA_NOT_EXIST, "会员不存在");
        }
        Client client = keChuanCrmService.getMemberByTel(tel);
        if (client == null) {
            throw new BusinessException(DATA_NOT_EXIST, "无法获取会员信息");
        }
        return client.getVipPoint();
    }

    private Integer getVipPoint(Long clientId) {
        return getVipPoint(clientId, null);
    }

    private Integer getVipPoint(String phone) {
        return getVipPoint(null, phone);
    }*/



    public static final Byte PAY_TYPE_WECHAT = 0;

    public static final Byte CANCEL_BY_CLIENT = 0;
    public static final Byte CANCEL_BY_SYSTEM = 1;


    /**
     * @param clientId
     * @param jsonStr       array list of { Long attrId,Integer num,Boolean moneyOrPoint;}
     * @param pickUpOrPost
     * @param postAddressId
     * @return
     */
    //todo 优化
    @Override
    public ResultData orderForWechat(Long projectId, Long clientId, String jsonStr, Boolean pickUpOrPost, Long postAddressId, Boolean fromCart) {
        String openId = wechatOpenIdService.getOpenId(projectId, clientId);
        if (openId == null) {
            throw new BusinessException(DATA_NOT_EXIST, "会员微信openId获取失败");
        }
        //积分

        Client client = clientMapper.selectByPrimaryKey(clientId);
        Integer nowPoint = clientMapper.getPoint(clientId);
        //先获得数据
        List<FormDataWithInfo> rawDatas;
        try {
            rawDatas = JSON.parseArray(jsonStr, FormDataWithInfo.class);
        } catch (JSONException e) {
            throw new BusinessException(REQUEST_PARAM_ERROR, "订单初始数据错误");

        }
        //给数据增加详细信息

        for (FormDataWithInfo rawData : rawDatas) {

            rawData.setInfo(orderMapper.getOrderRawDataItem(rawData.getAttrId()));
        }
        //朱订单初始化
        ProductOrder mainOrder = new ProductOrder();
        mainOrder.setProjectId(projectId);
        mainOrder.setClientId(clientId);
        mainOrder.setCode(createTradeNo());
        mainOrder.setStatus(MAIN_ORDER_STATUS_NOT_PAYED);
        mainOrder.setPayType(PAY_TYPE_WECHAT);
        //快递相关
        if (pickUpOrPost) {
            mainOrder.setReceiveType(RECEIVE_TYPE_PICKUP);
        } else {
            mainOrder.setReceiveType(RECEIVE_TYPE_POST);

            if (postAddressId == null) {

                throw new BusinessException(REQUEST_PARAM_ERROR, "订单初始数据错误");
            }
            ClientPostAddress postAddress = postAddressMapper.selectByPrimaryKey(postAddressId);
            if (postAddress == null) {

                throw new BusinessException(DATA_NOT_EXIST, "收货地址不存在");
            }
            mainOrder.setReceiverName(postAddress.getName());
            mainOrder.setReceiverPhone(postAddress.getPhone());
            mainOrder.setReceiverAddress(postAddress.getAddress());
            mainOrder.setReceiverPostcode(postAddress.getPostcode());
        }

        //初始化商户子订单，但是记住都没赋值主要的order_id
        List<StoreOrderWithDetails> storeWithDetails = new ArrayList<>();


        Integer moneySum = 0, pointSum = 0;
        for (FormDataWithInfo rawData : rawDatas) {

            ProductOrderFormDataItem info = rawData.getInfo();
            Float pointRate = info.getPointRate() != null ? info.getPointRate() : info.getBasePointRate();
            Integer itemMoney = rawData.getNum() * info.getPrice();
            Integer itemPoint = (int) (rawData.getNum() * info.getPrice() * pointRate);
            if (rawData.getMoneyOrPoint()) {
                moneySum += itemMoney;

            } else {
                pointSum += itemPoint;
            }
            //初始化商品订单信息
            ProductOrderDetail detailOrder = new ProductOrderDetail();
            detailOrder.setProductAttr(info.getAttrId());
            detailOrder.setNum(rawData.getNum());
            detailOrder.setPrice(info.getPrice());
            detailOrder.setPointRate(pointRate);
            //金钱
            if (rawData.getMoneyOrPoint()) {
                detailOrder.setMoneyPaid(itemMoney);
                detailOrder.setPointPaid(0);
            } else {
                detailOrder.setMoneyPaid(0);
                detailOrder.setPointPaid(itemPoint);
            }


            boolean hasStore = false;
            for (StoreOrderWithDetails storeWithDetail : storeWithDetails) {
                ProductOrderStore storeOrder = storeWithDetail.getOrderStore();
                if (storeOrder.getOrderId() == info.getStoreId()) {
                    hasStore = true;

                    //金钱
                    if (rawData.getMoneyOrPoint()) {
                        storeOrder.setMoneySum(storeOrder.getMoneySum() + itemMoney);
                    } else {
                        storeOrder.setPointSum(storeOrder.getPointSum() + itemPoint);
                    }
                    storeWithDetail.getOrderDetails().add(detailOrder);

                    break;
                }
            }
            if (!hasStore) {
                ProductOrderStore storeOrder = new ProductOrderStore();
                storeOrder.setStoreId(info.getStoreId());
                storeOrder.setPointSum(moneySum);
                storeOrder.setMoneySum(pointSum);
                storeOrder.setStatus(OrderStatus.STORE_ORDER_STATUS_NOT_PAYED);
                StoreOrderWithDetails storeWithDetail = new StoreOrderWithDetails();
                storeWithDetail.setOrderStore(storeOrder);
                storeWithDetail.setOrderDetails(new ArrayList<ProductOrderDetail>());
                storeWithDetail.getOrderDetails().add(detailOrder);
                storeWithDetails.add(storeWithDetail);

            }
        }
        //积分不足，提前判断，避免生成订单
        if (pointSum > nowPoint) {
            throw new BusinessException(ResultCode.VIP_POINT_NOT_ENOUGH, "积分不足");
        }
        //再统计主订单的sum
        mainOrder.setMoneySum(moneySum);
        mainOrder.setPointSum(pointSum);
        //插入主订单
        orderMapper.insertSelective(mainOrder);
        //更新商户子订单,减库存 并插入
        for (StoreOrderWithDetails storeWithDetail : storeWithDetails) {
            ProductOrderStore storeOrder = storeWithDetail.getOrderStore();
            storeOrder.setOrderId(mainOrder.getId());
            orderStoreMapper.insertSelective(storeOrder);
            for (ProductOrderDetail detail : storeWithDetail.getOrderDetails()) {
                detail.setStoreOrderId(storeOrder.getOrderId());
                orderDetailMapper.insertSelective(detail);
                //减库存
                productAttrMapper.addNum(detail.getProductAttr(), -detail.getNum());
                //减购物车
                if (fromCart) {
                    cartMapper.subCartNum(projectId, clientId, detail.getProductAttr(), detail.getNum());
                }
            }
        }

        PreOrderResult preOrderResult = new PreOrderResult();
        //金钱业务
        if (moneySum > 0) {
            //涉及金钱，应该等微信支付回调在处理积分
            preOrderResult.setIfUseWechat(true);
            preOrderResult.setPayParam(getWechatPayParams(openId, mainOrder.getMoneySum(), mainOrder.getCode()));

        } else {
            //总金钱为0，直接积分处理，支付成功
            clientMapper.setPoint(clientId, -pointSum);
            setOrderPayed(mainOrder.getCode(), null);
            // 订单已经算支付完成了
            preOrderResult.setIfUseWechat(false);
        }


        return new ResultData(preOrderResult);

    }

    @Override
    public ResultData reorderForWechat(Long orderId) {
        //纯积分业务不会在这里发生
        ProductOrder order = orderMapper.selectByPrimaryKey(orderId);
        ThrowBusinessExceptionUtil.checkNull(order, "订单不存在");
        String openId = wechatOpenIdService.getOpenId(order.getProjectId(), order.getClientId());
        ThrowBusinessExceptionUtil.checkNull(order, "获取微信信息失败");
        PreOrderResult preOrderResult = new PreOrderResult();
        preOrderResult.setIfUseWechat(true);
        preOrderResult.setPayParam(getWechatPayParams(openId, order.getMoneySum(), order.getCode()));
        return new ResultData(preOrderResult);
    }

    @Override
    public ResultData clientCancelOrder(Long orderId) {
        orderMapper.cancelOrder(orderId, CANCEL_BY_CLIENT);
        return new ResultData();
    }


    @Override
    public void wechatNotifyPayed(String code, String outCode) {
        ProductOrderExample exmaple = new ProductOrderExample();
        ProductOrderExample.Criteria criteria = exmaple.createCriteria();
        criteria.andCodeEqualTo(code);
        List<ProductOrder> list = orderMapper.selectByExample(exmaple);
        if (list.size() == 0) {
            logger.error("微信回调时发现我方单号不存在：返回值为我放单号：" + code + " 微信单号：" + outCode);
        }
        ProductOrder order = list.get(0);
        // TODO: 2017/4/20 用户可能在下订单时积分够了,在回调时积分用了
        clientMapper.setPoint(order.getClientId(), -order.getPointSum());
        setOrderPayed(code, outCode);
    }

    @Override
    public void systemCancelOrder(Long orderId) {
        orderMapper.cancelOrder(orderId, CANCEL_BY_SYSTEM);
    }

    /**
     * @param orderCode
     * @param outCode   如果是微信支付，需要赋值
     */
    private void setOrderPayed(String orderCode, String outCode) {
        Long orderId = orderMapper.getIdByCode(orderCode);
        ThrowBusinessExceptionUtil.checkNull(orderId, "订单号不存在");
        orderMapper.setPayedById(orderId);
        if (outCode != null) {
            orderMapper.setOutPayCodeById(orderId, outCode);
        }
        orderStoreMapper.setOrderPayedByMainOrderId(orderId);

    }

    public Map<String, Object> getWechatPayParams(String openId, Integer moneySum, String sysOrderCode) {
        //涉及金钱，应该等微信支付回调在处理积分
        PreOrder preOrder = new PreOrder();
        preOrder.setBody("悦客会");
        preOrder.setOpenId(openId);
        preOrder.setTotalFee(moneySum);
        preOrder.setOutTradeNo(sysOrderCode);
        String prepayResultStr = wxPayService.precreate(preOrder);

        Map<String, String> prepayResult = WechatXmlUtil.xmlToMap(prepayResultStr);
        String prepay_id = prepayResult.get("prepay_id");
        if (prepay_id == null) {
            logger.error("微信统一下单失败,错误返回值为 " + prepayResultStr);
            throw new BusinessException(ResultCode.WECHAT_PAY_REQUEST_ERROR, "微信统一下单失败");
        }
        return getWechatPayParams(prepay_id);
    }

    public Map<String, Object> getWechatPayParams(String prepayId) {
        Map<String, Object> param = new HashMap<>();
        param.put("appId", wxpayConfig.getAppid());
        param.put("timeStamp", String.valueOf(new Date().getTime()));
        param.put("nonceStr", "12345678");
        param.put("package", "prepay_id=" + prepayId);
        param.put("signType", "MD5");
        String sign = SignUtils.paySign(param, wxpayConfig.getSign());
        param.put("paySign", sign.toUpperCase());
        return param;
    }

  /*
    public void changePoint(Long clientId, String vipCode, Integer point) throws NullPointerException {
        //先验证参数
        if (clientId == null && vipCode == null) {
            throw new NullPointerException("clientId vipCode 不能同时为空");
        }
        //再给null参数赋值
        if (vipCode == null) {
            vipCode = clientMapper.getVipCodeById(clientId);
        }
        if (clientId == null) {
            clientId = clientMapper.getIdByVipCode(vipCode);
        }
        // TODO: 2017/4/21 clientUseMapper的修改积分方法
        //可能clientId或者vipcode错误 抛出异常
        if (clientId == null || vipCode == null) {
            throw new BusinessException(DATA_NOT_EXIST, "会员不存在");
        }
        Integer newPoint = keChuanCrmService.changePoint(vipCode, Double.valueOf(point));
        if (newPoint != null) {
            clientMapper.setPoint(clientId, newPoint);
        }
    }

    public void changePoint(Long clientId, Integer point) {
        changePoint(clientId, null, point);
    }

    public void changePoint(String vipCode, Integer point) {
        changePoint(null, vipCode, point);
    }*/


    private String createTradeNo() {
        String pix = String.valueOf(System.nanoTime());
        return pix + RandomStringUtils.random(8, "1234567890");
    }


    class StoreOrderWithDetails {
        ProductOrderStore orderStore;
        List<ProductOrderDetail> orderDetails;

        public List<ProductOrderDetail> getOrderDetails() {
            return orderDetails;
        }

        public void setOrderDetails(List<ProductOrderDetail> orderDetails) {
            this.orderDetails = orderDetails;
        }

        public ProductOrderStore getOrderStore() {
            return orderStore;
        }

        public void setOrderStore(ProductOrderStore orderStore) {
            this.orderStore = orderStore;
        }
    }

    class PreOrderResult {
        //是否还要微信支付
        Boolean ifUseWechat;
        //微信支付的参数
        Map<String, Object> payParam;

        public Boolean getIfUseWechat() {
            return ifUseWechat;
        }

        public void setIfUseWechat(Boolean ifUseWechat) {
            this.ifUseWechat = ifUseWechat;
        }

        public Map<String, Object> getPayParam() {
            return payParam;
        }

        public void setPayParam(Map<String, Object> payParam) {
            this.payParam = payParam;
        }
    }


}
