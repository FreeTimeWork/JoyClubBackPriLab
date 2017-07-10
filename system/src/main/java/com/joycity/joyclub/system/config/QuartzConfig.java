package com.joycity.joyclub.system.config;

import com.joycity.joyclub.system.profile.DevProfile;
import com.joycity.joyclub.system.profile.NotDevProfile;
import org.quartz.Scheduler;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.io.IOException;

/**
 * Created by CallMeXYZ on 2017/7/7.
 */
@Configuration
public class QuartzConfig {
    @Bean
    public SpringBeanJobFactory springBeanJobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory springBeanJobFactory = new AutowiringSpringBeanJobFactory();
        springBeanJobFactory.setApplicationContext(applicationContext);
        return springBeanJobFactory;
    }

    @Bean
    @DevProfile
    public SchedulerFactoryBean schedulerFactoryBeanDev(SpringBeanJobFactory springBeanJobFactory) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(springBeanJobFactory);
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz-dev.properties"));
        try {
            propertiesFactoryBean.afterPropertiesSet();
            schedulerFactoryBean.setQuartzProperties(propertiesFactoryBean.getObject());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return schedulerFactoryBean;
    }

    @Bean
    @NotDevProfile
    public SchedulerFactoryBean schedulerFactoryBean(SpringBeanJobFactory springBeanJobFactory) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(springBeanJobFactory);
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
        try {
            propertiesFactoryBean.afterPropertiesSet();
            schedulerFactoryBean.setQuartzProperties(propertiesFactoryBean.getObject());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return schedulerFactoryBean;
    }

    @Bean
    public Scheduler quartzScheduler(SchedulerFactoryBean schedulerFactoryBean) {
        return schedulerFactoryBean.getScheduler();
    }


}
