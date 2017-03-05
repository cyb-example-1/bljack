package com.cybernetica.bj.server.controllers;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import com.cybernetica.bj.common.JsonUtils;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;
import com.cybernetica.bj.common.dto.user.BalanceChangeDTO;
import com.cybernetica.bj.common.dto.user.UserResponseDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest  extends BaseControllerTest{
	

	@Test
	public void testUserGet() throws Exception {
		ResponseEntity<LoginResponseDTO> ret = login("test","test");
		String sessionId=ret.getHeaders().getFirst("X-Auth-Token");
		ResultActions result = mockMvc
		.perform(get("/user/get")
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).	header("X-Auth-Token", sessionId));
		result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
		
		UserResponseDTO dto = JsonUtils.fromString(result.andReturn().getResponse().getContentAsString(),UserResponseDTO.class);
		assertNotNull(dto);
		assertNotNull(dto.getUser().getId());
	}
	
	@Test
	public void testUserIncreaseBalance() throws Exception {
		ResponseEntity<LoginResponseDTO> ret = login("test","test");
		String sessionId=ret.getHeaders().getFirst("X-Auth-Token");
		BalanceChangeDTO request=new BalanceChangeDTO();
		request.setBalanceChange( new BigDecimal(100));
		ResultActions result = mockMvc
		.perform(post("/user/balance").content(JsonUtils.toString(request))
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).	header("X-Auth-Token", sessionId));
		result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
		
		UserResponseDTO dto = JsonUtils.fromString(result.andReturn().getResponse().getContentAsString(),UserResponseDTO.class);
		assertNotNull(dto);
		assertNotNull(dto.getUser().getId());
	}
}
