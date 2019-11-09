package com.studyboot.config.DataSource;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Data
@Slf4j
public class DataSourceConfig {

	private int initialSize;
    private int minIdle;
    private int maxActive = 100;
    private long maxWait;
    private long timeBetweenEvictionRunsMillis;
    private long minEvictableIdleTimeMillis;
    private String validationQuery;
    private int validationQueryTimeout;
    private boolean removeAbandoned;
    private int removeAbandonedTimeout;
    private boolean logAbandoned;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean poolPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
    private String filters;
    private boolean asyncInit;
    /**
     * 配置druid管理页面的访问控制
     * 访问网址: http://127.0.0.1:8080/druid
     * @return
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        log.info("init Druid Servlet Configuration 进入druid的配置");
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        //配置一个拦截器
        servletRegistrationBean.setServlet(new StatViewServlet());
        //指定拦截器只拦截druid管理页面的请求
        servletRegistrationBean.addUrlMappings("/druid/*");
        HashMap<String, String> initParam = new HashMap<>();
        //登录druid管理页面的用户名
        initParam.put("loginUsername", "admin");
        //登录druid管理页面的密码
        initParam.put("loginPassword", "admin");
        //是否允许重置druid的统计信息
        initParam.put("resetEnable", "true");
        //ip白名单，如果没有设置或为空，则表示允许所有访问
        initParam.put("allow", "");
        servletRegistrationBean.setInitParameters(initParam);
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
    
}
