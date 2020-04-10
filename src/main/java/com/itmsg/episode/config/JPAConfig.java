package com.itmsg.episode.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JPAConfig{

    // @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.jpa")
    public DataSource jpaDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    /*
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(jpaDataSource());
        em.setPackagesToScan(new String[] { "com.itmsg.episode" });
        
        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(jpaVendorAdapter);
        //em.setJpaProperties(additionalProperties());
        return em;
    }
    */
    /*
    @Bean
    public DataSourceTransactionManager jpaTransactionManager() {
 
        DataSourceTransactionManager manager = new DataSourceTransactionManager(jpaDataSource());
        //DataSourceTransactionManager manager = new DataSourceTransactionManager(jpaDataSource());
        return manager;
    }
    */
    
    // @Primary
    @Bean
    // public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
 
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
    
}
