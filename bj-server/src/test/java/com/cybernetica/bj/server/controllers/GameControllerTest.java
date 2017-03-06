package com.cybernetica.bj.server.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import com.cybernetica.bj.common.dto.RestResponseDTO;
import com.cybernetica.bj.common.dto.game.GameBetChangeDTO;
import com.cybernetica.bj.common.dto.game.GameResponseDTO;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;
import com.cybernetica.bj.common.dto.user.UserResponseDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GameControllerTest extends BaseControllerTest {

	@Test
	public void testAccessToPrivilegedResource() throws Exception{
		ResultActions result = get("/game/start",null);
		result.andExpect(status().isUnauthorized());
		
	}
	
	
	@Test
	public void testGameStart() throws Exception{
		String sessionId=login();
		
		startGame(sessionId);
		
	}	
	
	@Test
	public void testGameCancel() throws Exception{
		String sessionId=login();
		
		
		//start game
		startGame(sessionId);
		
		ResultActions result=cancelGame(sessionId);

		result.andExpect(status().isOk());
		RestResponseDTO responseDTO = getResult(result, RestResponseDTO.class);
		assertFalse(responseDTO.hasErrors());
		
		UserResponseDTO userData = getUserData("test");
		assertNull(userData.getUser().getGame());
	}	
	
	
	@Test
	public void testGameBet() throws Exception{
		String sessionId=login();
		
		cancelGame(sessionId);
		startGame(sessionId);
		
		UserResponseDTO userData = getUserData("test");
		BigDecimal prevBet = userData.getUser().getGame().getCurrentBet();
		
		betGame(sessionId);

		userData = getUserData("test");
		
		assertEquals(prevBet.add(BigDecimal.TEN), userData.getUser().getGame().getCurrentBet());
	}	
	
	@Test
	public void testGameBegin() throws Exception{
		String sessionId=login();
		
		cancelGame(sessionId);
		startGame(sessionId);
		betGame(sessionId);
		
		UserResponseDTO userData = getUserData("test");
		
		beginGame(sessionId);
		
		UserResponseDTO userData2 = getUserData("test");
		
		assertEquals(userData.getUser().getBalance().subtract(userData.getUser().getGame().getCurrentBet()), userData2.getUser().getBalance());
		
	}
	
	
	private String login() throws Exception{
		ResponseEntity<LoginResponseDTO> ret = login("test","test");
		String sessionId=ret.getHeaders().getFirst("X-Auth-Token");
		return sessionId;
	}
	
	private ResultActions  cancelGame(String sessionId) throws Exception{
		UserResponseDTO userData = getUserData("test");
		
		if(userData.getUser().getGame()==null)
			return null;
		
		//game cancel
		Long gameId=userData.getUser().getGame().getId();
		return get("/game/cancel/{id}", sessionId,gameId);
	}
	
	private ResultActions startGame(String sessionId) throws Exception{
		ResultActions result = get("/game/start",sessionId);
		
		result.andExpect(status().isOk());
		GameResponseDTO gameResponseDTO = getResult(result, GameResponseDTO.class);
		assertNotNull(gameResponseDTO);
		assertNotNull(gameResponseDTO.getObject());
		assertNotNull(gameResponseDTO.getObject().getId());
		
		
		UserResponseDTO userData = getUserData("test");
		assertNotNull(userData.getUser().getGame());
		
		return result;
	}
	
	private ResultActions betGame(String sessionId) throws Exception {
		
		UserResponseDTO userData = getUserData("test");
		Long gameId=userData.getUser().getGame().getId();
		
		//game cancel
		GameBetChangeDTO betDTO = new GameBetChangeDTO();
		betDTO.setGameId(gameId);
		betDTO.setBet(BigDecimal.TEN);
		
		ResultActions result = post("/game/bet",betDTO, sessionId);

		result.andExpect(status().isOk());
		GameResponseDTO responseDTO = getResult(result, GameResponseDTO.class);
		assertFalse(responseDTO.hasErrors());
		
		userData = getUserData("test");
		assertNotNull(userData.getUser().getGame());
		
		return result;
	}
	
	private ResultActions beginGame(String sessionId) throws Exception {
		UserResponseDTO userData = getUserData("test");
		Long gameId=userData.getUser().getGame().getId();
		ResultActions result= get("/game/begin/{id}", sessionId,gameId);
		
		result.andExpect(status().isOk());
		
		userData = getUserData("test");
		
		assertEquals(userData.getUser().getGame().isBetDone(),true);
		return result;
	}
	
}
