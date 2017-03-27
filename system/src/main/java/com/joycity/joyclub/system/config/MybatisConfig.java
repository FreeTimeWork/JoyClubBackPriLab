package com.joycity.joyclub.system.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * MybatisConfig
 *
 * @author CallMeXYZ
 * @date 2016/6/24
 */
@Configuration
@MapperScan("com.joycity.joyclub.**.mapper")
@PropertySource("classpath:jdbc.properties")
public class MybatisConfig {
  /*  @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        propertyPlaceholderConfigurer.setLocation(new ClassPathResource("jdbc.properties"));
        return propertyPlaceholderConfigurer;
    }
*/
    @Bean
    public BasicDataSource dataSource(@Value("${jdbc.className}") String className,
                                      @Value("${jdbc.url}")
                                              String url, @Value("${jdbc.username}")
                                              String username, @Value("${jdbc.password}")
                                              String password) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setInitialSize(5);
        ds.setMinIdle(5);
        ds.setMaxIdle(20);
        ds.setMaxActive(20);
        return ds;
    }
    @Bean
   public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource ){
        return new DataSourceTransactionManager(dataSource);
    }
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws IOException {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        sessionFactoryBean.setConfiguration(configuration);
        // TODO: 2017/2/21 classpath for multi modules
        sessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath*:mapper/**/*.xml"));
        return sessionFactoryBean;
    }

 /*   @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.joycity.joyclub");
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return configurer;
    }*/

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}
