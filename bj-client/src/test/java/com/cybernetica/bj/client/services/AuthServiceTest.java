package com.cybernetica.bj.client.services;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.services.impl.AuthServiceImpl;
import com.cybernetica.bj.client.test.BaseServiceTest;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;
import com.cybernetica.bj.common.dto.user.UserDTO;
import com.cybernetica.bj.common.dto.user.UserResponseDTO;

public class AuthServiceTest extends BaseServiceTest{
	private AuthServiceImplHelper authService;
	
	@Before
	public void setUp(){
		authService= new AuthServiceImplHelper();
		authService.setRestService(restService);
	}
	
	@Test
	public void testLogin() throws ClientException{
		LoginResponseDTO ret = new LoginResponseDTO();
		when(restService.post(eq("/session/login"), anyObject(), anyObject())).thenReturn(ret);
		
		
		UserResponseDTO userDTO = new UserResponseDTO();
		userDTO.setUser(new UserDTO());
		userDTO.getUser().setBalance(BigDecimal.ZERO);
		//GameCoordinator.get().getEventDispatcher().onEvent(new UserDataEvent(userDTO));
		when(restService.get(eq("/user/get"),anyObject())).thenReturn(userDTO);		
		
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
