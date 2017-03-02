package com.cybernetica.bj.server.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Database and transaction manager config
 * 
 * @author dmitri
 *
 */
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${hibernate.show_sql}")
	private String HIBERNATE_SHOW_SQL;

	@Value("${hibernate.hbm2ddl.auto}")
	private String HIBERNATE_HBM2DDL_AUTO;

	@Value("${bjapp.packagesToScan}")
	private String ENTITYMANAGER_PACKAGES_TO_SCAN;

	@Value("${hibernate.dialect}")
	private String HIBERNATE_DIALECT;

	@Bean
	public DataSource dataSource() {
		DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
				.username(username).password(password);
		return factory.build();
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
		hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
		hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
		sessionFactoryBean.setHibernateProperties(hibernateProperties);

		return sessionFactoryBean;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}
}
