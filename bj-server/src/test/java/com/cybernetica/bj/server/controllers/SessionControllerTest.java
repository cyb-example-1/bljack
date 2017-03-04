package com.cybernetica.bj.server.controllers;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cybernetica.bj.common.dto.login.LoginRequestDTO;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SessionControllerTest {

	private MockMvc mockMvc;

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
	
	private ResponseEntity<LoginResponseDTO> login(String username,String password) throws Exception {
		LoginRequestDTO dto = new LoginRequestDTO();
		dto.setUsername(username);
		dto.setPassword(password);
        ResponseEntity<LoginResponseDTO>  resp=		restTemplate.postForEntity("/session/login",dto,LoginResponseDTO.class);

		return resp;
	}

	@Test
	public void testLogin() throws Exception {

		ResultActions result = mockMvc
		.perform(post("/session/login").content("{\"username\":\"me9\",\"password\":\"pass\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8));
		result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}

	@Test
	public void testLogout() throws Exception {
		ResponseEntity<LoginResponseDTO> ret = login("test","test");
		String sessionId=ret.getHeaders().getFirst("X-Auth-Token");
		ResultActions result = mockMvc.perform(get("/game/start").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).header("X-Auth-Token", sessionId));
		result.andExpect(status().isOk());
		
		result = mockMvc.perform(get("/session/logout").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).header("X-Auth-Token", sessionId));
		result.andExpect(status().isOk());
		
		result = mockMvc.perform(get("/game/start").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).header("X-Auth-Token", sessionId));
		result.andExpect(status().isUnauthorized());
		
	}
	
	@Test
	public void testUnauthorizedAccess() throws Exception {
		ResultActions result = mockMvc.perform(get("/game/start").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8));
		result.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void testBadCredentials() throws Exception {
		ResponseEntity<LoginResponseDTO> res = login("test","test");
		assertEquals(res.getStatusCodeValue(),200);
		res = login("test","test1");
		LoginResponseDTO body = res.getBody();
		assertEquals(res.getStatusCodeValue(),403);
	}
}
