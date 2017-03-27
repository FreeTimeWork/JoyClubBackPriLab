package com.joycity.joyclub.system.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * RootConfig
 *
 * @author CallMeXYZ
 * @date 2016/6/23
 */
@Configuration
/*@EnableScheduling*/
@ComponentScan(basePackages = {"com.joycity.joyclub"}, excludeFilters = {@ComponentScan.Filter(Controller.class)})
public class RootConfig {

/**
 * 属性文件读取
 * @param applicationContext
 * @return
 * @throws IOException
 */
//    @Bean
//    public static PropertyPlaceholderConfigurer getProperties(ApplicationContext applicationContext) throws IOException {
//
//        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
//        configurer.setLocations(applicationContext.getResources("classpath:***/*//*.properties"));
//        System.out.println("---------------------load properties file num；" + applicationContext.getResources("classpath:*.properties").length);
//
//        return configurer;
//    }
}
