package com.cybernetica.bj.client.services;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.services.impl.RestServiceImpl;
import com.cybernetica.bj.client.test.CloseableHttpResponseWrapper;
import com.cybernetica.bj.common.dto.login.LoginRequestDTO;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;

public class RestServiceTest {
	private RestServiceImplHelper restService;
	private CloseableHttpClient httpclient;
	
	@Before
	public void setUp(){
		httpclient= mock(CloseableHttpClient.class);
		restService= new RestServiceImplHelper(httpclient);
		
	}
	
	@Test
	public void testLogin() throws ClientException, ClientProtocolException, IOException{
		
		
		LoginResponseDTO ret = new LoginResponseDTO();
		when(httpclient.execute(anyObject())).thenReturn(new CloseableHttpResponseWrapper(HttpStatus.SC_OK,ret));
		
		LoginRequestDTO dto = new LoginRequestDTO();
		dto.setPassword("test");
		dto.setUsername("test");
		LoginResponseDTO resp= restService.post("/session/login", dto, LoginResponseDTO.class);
		assertNotNull(resp);
	}
	

	private class RestServiceImplHelper extends RestServiceImpl{

		private CloseableHttpClient client;

		public RestServiceImplHelper(CloseableHttpClient client) {
			this.client = client;
		}

		@Override
		protected CloseableHttpClient getClient() {
			return this.client;
		}
	}
	
	
}
