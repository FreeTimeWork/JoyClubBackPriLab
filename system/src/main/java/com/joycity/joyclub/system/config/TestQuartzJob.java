package com.joycity.joyclub.system.config;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CallMeXYZ on 2017/7/10.
 */
public class TestQuartzJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();

        System.out.println("job start id:" + jobDataMap.getInt("id") + " time: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));

    }
}
