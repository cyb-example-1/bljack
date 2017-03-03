package com.cybernetica.bj.server;


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
public class TestApplication extends Application{
	

}
