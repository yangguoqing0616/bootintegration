package com.studyboot.config.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.studyboot.dao"}, sqlSessionTemplateRef = "webSqlSessionTemplate")
public class WebDataSourceConfig {
	@Autowired
	private DataSourceConfig dataSourceConfig;
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;
	@Value("${spring.datasource.username}")
	private String userName;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.url}")
	private String url;

	//数据源注册方式一
	@Bean(name = "webDataSource")
	@Primary
    public DataSource setDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		BeanUtils.copyProperties(dataSourceConfig, dataSource);
		dataSource.setUrl(url);
		dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }


    //数据源注册方式二
   /* @ConfigurationProperties(prefix = "spring.datasource")
    @Bean(name = "webDataSource")
    @Primary
    public DataSource druid(){
        return  new DruidDataSource();
    }*/

    @Bean(name = "webTransactionManager")
    @Primary
    public DataSourceTransactionManager setTransactionManager(@Qualifier("webDataSource") DataSource dataSource) {
      return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "webSqlSessionFactory")
    @Primary
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("webDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis.mapper/*.xml"));
        bean.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));
        return bean.getObject();
    }

    @Bean(name = "webSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("webSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
	  

}
