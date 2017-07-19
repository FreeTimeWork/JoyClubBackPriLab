package com.joycity.joyclub.system;

import java.util.Calendar;
import java.util.Date;

import com.joycity.joyclub.commons.constant.QuartzPreKeyConst;
import com.joycity.joyclub.system.config.TestQuartzJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by CallMeXYZ on 2017/7/10.
 */
@RestController
@Profile("development")
@RequestMapping("/test/job")
public class TestJobController {
    @Autowired
    Scheduler quartzScheduler;

    @RequestMapping("/add")
    public String addJob(@RequestParam int id, @RequestParam(defaultValue = "1") int minute) throws SchedulerException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(getTriggerKey(id))
                .startAt(new Date(calendar.getTimeInMillis()))
                .build();
        JobDetail jobDetail = JobBuilder.newJob()
                .ofType(TestQuartzJob.class)
                .usingJobData("id", id).build();
        quartzScheduler.scheduleJob(jobDetail, trigger);
        return "add job " + id + " success,start at " + minute + " minutes later";
    }

    @RequestMapping("/misfire")
    public String misfire() throws SchedulerException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(getTriggerKey(-2))
                .startAt(new Date(calendar.getTimeInMillis()))
                .withSchedule(SimpleScheduleBuilder.repeatMinutelyForTotalCount(1).withMisfireHandlingInstructionNextWithExistingCount())
                .build();
        JobDetail jobDetail = JobBuilder.newJob()
                .ofType(TestQuartzJob.class)
                .usingJobData("id", -1).build();
        quartzScheduler.scheduleJob(jobDetail, trigger);
        return "add history job " + "-2" + " success,";
    }

    @RequestMapping("/repeat/add")
    public String addRepeatJob(@RequestParam int id) throws SchedulerException {


        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(getTriggerKey(id)).withSchedule(SimpleScheduleBuilder.repeatMinutelyForever())
                .startNow()
                .build();
        JobDetail jobDetail = JobBuilder.newJob()
                .ofType(TestQuartzJob.class)
                .usingJobData("id", id).build();
        quartzScheduler.scheduleJob(jobDetail, trigger);
        return "add repeat job " + id + " success";
    }

    @RequestMapping("/remove")
    public String removeJobJob(int id) throws SchedulerException {

        quartzScheduler.unscheduleJob(getTriggerKey(id));
        return "remove job success";
    }

    @RequestMapping("/checkExist")
    public Boolean checkExist(int id) throws SchedulerException {


        return quartzScheduler.checkExists(getTriggerKey(id));
    }

    private TriggerKey getTriggerKey(int id) {
        return new TriggerKey(QuartzPreKeyConst.BATCH_LAUNCH.getName() + id);
    }
}
