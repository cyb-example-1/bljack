package com.cybernetica.bj.server.controllers;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import com.cybernetica.bj.common.JsonUtils;
import com.cybernetica.bj.common.dto.user.UserResponseDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest  extends BaseControllerTest{
	

	@Test
	public void testUserGet() throws Exception {
		login("test","test");
		ResultActions result = mockMvc
		.perform(get("/user/get")
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8));
		result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
		
		UserResponseDTO dto = JsonUtils.fromString(result.andReturn().getResponse().getContentAsString(),UserResponseDTO.class);
		assertNotNull(dto);
		assertNotNull(dto.getId());
	}
}
