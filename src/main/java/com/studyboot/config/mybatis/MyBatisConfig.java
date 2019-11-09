package com.studyboot.config.mybatis;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;


/**
 * mybatis相关配置
 */
@org.springframework.context.annotation.Configuration
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new ConfigurationCustomizer() {

            @Override
            public void customize(Configuration configuration) {
                //开启驼峰命名方式
                //也可以在mybatis/mybatis-config.xml 配置文件中进行相关的配置
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}
