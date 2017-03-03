package com.cybernetica.bj.client.services;

import org.junit.Before;
import org.junit.Test;

import com.cybernetica.bj.client.exceptions.ClientException;

public class AuthServiceTest {
	private AuthService authService;
	
	@Before
	public void setUp(){
		authService=AuthService.get();
	}
	
	@Test
	public void testLogin() throws ClientException{
		authService.login("test", "test");
	}
	
	@Test(expected=ClientException.class)
	public void testLogin_1() throws ClientException{
		authService.login("", "test");
	}
	
	@Test(expected=ClientException.class)
	public void testLogin_2() throws ClientException{
		authService.login("test", null);
	}

}
