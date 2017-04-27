package com.joycity.joyclub.coupon.service.impl;

import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.commons.utils.ThrowBusinessExceptionUtil;
import com.joycity.joyclub.coupon.mapper.CouponCardTypeMapper;
import com.joycity.joyclub.coupon.mapper.CouponCodeMapper;
import com.joycity.joyclub.coupon.mapper.CouponMapper;
import com.joycity.joyclub.coupon.modal.CouponCodeInfo;
import com.joycity.joyclub.coupon.modal.CouponForClient;
import com.joycity.joyclub.coupon.modal.generated.Coupon;
import com.joycity.joyclub.coupon.modal.generated.CouponCardType;
import com.joycity.joyclub.coupon.modal.generated.CouponCode;
import com.joycity.joyclub.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/12.
 */
@Service
public class CouponServiceImpl implements CouponService {
    /**
     * 单次sql拼接插入最大值,实际是-1
     */
    private final int MAX_INSERT_PER_SQL = 1000;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    CouponCodeMapper couponCodeMapper;
    @Autowired
    CouponCardTypeMapper couponCardTypeMapper;

    @Override
    public Coupon getById(Long id) {
        return couponMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CouponCardType> getCardTypes(Long couponId) {
        return couponCardTypeMapper.getByCouponId(couponId);
    }

    @Override
    public ResultData getCouponList(final Long projectId, final Integer type, String name, final Boolean useFlag, final PageUtil pageUtil) {
        if (name != null) {
            name = "%" + name + "%";
        }
        final String finalName = name;
        return new AbstractGetListData<Coupon>() {
            @Override
            public Long countByFilter() {
                return couponMapper.countByFilter(projectId,
                        type, finalName, useFlag, pageUtil);
            }

            @Override
            public List<Coupon> selectByFilter() {
                return couponMapper.selectByFilter(projectId, type, finalName, useFlag, pageUtil);
            }
        }.getList(pageUtil);

    }

    @Override
    public ResultData getCouponCodeList(final String type, final String code, final String phone, String name, final PageUtil pageUtil) {
        if (name != null) {
            name = "%" + name + "%";
        }
        final String finalName = name;
        return new AbstractGetListData<CouponCodeInfo>() {
            @Override
            public Long countByFilter() {
                return couponCodeMapper.countByFilter(type, code, phone, finalName, pageUtil);
            }

            @Override
            public List<CouponCodeInfo> selectByFilter() {
                return couponCodeMapper.selectByFilter(type, code, phone, finalName, pageUtil);
            }
        }.getList(pageUtil);


    }

    @Override
    public ResultData insert(Coupon coupon, String[] cardTypes) {

        couponMapper.insertSelective(coupon);
        addCardTypes(coupon.getId(), cardTypes);
        return new ResultData(new CreateResult(coupon.getId()));
    }

    /**
     * 增加允许领取的卡类型
     * 如果重复则不添加
     *
     * @param id
     * @param cardTypes
     * @return
     */
    public int addCardTypes(Long id, String[] cardTypes) {
        CouponCardType couponCardType = new CouponCardType();
        int numAdded = 0;
        for (String cardType : cardTypes) {
            if (couponCardTypeMapper.countByIdAndType(id, cardType) == 0) {
                couponCardType.setCouponId(id);
                couponCardType.setCardType(cardType);
                couponCardTypeMapper.insertSelective(couponCardType);
                numAdded++;
            }
        }
        return numAdded;
    }

    @Override
    public ResultData updateInfo(Long id, String info, String[] cardTypes) {
        Coupon coupon = createCouponObject(id);
        coupon.setInfo(info);
        return new ResultData(new UpdateResult(updateByPrimaryKeySelective(coupon)));
    }

    @Override
    public ResultData startUse(Long id) {
        Coupon coupon = createCouponObject(id);
        coupon.setUseFlag(true);
        coupon.setUseTime(new Date());
        return new ResultData(new UpdateResult(updateByPrimaryKeySelective(coupon)));
    }

    @Override
    public ResultData forbid(Long id) {
        Coupon coupon = createCouponObject(id);
        coupon.setForbidFlag(true);
        coupon.setForbidTime(new Date());
        return new ResultData(new UpdateResult(updateByPrimaryKeySelective(coupon)));
    }

    @Override
    public ResultData addCodes(Long id, List<String> codes) {
        String comma = ",";
        String quotes = "'";
        int length = codes.size();
        int insertedNum = 0;
        //MAX_INSERT_PER_SQL 里的index

        StringBuilder sb = new StringBuilder();
        for (int i = 0, j = 0; i < length; i++) {
            sb.append("(").append(id).
                    append(comma).append(quotes).append(codes.get(i))
                    .append(quotes).append(")");
            if (j == MAX_INSERT_PER_SQL || i == length - 1) {
                insertedNum += insertCodeByValuesSql(sb.toString());
                sb.setLength(0);
                j = 0;
            } else {
                j++;
                //加值之间的分隔符
                sb.append(comma);
            }
        }
        //更新卡券数量
        couponMapper.addNum(id, insertedNum);
        return new ResultData(new UpdateResult(insertedNum));

    }

    @Override
    public ResultData checkCode(Long couponId, Long managerId, String code) {
        CouponCode couponCode = couponCodeMapper.getCodeByCodeAndCouponId(couponId, code);
        String errorText = null;
        //先检查卡券号存在性
        if (couponCode == null) {
            errorText = "该卡券号不存在";
        } else {
            if (!couponCode.getUseStatus()) {
                errorText = "该卡券号未领取";
            } else if (couponCode.getCheckFlag()) {
                errorText = "该卡券号已被核销";
            } else {
                //如果卡券号还没被核销，确认下卡券现在是否可以核销（当前时间在使用期）
                Coupon coupon = couponMapper.selectByPrimaryKey(couponCode.getCouponId());
                //检查下该卡券的存在，基本上是存在的，主要是防止下一步出错
                if (coupon == null) {
                    errorText = "卡券不存在";
                } else {
                    //卡券存在的话，确定核销日期是否是卡券的使用日期
                    long nowTime = System.currentTimeMillis();
                    if (coupon.getAvailableStartTime().getTime() > nowTime || coupon.getAvailableEndTime().getTime() < nowTime) {
                        errorText = "卡券已过期";
                    }
                }
            }
        }
        if (errorText != null) {
            throw new BusinessException(ResultCode.COUPON_CHECK_ERROR, errorText);

        } else {
            //成功的话 设置卡券号为已核销
            // TODO: 2017/4/21 记录核销人
            couponCodeMapper.setCodeChecked(couponCode.getId(), managerId);
            return new ResultData("核销成功");
        }
    }

    @Override
    public ResultData getSimpleCouponList() {
        return new ResultData(new ListResult(couponMapper.getAllSimpleList()));
    }

    //////////////////////////////////////////////////////////
    @Override
    public List<CouponForClient> getCouponListForFront(Long projectId, Long clientId, String cardType, PageUtil pageUtil) {
        return couponMapper.selectForFrontByFilter(projectId, clientId, cardType, pageUtil);
    }

    @Override
    public List<CouponForClient> getCouponListForFront(Long projectId, PageUtil pageUtil) {
        return couponMapper.selectForFrontWithoutClientByFilter(projectId, pageUtil);
    }

    @Override
    public List<CouponForClient> getCouponListForFrontClient(Long clientId, PageUtil pageUtil) {
        return couponMapper.selectClientCouponsByFilter(clientId, pageUtil);
    }

    @Override
    public Integer clientReceiveCoupon(Long couponId, Long clientId) {
        CouponCode code = couponCodeMapper.getMinCodeIdOfCoupon(couponId);
        ThrowBusinessExceptionUtil.checkNull(code, "卡券已被领光了");

        Coupon coupon = couponMapper.selectByPrimaryKey(code.getCouponId());
        ThrowBusinessExceptionUtil.checkNull(coupon, "卡券已下架");
        couponCodeMapper.setCodeUsed(code.getId(), clientId);
        //并发问题
        couponMapper.addNum(coupon.getId(), -1);
        return coupon.getPointCost();

    }

    /**
     * 创建一个赋值了id的对象
     *
     * @param id
     * @return
     */

    private Coupon createCouponObject(Long id) {
        Coupon coupon = new Coupon();
        coupon.setId(id);
        return coupon;
    }

    private int updateByPrimaryKeySelective(Coupon coupon) {
        return couponMapper.updateByPrimaryKeySelective(coupon);
    }

    private int insertCodeByValuesSql(String valuesSql) {
        return couponCodeMapper.insertCodeWithValuesSql(valuesSql);
    }


}
