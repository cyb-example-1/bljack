package com.cybernetica.bj.client.services;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

import org.apache.http.HttpStatus;

import static org.junit.Assert.*;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.services.impl.AuthServiceImpl;
import com.cybernetica.bj.client.test.CloseableHttpResponseWrapper;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;

public class AuthServiceTest {
	private AuthServiceImplHelper authService;
	private RestService restService;
	
	@Before
	public void setUp(){
		authService= new AuthServiceImplHelper();
		restService=mock(RestService.class);
		authService.setRestService(restService);
	}
	
	@Test
	public void testLogin() throws ClientException{
		LoginResponseDTO ret = new LoginResponseDTO();
		when(restService.post(eq("/session/login"), anyObject(), anyObject())).thenReturn(ret);
		LoginResponseDTO respDTO = authService.login("test", "test");
		assertNotNull(respDTO);
	}
	
	@Test(expected=ClientException.class)
	public void testLogin_1() throws ClientException{
		authService.login("", "test");
	}
	
	@Test(expected=ClientException.class)
	public void testLogin_2() throws ClientException{
		authService.login("test", null);
	}
	
	private class AuthServiceImplHelper extends AuthServiceImpl{

		@Override
		public void setRestService(RestService restService) {
			super.setRestService(restService);
		}
		
	} 

}
