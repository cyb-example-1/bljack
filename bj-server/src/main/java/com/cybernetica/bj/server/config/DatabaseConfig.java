package com.cybernetica.bj.server.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

	
	@Bean
	public BasicDataSource dataSource() {
	    return (BasicDataSource) DataSourceBuilder.create().build();
	}
}
