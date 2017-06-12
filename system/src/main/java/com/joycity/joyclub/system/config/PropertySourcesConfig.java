package com.joycity.joyclub.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


/**
 * Created by CallMeXYZ on 2017/3/30.
 */
public class PropertySourcesConfig {

    private static final Resource[] DEV_PROPERTIES = new ClassPathResource[]{
            new ClassPathResource("application.yaml"),
    };

    private static final Resource[] PROD_PROPERTIES = new ClassPathResource[]{
            new ClassPathResource("application-prod.properties"),
    };

    @Profile("development")
    public static class DevConfig {
        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
            pspc.setLocations(DEV_PROPERTIES);
            return pspc;
        }
    }


    @Profile("production")
    public static class ProdConfig {
        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
            pspc.setLocations(PROD_PROPERTIES);
            return pspc;
        }
    }

}