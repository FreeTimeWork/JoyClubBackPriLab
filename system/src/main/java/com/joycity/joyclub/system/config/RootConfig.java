package com.joycity.joyclub.system.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

/**
 * RootConfig
 *
 * @author CallMeXYZ
 * @date 2016/6/23
 */
@Configuration
@EnableScheduling
@ComponentScan(basePackages = {"com.joycity.joyclub"},excludeFilters = {@ComponentScan.Filter(Controller.class)})
public class RootConfig {

}
