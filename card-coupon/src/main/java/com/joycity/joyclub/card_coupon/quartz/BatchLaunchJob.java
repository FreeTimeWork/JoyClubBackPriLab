package com.joycity.joyclub.card_coupon.quartz;

import com.joycity.joyclub.card_coupon.service.CardCouponCodeService;
import com.joycity.joyclub.commons.constant.LogConst;
import com.joycity.joyclub.commons.constant.QuartzPreKeyConst;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by fangchen.chai on 2017/7/17.
 */
public class BatchLaunchJob implements Job{
    private final Log logger = LogFactory.getLog(LogConst.LOG_TASK);

    @Autowired
    private CardCouponCodeService cardCouponCodeService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("start BatchLaunchJob ");
        long time = System.currentTimeMillis();

        Long launchId = jobExecutionContext.getMergedJobDataMap().getLong(QuartzPreKeyConst.BATCH_LAUNCH.getName());
        cardCouponCodeService.batchCreateCouponCode(launchId);
        logger.info("End start BatchLaunchJob use time : "+  (System.currentTimeMillis() - time));

    }
}
