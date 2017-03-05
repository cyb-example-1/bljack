package com.cybernetica.bj.server.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class SecurityUtils {
	
	public static User getLoggerUser(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return (User) principal;
	}
	
	public static String getLoggedUserName(){
		if(getLoggerUser()==null)
			return null;
		return getLoggerUser().getUsername();
	}

}
