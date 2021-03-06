package com.cybernetica.bj.server.controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cybernetica.bj.common.JsonUtils;
import com.cybernetica.bj.common.dto.BaseDTO;
import com.cybernetica.bj.common.dto.login.LoginRequestDTO;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;
import com.cybernetica.bj.common.dto.user.BalanceChangeDTO;
import com.cybernetica.bj.common.dto.user.UserResponseDTO;

public abstract class BaseControllerTest {
	protected MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private FilterChainProxy filterChainProxy;
	@Autowired
    protected TestRestTemplate restTemplate ;//=new TestRestTemplate();

	@Before
	public void setUp() {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(webApplicationContext);
		builder.addFilter(filterChainProxy);
		mockMvc = builder.build();
	}
	
	protected ResponseEntity<LoginResponseDTO> login(String username,String password) throws Exception {
		LoginRequestDTO dto = new LoginRequestDTO();
		dto.setUsername(username);
		dto.setPassword(password);
        ResponseEntity<LoginResponseDTO>  resp=		restTemplate.postForEntity("/session/login",dto,LoginResponseDTO.class);

		return resp;
	}
	
	protected <T> T getResult(ResultActions result,Class<T> beanCls) throws Exception{
		String resp = result.andReturn().getResponse().getContentAsString();
		return JsonUtils.fromString(resp, beanCls);
	}
	
	
	
	protected ResultActions get(String uri,String sessionId, Object... uriVar) throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(uri,uriVar)
		.contentType(MediaType.APPLICATION_JSON_UTF8)
		.accept(MediaType.APPLICATION_JSON_UTF8);
		if(sessionId!=null)
			builder.header("X-Auth-Token", sessionId);
		
		return mockMvc.perform(builder);		
	}
	

	protected ResultActions post(String uri, String content, String sessionId, Object... uriVar) throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(uri,uriVar)
		.contentType(MediaType.APPLICATION_JSON_UTF8)
		.accept(MediaType.APPLICATION_JSON_UTF8);
		if(content!=null)
			builder.content(content);
		if(sessionId!=null)
			builder.header("X-Auth-Token", sessionId);
		
		return mockMvc.perform(builder);	
	}	
	
	protected ResultActions delete(String uri, String sessionId, Object... uriVar) throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete(uri,uriVar)
		.contentType(MediaType.APPLICATION_JSON_UTF8)
		.accept(MediaType.APPLICATION_JSON_UTF8);
		if(sessionId!=null)
			builder.header("X-Auth-Token", sessionId);
		
		return mockMvc.perform(builder);	
	}	
	
	protected ResultActions put(String uri, String sessionId, Object... uriVar) throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(uri,uriVar)
		.contentType(MediaType.APPLICATION_JSON_UTF8)
		.accept(MediaType.APPLICATION_JSON_UTF8);
		if(sessionId!=null)
			builder.header("X-Auth-Token", sessionId);
		
		return mockMvc.perform(builder);	
	}	
	
	protected <T extends BaseDTO> ResultActions post(String uri, T request, String sessionId, Object... uriVar) throws Exception {
		if(request==null)
			return post(uri,(String)null,sessionId,uriVar);
		return post(uri,JsonUtils.toString(request),sessionId);
	}	
	
	protected UserResponseDTO getUserData(String username) throws Exception {
		UserResponseDTO dto = webApplicationContext.getBean(UserController.class).get(username);
		return dto;
	}
	
	protected ResultActions updateBalance(String sessionId) throws Exception {
		
		UserResponseDTO userData = getUserData("test");
		
		BalanceChangeDTO request=new BalanceChangeDTO();
		request.setBalanceChange( new BigDecimal(100));
		ResultActions result =post("/user/balance",request, sessionId);		

		result.andExpect(status().isOk());
		UserResponseDTO responseDTO = getResult(result, UserResponseDTO.class);
		assertFalse(responseDTO.hasErrors());
		
		userData = getUserData("test");
		assertTrue(userData.getUser().getBalance().compareTo(BigDecimal.ZERO)>0);
		
		return result;
	}
}
