package com.cybernetica.bj.server.controllers;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import com.cybernetica.bj.common.dto.login.LoginResponseDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SessionControllerTest extends BaseControllerTest{

	

	@Test
	public void testLogin() throws Exception {

		ResultActions result = post("/session/login","{\"username\":\"me9\",\"password\":\"pass\"}",null);
		result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}

	@Test
	public void testLogout() throws Exception {
		ResponseEntity<LoginResponseDTO> ret = login("test","test");
		String sessionId=ret.getHeaders().getFirst("X-Auth-Token");
		ResultActions result = get("/game/start",sessionId);
		result.andExpect(status().isOk());
		
		result = get("/session/logout", sessionId);
		result.andExpect(status().isOk());
		
		result = get("/game/start", sessionId);
		result.andExpect(status().isUnauthorized());
		
	}
	
	@Test
	public void testUnauthorizedAccess() throws Exception {
		ResultActions result = get("/game/start",null);
		result.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void testBadCredentials() throws Exception {
		ResponseEntity<LoginResponseDTO> res = login("test","test");
		assertEquals(res.getStatusCodeValue(),200);
		//res = login("test","test1");
		ResultActions result = post("/session/login","{\"username\":\"test\",\"password\":\"test1\"}",null);

		result.andExpect(status().isUnauthorized());
	}
}
