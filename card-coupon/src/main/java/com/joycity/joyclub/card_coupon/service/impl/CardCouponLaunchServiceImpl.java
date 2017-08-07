package com.joycity.joyclub.card_coupon.service.impl;

import static com.joycity.joyclub.commons.constant.ResultCode.COUPON_LAUNCH_ERROR;
import static com.joycity.joyclub.commons.constant.ResultCode.LAUNCH_NUM_EXCEED_COUPON_NUM;

import java.util.Date;
import java.util.List;

import com.joycity.joyclub.card_coupon.cache.CardCouponCodeCache;
import com.joycity.joyclub.card_coupon.constant.CouponCodeUseStatus;
import com.joycity.joyclub.card_coupon.constant.CouponLaunchReviewStatus;
import com.joycity.joyclub.card_coupon.constant.CouponLaunchType;
import com.joycity.joyclub.card_coupon.constant.CouponType;
import com.joycity.joyclub.card_coupon.mapper.CardCouponCodeMapper;
import com.joycity.joyclub.card_coupon.mapper.CardCouponLaunchMapper;
import com.joycity.joyclub.card_coupon.mapper.CardCouponMapper;
import com.joycity.joyclub.card_coupon.mapper.CardCouponTriggerScopeMapper;
import com.joycity.joyclub.card_coupon.modal.CreateCouponInfo;
import com.joycity.joyclub.card_coupon.modal.CreateCouponLaunchInfo;
import com.joycity.joyclub.card_coupon.modal.ShowClientVisibleLaunchCoupon;
import com.joycity.joyclub.card_coupon.modal.ShowCouponLaunchInfo;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunch;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponTriggerScope;
import com.joycity.joyclub.card_coupon.quartz.BatchLaunchJob;
import com.joycity.joyclub.card_coupon.service.CardCouponLaunchService;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.constant.QuartzPreKeyConst;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.commons.quartz.QuartzManager;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
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
    @Autowired
    private CardCouponTriggerScopeMapper cardCouponTriggerScopeMapper;
    @Autowired
    private CardCouponCodeMapper cardCouponCodeMapper;
    @Autowired
    private QuartzManager quartzManager;
    @Autowired
    private CardCouponCodeCache couponCodeCache;

    @Override
    public ResultData createCardCouponLaunch(CreateCouponLaunchInfo launch) {
        if (launch.getType().equals(CouponType.DEDUCTION_COUPON)) {
            if (launch.getType().equals(CouponLaunchType.CONDITION_LAUNCH)) {
                throw new BusinessException(COUPON_LAUNCH_ERROR, "满减券只能选择批量投放和线上投放");
            }
        } else if (launch.getType().equals(CouponType.CASH_COUPON)) {
            if (!launch.getType().equals(CouponLaunchType.CONDITION_LAUNCH)) {
                throw new BusinessException(COUPON_LAUNCH_ERROR, "代金券只能选择条件投放");
            }
        }
        launchMapper.insertSelective(launch);
        if (CollectionUtils.isNotEmpty(launch.getTriggerScopeIds())) {
            CardCouponTriggerScope triggerScope = new CardCouponTriggerScope();
            triggerScope.setLaunchId(launch.getId());
            for (Long storeId : launch.getTriggerScopeIds()) {
                triggerScope.setStoreId(storeId);
                cardCouponTriggerScopeMapper.insertSelective(triggerScope);
            }
        }
        return new ResultData(new CreateResult(launch.getId()));
    }

    @Override
    public ResultData getListByCouponNameAndCouponTypeAndStatus(String couponName, Integer couponType, String name, Integer type, Integer status, PageUtil pageUtil) {
        if (name != null) {
            name = "%" + name + "%";
        }
        if (couponName != null) {
            couponName = "%" + couponName + "%";
        }
        final String finalName = name;
        final String finalCouponName = couponName;
        final Date now = new Date();
        return new AbstractGetListData<ShowCouponLaunchInfo>() {
            @Override
            public Long countByFilter() {
                return launchMapper.countByCouponNameAndCouponTypeAndStatus(finalCouponName, couponType, finalName, type, status, now, pageUtil);
            }

            @Override
            public List<ShowCouponLaunchInfo> selectByFilter() {
                return launchMapper.selectByCouponNameAndCouponTypeAndStatus(finalCouponName, couponType, finalName, type, status, now, pageUtil);
            }

        }.getList(pageUtil);
    }

    @Override
    public ResultData getCardCouponLaunchById(Long id) {
        CreateCouponLaunchInfo createCouponLaunchInfo = launchMapper.selectCouponLaunchInfoById(id);
        if (createCouponLaunchInfo != null) {

            launchResult(createCouponLaunchInfo);
        }
        return new ResultData(createCouponLaunchInfo) ;
    }

    @Override
    public ResultData confirmLaunch(Long id) throws SchedulerException {

        CardCouponLaunch launchDb = launchMapper.selectByPrimaryKey(id);
        checkLaunchNum(launchDb);
        if (!launchDb.getReviewStatus().equals(CouponLaunchReviewStatus.STATUS_REVIEW_PERMIT)) {
            throw new BusinessException(ResultCode.COUPON_LAUNCH_ERROR, "只有审核通过，才可以开始投放");
        }
        if (launchDb.getConfirmFlag()) {
            throw new BusinessException(ResultCode.COUPON_LAUNCH_ERROR, "已经投放");

        }
        //一个条件投放期间只能有一个条件投放
        if (launchDb.getType().equals(CouponLaunchType.CONDITION_LAUNCH)) {
            int num = launchMapper.verifyConditionLaunch(launchDb.getLaunchStartTime(), launchDb.getLaunchEndTime());
            if (num > 0) {
                throw new BusinessException(ResultCode.COUPON_LAUNCH_ERROR, "投放期间已存在其他条件投放");
            }
        }

        //如果是批量投放，开启定时任务
        if (launchDb.getType().equals(CouponLaunchType.BATCH_LAUNCH)) {
            quartzManager.addJob(BatchLaunchJob.class, getTriggerKey(id), QuartzPreKeyConst.BATCH_LAUNCH.getName(), id, launchDb.getLaunchStartTime());
        }

        // 添加库存缓存
        couponCodeCache.addInventory(launchDb.getId(), launchDb.getLaunchNum());

        CardCouponLaunch launch = new CardCouponLaunch();
        launch.setId(id);
        launch.setConfirmFlag(true);
        launch.setConfirmTime(new Date());
        return new ResultData(new UpdateResult(launchMapper.updateByPrimaryKeySelective(launch)));
    }

    @Override
    public ResultData forbidLaunch(Long id) throws SchedulerException {
        CardCouponLaunch launchDb = launchMapper.selectByPrimaryKey(id);
        if (!launchDb.getReviewStatus().equals(CouponLaunchReviewStatus.STATUS_REVIEW_PERMIT)) {
            throw new BusinessException(ResultCode.COUPON_LAUNCH_ERROR, "只有审核通过，才可以开始投放");
        }
        if (launchDb.getType().equals(CouponLaunchType.BATCH_LAUNCH)
                && launchDb.getConfirmFlag()
                && quartzManager.checkExist(getTriggerKey(id))) {

            quartzManager.removeJob(getTriggerKey(id));
        }

        CardCouponLaunch launch = new CardCouponLaunch();
        launch.setId(id);
        launch.setForbidFlag(true);
        launch.setForbidTime(new Date());
        return new ResultData(new UpdateResult(launchMapper.updateByPrimaryKeySelective(launch)));
    }

    @Override
    public ResultData permitLaunch(Long id) {
        CardCouponLaunch launchDb = launchMapper.selectByPrimaryKey(id);
        if (!launchDb.getReviewStatus().equals(CouponLaunchReviewStatus.STATUS_NOT_REVIEW)) {
            throw new BusinessException(ResultCode.COUPON_LAUNCH_ERROR, "只有未审核，才可以审核通过");
        }
        CardCouponLaunch launch = new CardCouponLaunch();
        launch.setId(id);
        launch.setReviewStatus(CouponLaunchReviewStatus.STATUS_REVIEW_PERMIT);
        return new ResultData(new UpdateResult(launchMapper.updateByPrimaryKeySelective(launch)));
    }

    @Override
    public ResultData rejectLaunch(Long id, String reviewInfo) {
        CardCouponLaunch launchDb = launchMapper.selectByPrimaryKey(id);
        if (!launchDb.getReviewStatus().equals(CouponLaunchReviewStatus.STATUS_NOT_REVIEW)) {
            throw new BusinessException(ResultCode.COUPON_LAUNCH_ERROR, "只有未审核，才可以审核不通过");
        }
        CardCouponLaunch launch = new CardCouponLaunch();
        launch.setId(id);
        launch.setReviewStatus(CouponLaunchReviewStatus.STATUS_REVIEW_REJECT);
        launch.setReviewInfo(reviewInfo);
        return new ResultData(new UpdateResult(launchMapper.updateByPrimaryKeySelective(launch)));
    }

    @Override
    public ResultData deleteCardCouponLaunch(Long id) {
        CardCouponLaunch launchDb = launchMapper.selectByPrimaryKey(id);
        if (!launchDb.getReviewStatus().equals(CouponLaunchReviewStatus.STATUS_NOT_REVIEW)) {
            throw new BusinessException(ResultCode.COUPON_LAUNCH_ERROR, "只有未审核，才可以删除");
        }
        return new ResultData(new UpdateResult(launchMapper.deleteCardCouponLaunchById(id)));
    }

    @Override
    public ResultData getClientVisibleListByCouponType(Long projectId, Long clientId, Byte couponType, PageUtil pageUtil) {
        return new AbstractGetListData<ShowClientVisibleLaunchCoupon>() {
            @Override
            public Long countByFilter() {
                return launchMapper.countClientVisibleByCouponType(projectId,clientId,couponType);
            }

            @Override
            public List<ShowClientVisibleLaunchCoupon> selectByFilter() {
                return launchMapper.selectClientVisibleByCouponType(projectId, clientId, couponType, pageUtil);
            }
        }.getList(pageUtil);
    }

    @Override
    public ResultData getVisitorVisibleListByCouponType(Long projectId, Byte couponType, PageUtil pageUtil) {
        return new AbstractGetListData<ShowClientVisibleLaunchCoupon>() {
            @Override
            public Long countByFilter() {
                return launchMapper.countVisitorVisibleByCouponType(projectId,couponType);
            }

            @Override
            public List<ShowClientVisibleLaunchCoupon> selectByFilter() {
                return launchMapper.selectVisitorVisibleByCouponType(projectId, couponType, pageUtil);
            }
        }.getList(pageUtil);
    }

    private void checkLaunchNum(CardCouponLaunch launch){
        CreateCouponInfo couponInfo = cardCouponMapper.selectCardCouponById(launch.getCouponId());
        if (couponInfo != null) {
            int launchSum = launchMapper.selectLaunchNumByCouponId(couponInfo.getId());
            int remainNum = couponInfo.getNum() - launchSum;
            if (remainNum < launch.getLaunchNum()) {
                throw new BusinessException(LAUNCH_NUM_EXCEED_COUPON_NUM, "投放数量超过剩余发行量");
            }

        }
    }

    /**
     * 添加投放结果
     * @param info
     */
    private void launchResult(CreateCouponLaunchInfo info){
        int receiveNum = cardCouponCodeMapper.countByLaunchId(info.getId());
        info.setReceiveNum(receiveNum);
        int usedNum = cardCouponCodeMapper.countByLaunchIdAndUseStatus(info.getId(), CouponCodeUseStatus.USED);
        info.setUsedNum(usedNum);
    }

    private TriggerKey getTriggerKey(Long launchId) {
        return new TriggerKey(QuartzPreKeyConst.BATCH_LAUNCH.getName() + "_" + launchId);
    }

}
