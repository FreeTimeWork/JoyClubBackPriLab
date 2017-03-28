package com.joycity.joyclub.system.config;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joycity.joyclub.apiback.config.security.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * ServletConfig
 *
 * @author CallMeXYZ
 * @date 2016/6/23
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.joycity.joyclub"},useDefaultFilters = false,includeFilters = {@ComponentScan.Filter(Controller.class)})
public class ServletConfig extends WebMvcConfigurerAdapter {
    @Value("${session.api-back.attr.user}")
    public String name;
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        converter.setObjectMapper(objectMapper);
        converters.add(converter);
        super.configureMessageConverters(converters);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AuthenticationInterceptor apiBackInterceptor  = new AuthenticationInterceptor();
        apiBackInterceptor.setApiBackSessionAttrUser(name);
        registry.addInterceptor(apiBackInterceptor);
        super.addInterceptors(registry);
    }

}
