package com.thanhle.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@PropertySource(value="classpath:db.properties")
public class AppConfig {
	
	@Autowired
	private Environment env;
	
	@Bean
	public DriverManagerDataSource dataSource() {
		
		System.out.println("url: "+env.getProperty("url"));
		System.out.println("driver: "+env.getProperty("driver"));
		System.out.println("user name: "+env.getProperty("username"));
		System.out.println("password: "+env.getProperty("password"));
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(env.getProperty("url"));
		dataSource.setDriverClassName(env.getProperty("driver"));
		dataSource.setUsername(env.getProperty("username"));
		dataSource.setPassword(env.getProperty("password"));
		
		return dataSource;
	}
	

	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	         LocalContainerEntityManagerFactoryBean entityManager = new  LocalContainerEntityManagerFactoryBean();
	         entityManager.setDataSource(dataSource());
	         entityManager.setPackagesToScan("com.thanhle.domain");
	         entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
	         entityManager.setJpaProperties(jpaProerties());
	         return entityManager;
	}
	
	
	Properties jpaProerties() {
		Properties jpaProerties = new Properties();
		jpaProerties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		jpaProerties.setProperty("hibernate.show_sql", "true");
		jpaProerties.setProperty("hibernate.hbm2ddl.auto", "update");
		return jpaProerties;
	}
	
}
