package com.joycity.joyclub.card_coupon.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by fangchen.chai on 2017/7/17.
 */
public class BatchLaunchJob implements Job{
    private final Log logger = LogFactory.getLog(BatchLaunchJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("start BatchLaunchJob ");
        long time = System.currentTimeMillis();

        logger.info("End start BatchLaunchJob use time "+  (System.currentTimeMillis() - time));

    }
}
