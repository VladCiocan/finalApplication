package com.hh.HHBank.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("com.hh.HHBank.controllers.DAO")
public class DatabaseConfiguration {

	@Autowired
	private Environment env;
	
	@Autowired
	private DataSourceConfiguration dsConfig;

	@Bean
	public JpaVendorAdapter getJpaVendorAdapter() {
		JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		return adapter;
	}

	@Bean(name="entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
		lcemfb.setJpaVendorAdapter(getJpaVendorAdapter());
		lcemfb.setDataSource(dsConfig.getDataSource());
		lcemfb.setPersistenceUnitName("myJpaPersistenceUnit");
		lcemfb.setPackagesToScan("com.hh.HHBank");
		lcemfb.setJpaProperties(jpaProperties());
		return lcemfb;
	}
	@Bean(name="transactionManager")
	public PlatformTransactionManager txManager() {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(
				getEntityManagerFactory().getObject());
		return jpaTransactionManager;
	}
	
	private Properties jpaProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.default_schema", env.getProperty("hibernate.default_schema"));
		return properties;
	}

	@Configuration
	class DataSourceConfiguration {
		@Bean
		@Primary
		public DataSource getDataSource() {
			BasicDataSource dataSource = new BasicDataSource();
			dataSource.setDriverClassName(env.getProperty("database.driverClassName"));
			dataSource.setUrl(env.getProperty("database.url"));
			dataSource.setUsername(env.getProperty("database.username"));
			dataSource.setPassword(env.getProperty("database.password"));
			return dataSource;
		}
	}
}
