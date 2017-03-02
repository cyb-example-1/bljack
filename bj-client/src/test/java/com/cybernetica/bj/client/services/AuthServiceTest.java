package com.cybernetica.bj.client.services;

import javax.validation.ValidationException;

import org.junit.Before;
import org.junit.Test;

public class AuthServiceTest {
	private AuthService authService;
	
	@Before
	public void setUp(){
		authService=AuthService.get();
	}
	
	@Test
	public void testLogin(){
		authService.login("test", "test");
	}
	
	@Test(expected=ValidationException.class)
	public void testLogin_1(){
		authService.login("", "test");
	}
	
	@Test(expected=ValidationException.class)
	public void testLogin_2(){
		authService.login("test", null);
	}

}
