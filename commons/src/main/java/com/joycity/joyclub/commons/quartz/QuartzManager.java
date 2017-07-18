package com.joycity.joyclub.commons.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;

/**
 * Created by fangchen.chai on 2017/7/17.
 */
public interface QuartzManager {

    void addJob(Class<? extends Job> jobClazz, TriggerKey triggerKey, String dataKey, long dataValue, Date date) throws SchedulerException;
}
