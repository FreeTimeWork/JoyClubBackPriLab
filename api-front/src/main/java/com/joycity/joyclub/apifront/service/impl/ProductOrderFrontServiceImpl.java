package com.joycity.joyclub.apifront.service.impl;

import com.joycity.joyclub.alipay.service.AliPayService;
import com.joycity.joyclub.alipay.service.constant.AliPayConfig;
import com.joycity.joyclub.alipay.service.modal.AliPayStoreInfo;
import com.joycity.joyclub.apifront.mapper.manual.cart.PostAddressMapper;
import com.joycity.joyclub.apifront.modal.cart.ClientPostAddress;
import com.joycity.joyclub.apifront.modal.project.ProductOrderItem;
import com.joycity.joyclub.apifront.modal.vo.product.order.ProductOrderItemVO;
import com.joycity.joyclub.apifront.modal.vo.product.order.ProductOrderVO;
import com.joycity.joyclub.we_chat.pay.wechat.PreOrder;
import com.joycity.joyclub.we_chat.pay.wechat.SignUtils;
import com.joycity.joyclub.we_chat.pay.wechat.WxPayConfig;
import com.joycity.joyclub.we_chat.pay.wechat.WxPayService;
import com.joycity.joyclub.apifront.service.ActOrderFrontService;
import com.joycity.joyclub.apifront.service.CartFrontService;
import com.joycity.joyclub.apifront.service.ProductOrderFrontService;
import com.joycity.joyclub.we_chat.util.WechatXmlUtil;
import com.joycity.joyclub.client.mapper.ClientUserMapper;
import com.joycity.joyclub.client.service.ClientService;
import com.joycity.joyclub.client.service.KeChuanCrmService;
import com.joycity.joyclub.commons.constant.Global;
import com.joycity.joyclub.commons.constant.LogConst;
import com.joycity.joyclub.commons.constant.OrderStatus;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.commons.utils.ThrowBusinessExceptionUtil;
import com.joycity.joyclub.product.mapper.*;
import com.joycity.joyclub.product.modal.ProductOrderFormDataItem;
import com.joycity.joyclub.product.modal.generated.SaleProductOrder;
import com.joycity.joyclub.product.modal.generated.SaleProductOrderDetail;
import com.joycity.joyclub.product.modal.generated.SaleProductOrderExample;
import com.joycity.joyclub.product.modal.generated.SaleProductOrderStore;
import com.joycity.joyclub.we_chat.service.WechatOpenIdService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.joycity.joyclub.commons.constant.OrderStatus.*;
import static com.joycity.joyclub.commons.constant.ResultCode.DATA_NOT_EXIST;
import static com.joycity.joyclub.commons.constant.ResultCode.REQUEST_PARAM_ERROR;

/**
 * Created by Administrator on 2017/4/20.
 */
@Service
public class ProductOrderFrontServiceImpl implements ProductOrderFrontService {
    /**
     * 微信支付
     */
    public static final Byte PAY_TYPE_WECHAT = 0;
    /**
     * 支付宝支付
     */
    public static final Byte PAY_TYPE_ALI = 1;
    public static final Byte CANCEL_BY_CLIENT = 0;
    public static final Byte CANCEL_BY_SYSTEM = 1;
    public final String LIST_TYPE_ALL = "all";
    public final String LIST_TYPE_NOT_PAYED = "notPayed";
    public final String LIST_TYPE_NOT_SENT = "notSent";
    public final String LIST_TYPE_NOT_RECEIVED = "notReceived";
    public final String LIST_TYPE_DONE = "done";
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
    ActOrderFrontService actOrderService;



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
    private Log logger = LogFactory.getLog(ProductOrderFrontServiceImpl.class);
    private Log taskLogger = LogFactory.getLog(LogConst.LOG_TASK);

    @Override
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

    @Override
    public ResultData getFormData(Long clientId, List<Long> attrIds) {
        /**
         * 特价商品只能购买（包括未支付）一次，会过滤购买过的特价商品
         */
        Boolean ifRemoveBoughtSpecialPrice = false;
        if (attrIds.size() == 0) {
            throw new BusinessException(REQUEST_PARAM_ERROR, "订单商品为空");
        }
        List<ProductOrderFormDataItem> list = new ArrayList<>();
        for (Long attrId : attrIds) {
            ProductOrderFormDataItem rawDataItem = orderMapper.getOrderRawDataItem(attrId);

            if (rawDataItem != null) {
                //不是特价商品 或者是特价商品但是没买过
                if (!rawDataItem.getSpecialPriceFlag() || productMapper.countProductNotCanceledOrder(rawDataItem.getProductId(), clientId) == 0) {
                    list.add(rawDataItem);
                } else {
                    ifRemoveBoughtSpecialPrice = true;
                }

            }
        }
        /*if (list.size() == 0) {
            throw new BusinessException(DATA_NOT_EXIST, "订单里所有商品都已不可用");
        }
        */

        //此处的积分从科传获取

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("point", clientService.getPoint(clientId));
        result.put("ifRemoveSpecialPrice", ifRemoveBoughtSpecialPrice);
        return new ResultData(result);
    }

    @Override
    public ResultData orderForWechat(Long clientId, ProductOrderVO vo) {
        return clientOrder(PAY_TYPE_WECHAT, clientId, vo);

    }

    @Override
    public ResultData orderForAli(Long clientId, ProductOrderVO vo) {

        return clientOrder(PAY_TYPE_ALI, clientId, vo);
    }


    //todo 优化

    /**
     * 生成主订单、次订单、店铺订单、
     */
    private SaleProductOrder createOrder(Byte payType, Long clientId, ProductOrderVO vo) {

        //积分
        Integer nowPoint = clientMapper.getPoint(clientId);
        //先获得数据
        List<ProductOrderItem> items = new ArrayList<>();


        for (ProductOrderItemVO voItem : vo.getItems()) {
            ProductOrderItem item = new ProductOrderItem(voItem);
            item.setInfo(orderMapper.getOrderRawDataItem(item.getAttrId()));
        }
        //朱订单初始化
        SaleProductOrder mainOrder = new SaleProductOrder();
        mainOrder.setProjectId(vo.getProjectId());
        mainOrder.setClientId(clientId);
        mainOrder.setCode(createTradeNo());
        mainOrder.setStatus(MAIN_ORDER_STATUS_NOT_PAYED);
        mainOrder.setPayType(payType);
        mainOrder.setSubProjectId(vo.getSubProjectId());
        //快递相关
        if (vo.getPickupOrPost()) {
            mainOrder.setReceiveType(RECEIVE_TYPE_PICKUP);
        } else {
            mainOrder.setReceiveType(RECEIVE_TYPE_POST);

            if (vo.getPostAddressId() == null) {

                throw new BusinessException(REQUEST_PARAM_ERROR, "订单初始数据错误");
            }
            ClientPostAddress postAddress = postAddressMapper.selectByPrimaryKey(vo.getPostAddressId());
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


        Float moneySum = 0f;
        Integer pointSum = 0;
        for (ProductOrderItem rawData : items) {

            ProductOrderFormDataItem info = rawData.getInfo();
            Float pointRate = info.getPointRate() != null ? info.getPointRate() : info.getBasePointRate();
            Float itemMoney = rawData.getNum() * info.getPrice();
            // TODO: 2017/5/8  向上取整
            Integer itemPoint = ((int) Math.ceil(rawData.getNum() * info.getPrice() * pointRate));
            if (rawData.getMoneyOrPoint()) {
                moneySum += itemMoney;

            } else {
                pointSum += itemPoint;
            }
            //初始化商品订单信息
            SaleProductOrderDetail detailOrder = new SaleProductOrderDetail();
            detailOrder.setProductAttr(info.getAttrId());
            detailOrder.setNum(rawData.getNum());
            detailOrder.setPrice(info.getPrice());
            detailOrder.setPointRate(pointRate);
            //金钱
            if (rawData.getMoneyOrPoint()) {
                detailOrder.setMoneyPaid(itemMoney);
                detailOrder.setPointPaid(0);
            } else {
                detailOrder.setMoneyPaid(0f);
                detailOrder.setPointPaid(itemPoint);
            }


            boolean hasStore = false;
            for (StoreOrderWithDetails storeWithDetail : storeWithDetails) {
                SaleProductOrderStore storeOrder = storeWithDetail.getOrderStore();
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
                SaleProductOrderStore storeOrder = new SaleProductOrderStore();
                storeOrder.setStoreId(info.getStoreId());
                storeOrder.setPointSum(pointSum);
                storeOrder.setMoneySum(moneySum);
                storeOrder.setStatus(OrderStatus.STORE_ORDER_STATUS_NOT_PAYED);
                StoreOrderWithDetails storeWithDetail = new StoreOrderWithDetails();
                storeWithDetail.setOrderStore(storeOrder);
                storeWithDetail.setOrderDetails(new ArrayList<>());
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
            SaleProductOrderStore storeOrder = storeWithDetail.getOrderStore();
            storeOrder.setOrderId(mainOrder.getId());
            orderStoreMapper.insertSelective(storeOrder);
            for (SaleProductOrderDetail detail : storeWithDetail.getOrderDetails()) {
                detail.setStoreOrderId(storeOrder.getId());
                orderDetailMapper.insertSelective(detail);
                //减库存
                productAttrMapper.addNum(detail.getProductAttr(), -detail.getNum());
                //减购物车
                if (vo.getFromCart()) {
                    cartService.subCartNum(vo.getProjectId(), clientId, detail.getProductAttr(), detail.getNum());
                }
            }
        }
        return mainOrder;

    }

    /**
     * @param payType 支付方式
     */
    private ResultData clientOrder(Byte payType, Long clientId, ProductOrderVO vo) {
        SaleProductOrder order = createOrder(payType, clientId, vo);
        PreOrderResult preOrderResult = null;
        //金钱业务
        if (order.getMoneySum() > 0) {
            if (payType.equals(PAY_TYPE_WECHAT)) {
                preOrderResult = getWechatPreOrderResult(vo.getProjectId(), clientId, order.getMoneySum(), order.getCode());
            } else if (payType.equals(PAY_TYPE_ALI)) {
                preOrderResult = getAliPreOrderResult(vo.getProjectId(), order.getMoneySum(), order.getCode());
            }
        } else {
            //总金钱为0，直接积分处理，支付成功
            clientService.addPoint(clientId, (double) -order.getPointSum());
            setOrderPayed(order.getCode(), null, payType);
            // 订单已经算支付完成了
            preOrderResult = new PreOrderResult();
            preOrderResult.setIfUseMoney(false);
        }
        return new ResultData(preOrderResult);
    }

    /**
     * 生成微信支付相关的参数
     *
     * @param projectId
     * @param clientId
     * @param code
     * @param moneySum
     * @return
     */
    private PreOrderResult getWechatPreOrderResult(Long projectId, Long clientId, Float moneySum, String code) {
        //先判断openid存在性
        //getOpneId需要的所在的微信项目id,如果是平台悦客会或者购物中心，微信项目id就是项目id，如果是商业或者地产悦客会，微信项目id对应的是商业悦客会的项目id
        String openId = wechatOpenIdService.getOpenId(projectId, clientId);
        if (openId == null) {
            throw new BusinessException(DATA_NOT_EXIST, "会员微信openId获取失败");
        }
        PreOrderResult preOrderResult = new PreOrderResult();
        //涉及金钱，应该等微信支付回调在处理积分
        preOrderResult.setIfUseMoney(true);
        preOrderResult.setPayParam(getWechatPayParams(projectId, openId, moneySum, code));
        return preOrderResult;
    }

    /**
     * 生成支付宝支付相关的参数
     *
     * @param projectId
     * @param code
     * @param moneySum
     * @return
     */
    private PreOrderResult getAliPreOrderResult(Long projectId, Float moneySum, String code) {
        PreOrderResult preOrderResult = new PreOrderResult();
        //涉及金钱，应该等微信支付回调在处理积分
        preOrderResult.setIfUseMoney(true);
        AliPayStoreInfo storeInfo = null;
        if (projectId.equals(Global.PLATFORM_ID)) {

            storeInfo = new AliPayStoreInfo(aliPayConfig.getAppId(), aliPayConfig.getPrivateKey(), aliPayConfig.getPublicKey());

        } else {
            // TODO: 2017/5/9 非项目用户
        }
        //
        String formStr = aliPayService.wapPay(code, moneySum, storeInfo);
        preOrderResult.setFormString(formStr);
        return preOrderResult;
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
        SaleProductOrder order = orderMapper.selectByPrimaryKey(orderId);
        ThrowBusinessExceptionUtil.checkNull(order, "订单不存在");
        PreOrderResult preOrderResult = null;
        if (payType.equals(PAY_TYPE_WECHAT)) {
            preOrderResult = getWechatPreOrderResult(order.getProjectId(), order.getClientId(), order.getMoneySum(), order.getCode());
        } else if (payType.equals(PAY_TYPE_ALI)) {
            preOrderResult = getAliPreOrderResult(order.getProjectId(), order.getMoneySum(), order.getCode());

        }

        return new ResultData(preOrderResult);

    }

    @Override
    public ResultData clientCancelOrder(Long orderId) {
        restoreCancelledOrderAttrNum(orderId);
        orderMapper.cancelOrder(orderId, CANCEL_BY_CLIENT);
        orderStoreMapper.setOrderStatusByMainOrderId(orderId, OrderStatus.STORE_ORDER_STATUS_CANCELED);
        return new ResultData();
    }

    /**
     * 支付成功返回回调，进行订单支付成功业务处理
     * 包括商品和活动
     * 先检查code是否是商品订单，如果不是，则去寻找商品订单
     *
     * @param code
     * @param outCode
     */
    private void notifyPayed(String code, String outCode, Byte payType) {
        SaleProductOrderExample exmaple = new SaleProductOrderExample();
        SaleProductOrderExample.Criteria criteria = exmaple.createCriteria();
        criteria.andCodeEqualTo(code);
        List<SaleProductOrder> list = orderMapper.selectByExample(exmaple);
        if (list.size() == 0) {
            Boolean actResult = actOrderService.notifyPayed(code, outCode, payType);
            if (!actResult) {
                logger.error("商品订单回调时发现我方单号不存在，返回值为：我方单号：" + code + " 对方单号：" + outCode + " payType:" + payType);
            }
            return;
        }
        SaleProductOrder order = list.get(0);
        // TODO: 2017/4/20 用户可能在下订单时积分够了,在回调时积分用了
        clientMapper.setPoint(order.getClientId(), -order.getPointSum());
        setOrderPayed(code, outCode, payType);

    }

    @Override
    public void wechatNotifyPayed(String code, String outCode) {
        notifyPayed(code, outCode, PAY_TYPE_WECHAT);
    }

    @Override
    public void aliNotifyPayed(String code, String outCode) {
        notifyPayed(code, outCode, PAY_TYPE_ALI);
    }

    @Override
    public void systemCancelOrder(Long orderId) {
        restoreCancelledOrderAttrNum(orderId);
        orderMapper.cancelOrder(orderId, CANCEL_BY_SYSTEM);
        orderStoreMapper.setOrderStatusByMainOrderId(orderId, OrderStatus.STORE_ORDER_STATUS_CANCELED);

    }

    @Override
    public void cancelOverOneHourOrder() {
        List<Long> orderIds = orderMapper.getOneHourNotPayedOrder();
        for (Long orderId : orderIds) {
            systemCancelOrder(orderId);
        }
        if (orderIds.size() > 0) {
            taskLogger.info("取消超时未支付活动订单 " + orderIds.size() + " 个");
        }
    }

    @Override
    public void receiveOverTenDayStoreOrder() {
        orderStoreMapper.setDeliveredTenDayReceived(STORE_ORDER_STATUS_SENT, STORE_ORDER_STATUS_RECEIVED);
    }

    /**
     * 恢复取消的订单下所有商品属性的库存
     *
     * @param orderId
     */
//// TODO: 2017/4/27 这个会被定时任务不断执行，需要优化
    private void restoreCancelledOrderAttrNum(Long orderId) {
        List<SaleProductOrderDetail> details = orderMapper.getAllSimpleOrderDetails(orderId);
        for (SaleProductOrderDetail detail : details) {
            productAttrMapper.addNum(detail.getProductAttr(), detail.getNum());
        }
    }

    /**
     * 一开始下单时的支付方式已经定过了，这里再次赋值payType,主要是重新下单时的支付方式可能会变化
     *
     * @param orderCode
     * @param outCode
     */
    private void setOrderPayed(String orderCode, String outCode, Byte payType) {
        Long orderId = orderMapper.getIdByCode(orderCode);
        ThrowBusinessExceptionUtil.checkNull(orderId, "订单号不存在");
        orderMapper.setPayedById(orderId);
        if (outCode != null) {
            orderMapper.setOutPayCodeById(orderId, outCode, payType);
        }
        orderStoreMapper.setOrderStatusAndPayTypeByMainOrderId(orderId, payType, OrderStatus.STORE_ORDER_STATUS_PAYED);

    }

    public Map<String, Object> getWechatPayParams(Long projectId, String openId, float moneySum, String sysOrderCode) {
        //涉及金钱，应该等微信支付回调在处理积分
        PreOrder preOrder = new PreOrder();
        preOrder.setBody("悦客会");
        preOrder.setOpenId(openId);
        //转成分
        preOrder.setTotalFee((int) Math.ceil(moneySum * 100));
        preOrder.setOutTradeNo(sysOrderCode);

        // TODO: 2017/5/11  增加项目的微信支付传参传参
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

    // TODO: 2017/4/28 优化
    private String createTradeNo() {
        String pix = String.valueOf(System.nanoTime());
        return pix + RandomStringUtils.random(8, "1234567890");
    }


    class StoreOrderWithDetails {
        SaleProductOrderStore orderStore;
        List<SaleProductOrderDetail> orderDetails;

        public List<SaleProductOrderDetail> getOrderDetails() {
            return orderDetails;
        }

        public void setOrderDetails(List<SaleProductOrderDetail> orderDetails) {
            this.orderDetails = orderDetails;
        }

        public SaleProductOrderStore getOrderStore() {
            return orderStore;
        }

        public void setOrderStore(SaleProductOrderStore orderStore) {
            this.orderStore = orderStore;
        }
    }

    /**
     * 前台下单的返回值
     */
    class PreOrderResult {
        //是否要金钱支付
        Boolean ifUseMoney;
        //微信支付的参数
        Map<String, Object> payParam;
        //支付宝支付的返回参数，form表单的string
        String formString;

        public Boolean getIfUseMoney() {
            return ifUseMoney;
        }

        public void setIfUseMoney(Boolean ifUseMoney) {
            this.ifUseMoney = ifUseMoney;
        }

        public Map<String, Object> getPayParam() {
            return payParam;
        }

        public void setPayParam(Map<String, Object> payParam) {
            this.payParam = payParam;
        }

        public String getFormString() {
            return formString;
        }

        public void setFormString(String formString) {
            this.formString = formString;
        }
    }


}
