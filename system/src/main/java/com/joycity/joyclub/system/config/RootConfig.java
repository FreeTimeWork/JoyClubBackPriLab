package com.joycity.joyclub.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * RootConfig
 *
 * @author CallMeXYZ
 * @date 2016/6/23
 */
@Configuration
@EnableScheduling
@ComponentScan(basePackages = {"com.joycity.joyclub"}, excludeFilters = {@ComponentScan.Filter(Controller.class)})
@PropertySource("classpath:application-dev.properties")
//@PropertySource("classpath:application-prod.properties")
public class RootConfig {
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(10000);
        requestFactory.setConnectTimeout(5000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }
}
