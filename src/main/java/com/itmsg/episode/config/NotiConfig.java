package com.itmsg.episode.config;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


public class NotiConfig{
}

/*
@Configuration
@EnableTransactionManagement
//@MapperScan(basePackages = {"com.itmsg.episode.app"})
@MapperScan(annotationClass = Mapper.class, basePackages = "com.itmsg.test", sqlSessionFactoryRef = "notiSqlSessionFactory")
public class NotiConfig{

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.noti")
    public DataSource notiDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    
    @Bean(name="notiSqlSessionFactory")
    public SqlSessionFactory notiSqlSessionFactory(@Qualifier("notiDataSource") DataSource notiDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setJdbcTypeForNull(JdbcType.VARCHAR);
        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setDataSource(notiDataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.itmsg.test");
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/sqlmap/mysql/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
    
    
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("notiSqlSessionFactory") SqlSessionFactory notiSqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(notiSqlSessionFactory);
    }
    
    
    @Bean
    public DataSourceTransactionManager notiTransactionManager(@Qualifier("notiDataSource") DataSource notiDataSource) {
        DataSourceTransactionManager manager = new DataSourceTransactionManager(notiDataSource);
        return manager;
    }

}
*/