package com.cybernetica.bj.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cybernetica.bj.server.config.Application;
import com.cybernetica.bj.server.config.DatabaseConfig;
import com.cybernetica.bj.server.config.SessionSecurityConfig;

/**
 * Spring boot entry-point
 * @author dmitri
 *
 */
@SpringBootApplication
@ComponentScan({"com.cybernetica.bj.server.controllers","com.cybernetica.bj.server.security"})
@EnableAutoConfiguration(exclude=HibernateJpaAutoConfiguration.class)
@EnableWebMvc
@EnableWebSecurity
@Import(value={DatabaseConfig.class,SessionSecurityConfig.class,WebSecurityConfiguration.class})
public class TestApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        logger.info("Application initialized");
    }

}
