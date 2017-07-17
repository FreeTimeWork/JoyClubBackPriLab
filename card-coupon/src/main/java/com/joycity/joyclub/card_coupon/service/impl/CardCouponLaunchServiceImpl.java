package com.joycity.joyclub.card_coupon.service.impl;

import static com.joycity.joyclub.commons.constant.ResultCode.LAUNCH_NUM_EXCEED_COUPON_NUM;

import java.util.Date;
import java.util.List;

import com.joycity.joyclub.card_coupon.constant.CouponLaunchReviewStatus;
import com.joycity.joyclub.card_coupon.constant.CouponLaunchType;
import com.joycity.joyclub.card_coupon.constant.CouponType;
import com.joycity.joyclub.card_coupon.mapper.CardCouponLaunchMapper;
import com.joycity.joyclub.card_coupon.mapper.CardCouponMapper;
import com.joycity.joyclub.card_coupon.modal.CreateCouponInfo;
import com.joycity.joyclub.card_coupon.modal.ShowCouponLaunchInfo;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunch;
import com.joycity.joyclub.card_coupon.service.CardCouponLaunchService;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fangchen.chai on 2017/7/12.
 */
@Service
public class CardCouponLaunchServiceImpl implements CardCouponLaunchService {

    @Autowired
    private CardCouponLaunchMapper launchMapper;
    @Autowired
    private CardCouponMapper cardCouponMapper;

    @Override
    public ResultData createCardCouponLaunch(CardCouponLaunch launch) {
        checkLaunchNum(launch);
        launchMapper.insertSelective(launch);
        return new ResultData(new CreateResult(launch.getId()));
    }

    @Override
    public ResultData getListByCouponNameAndCouponTypeAndStatus(String name, Integer type, Integer status, PageUtil pageUtil) {
        if (name != null) {
            name = "%" + name + "%";
        }
        final String finalName = name;
        final Date now = new Date();
        ResultData data = new AbstractGetListData<ShowCouponLaunchInfo>() {
            @Override
            public Long countByFilter() {
                return launchMapper.countByCouponNameAndCouponTypeAndStatus(finalName, type, status, now, pageUtil);
            }

            @Override
            public List<ShowCouponLaunchInfo> selectByFilter() {
                return launchMapper.selectByCouponNameAndCouponTypeAndStatus(finalName, type, status, now, pageUtil);
            }

        }.getList(pageUtil);

        if (data.getCode().equals(ResultCode.SUCCESS)) {
            ListResult listResult = (ListResult) data.getData();
            List<ShowCouponLaunchInfo> infos = listResult.getList();
            if (CollectionUtils.isNotEmpty(infos)) {
                for (ShowCouponLaunchInfo info : infos) {
                    ShowCouponLaunchInfo.setStatus(info, now);
                }
            }
        }
        return data;
    }

    @Override
    public ResultData confirmLaunch(Long id) {
        CardCouponLaunch launchDb = launchMapper.selectByPrimaryKey(id);
        if (!launchDb.getReviewStatus().equals(CouponLaunchReviewStatus.STATUS_REVIEW_PERMIT)) {
            throw new BusinessException(ResultCode.LAUNCH_ERROR, "只有审核通过，才可以开始投放");
        }
        CardCouponLaunch launch = new CardCouponLaunch();
        launch.setId(id);
        launch.setConfirmFlag(true);
        launch.setConfirmTime(new Date());
        return new ResultData(new UpdateResult(launchMapper.updateByPrimaryKeySelective(launch)));
    }

    @Override
    public ResultData forbidLaunch(Long id) {
        CardCouponLaunch launchDb = launchMapper.selectByPrimaryKey(id);
        if (!launchDb.getReviewStatus().equals(CouponLaunchReviewStatus.STATUS_REVIEW_PERMIT)) {
            throw new BusinessException(ResultCode.LAUNCH_ERROR, "只有审核通过，才可以开始投放");
        }

        CardCouponLaunch launch = new CardCouponLaunch();
        launch.setId(id);
        launch.setForbidFlag(true);
        launch.setForbidTime(new Date());
        return new ResultData(new UpdateResult(launchMapper.updateByPrimaryKeySelective(launch)));
    }

    @Override
    public ResultData permitLaunch(Long id) {
        CardCouponLaunch launch = new CardCouponLaunch();
        launch.setId(id);
        launch.setReviewStatus(CouponLaunchReviewStatus.STATUS_REVIEW_PERMIT);
        return new ResultData(new UpdateResult(launchMapper.updateByPrimaryKeySelective(launch)));
    }

    @Override
    public ResultData rejectLaunch(Long id, String reviewInfo) {
        CardCouponLaunch launch = new CardCouponLaunch();
        launch.setId(id);
        launch.setReviewStatus(CouponLaunchReviewStatus.STATUS_REVIEW_REJECT);
        launch.setReviewInfo(reviewInfo);
        return new ResultData(new UpdateResult(launchMapper.updateByPrimaryKeySelective(launch)));
    }

    @Override
    public ResultData deleteCardCouponLaunch(Long id) {
        return new ResultData(new UpdateResult(launchMapper.deleteCardCouponLaunchById(id)));
    }

    private void checkLaunchNum(CardCouponLaunch launch){
        CreateCouponInfo couponInfo = cardCouponMapper.selectCardCouponById(launch.getCouponId());
        int launchSum = launchMapper.selectlaunchNumByCouponId(couponInfo.getId());
        int remainNum = couponInfo.getNum() - launchSum;
        if (remainNum < launch.getLaunchNum()) {
            throw new BusinessException(LAUNCH_NUM_EXCEED_COUPON_NUM, "投放数量超过剩余发行量");
        }
        if (couponInfo.getType().equals(CouponType.DEDUCTION_COUPON)) {
            if (launch.getType().equals(CouponLaunchType.CONDITION_LAUNCH)) {
                throw new BusinessException(LAUNCH_NUM_EXCEED_COUPON_NUM, "满减券只能选择批量投放和线上投放");
            }
        } else if (couponInfo.getType().equals(CouponType.CASH_COUPON)) {
            if (!launch.getType().equals(CouponLaunchType.CONDITION_LAUNCH)) {
                throw new BusinessException(LAUNCH_NUM_EXCEED_COUPON_NUM, "代金券只能选择条件投放");
            }
        }
    }
}
