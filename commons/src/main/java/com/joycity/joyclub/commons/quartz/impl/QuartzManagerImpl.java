package com.joycity.joyclub.commons.quartz.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.joycity.joyclub.commons.quartz.QuartzManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by fangchen.chai on 2017/7/17.
 */
@Component
public class QuartzManagerImpl implements QuartzManager{
    private Log logger = LogFactory.getLog(QuartzManagerImpl.class);

    @Autowired
    Scheduler quartzScheduler;

    @Override
    public void addJob(Class<? extends Job> jobClazz, TriggerKey triggerKey, String dataKey, long dataValue, Date date) throws SchedulerException {
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .startAt(date)
                .build();
        JobDetail jobDetail = JobBuilder.newJob()
                .ofType(jobClazz)
                .usingJobData(dataKey, dataValue).build();
        quartzScheduler.scheduleJob(jobDetail, trigger);
        logger.info( "add job " + triggerKey + " success,start at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
    }

}
