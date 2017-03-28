package com.joycity.joyclub.system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * Created by CallMeXYZ on 2017/3/28.
 */
@Configuration
public class FileConfig {
    @Value("${image.upload.api-back.max-per-file}")
    private long maxUploadPerFile;

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSizePerFile(maxUploadPerFile);
        return multipartResolver;
    }
}
