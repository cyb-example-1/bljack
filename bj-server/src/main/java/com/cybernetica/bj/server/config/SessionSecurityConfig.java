package com.cybernetica.bj.server.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

import com.cybernetica.bj.server.controllers.SessionController;
import com.cybernetica.bj.server.security.RestAuthenticationEntryPoint;
import com.cybernetica.bj.server.security.RestAuthenticationFailureHandler;
import com.cybernetica.bj.server.security.RestAuthenticationProcessingFilter;
import com.cybernetica.bj.server.security.RestAuthenticationSuccessHandler;
import com.cybernetica.bj.server.security.RestLogoutSuccessHandler;

@EnableJdbcHttpSession
@Configuration
@EnableWebSecurity
public class SessionSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SessionController sessionController;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select username,password, enabled from users where username=?")
				//.authoritiesByUsernameQuery("select username, role from user_roles where username=?")
				;

	}

	@Bean
	AuthenticationEntryPoint authenticationEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}
	

    @Bean
    AuthenticationFailureHandler authenticationFailureHandler() {
        return new RestAuthenticationFailureHandler();
    }

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler() {
    	RestAuthenticationSuccessHandler handler= new RestAuthenticationSuccessHandler();

    	handler.setSessionController(sessionController);
    	return handler;
    }
    
    @Bean
    LogoutSuccessHandler logoutSuccessHandler() {
    	RestLogoutSuccessHandler handler= new RestLogoutSuccessHandler();
    	handler.setSessionController(sessionController);
    	return handler;
    }
    
    @Bean
    RestAuthenticationProcessingFilter authenticationFilter(){
    	RestAuthenticationProcessingFilter filter =  new RestAuthenticationProcessingFilter();
    	//filter.setAuthenticationFailureHandler(authenticationFailureHandler());
    	return filter;
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// Use the custom authentication entry point.
				.exceptionHandling()
					.authenticationEntryPoint(authenticationEntryPoint())
					.and()
				.addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter.class)
				// Configure form login.
//				.formLogin()
//					.loginProcessingUrl("/session/login")
//					.failureHandler(authenticationFailureHandler())
//					.successHandler(authenticationSuccessHandler())
//					.permitAll()
//					.and()
				// Configure logout function.
				.logout()
					.deleteCookies("JSESSIONID")
					.logoutUrl("/session/logout")
					.logoutSuccessHandler(logoutSuccessHandler())
					.and()
				.authorizeRequests()
				.antMatchers("/game/**").authenticated()
				.antMatchers("/user/**").authenticated()
				.antMatchers("/session/**").permitAll()
				.anyRequest().permitAll()
				.and().csrf().disable();
	}

}