package com.cybernetica.bj.server.controllers;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cybernetica.bj.common.dto.login.LoginRequestDTO;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;

public abstract class BaseControllerTest {
	protected MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private FilterChainProxy filterChainProxy;
	@Autowired
    private TestRestTemplate restTemplate ;//=new TestRestTemplate();

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
}
