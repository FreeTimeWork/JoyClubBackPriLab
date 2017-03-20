package config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
}
