package com.cybernetica.bj.server.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTest {
	
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setUp() {
	     mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}	

	
	@Test
	public void testLogin() throws Exception{
		ResultActions result = mockMvc.perform(post("/session/login").content("{\"username\":\"me\",\"password\":\"pass\"}").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8));
		
		
		result
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
		
	}

}
