package com.cybernetica.bj.server.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.services.SessionService;

@RunWith(SpringRunner.class)
@WebMvcTest(SessionService.class)
public class SessionServiceTest {
	
	@Autowired
	private SessionService service;
	
	@Test
	public void testFindByUsername() throws ServiceException{
		service.findByUsername("test1");
	}

}
