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
		ResponseEntity<LoginResponseDTO> ret = login("test","test");
		String sessionId=ret.getHeaders().getFirst("X-Auth-Token");
		
		ResultActions result = get("/game/start",sessionId);

		result.andExpect(status().isOk());
		GameResponseDTO gameResponseDTO = getResult(result, GameResponseDTO.class);
		assertNotNull(gameResponseDTO);
		assertNotNull(gameResponseDTO.getObject());
		assertNotNull(gameResponseDTO.getObject().getId());
		
	}	
	
	@Test
	public void testGameCancel() throws Exception{
		ResponseEntity<LoginResponseDTO> ret = login("test","test");
		String sessionId=ret.getHeaders().getFirst("X-Auth-Token");
		
		
		//start game
		ResultActions result = get("/game/start",sessionId);
		
		result.andExpect(status().isOk());
		GameResponseDTO gameResponseDTO = getResult(result, GameResponseDTO.class);
		assertNotNull(gameResponseDTO);
		assertNotNull(gameResponseDTO.getObject());
		assertNotNull(gameResponseDTO.getObject().getId());
		
		UserResponseDTO userData = getUserData("test");
		assertNotNull(userData.getUser().getGame());
		
		//game cancel
		Long gameId=gameResponseDTO.getObject().getId();
		result = get("/game/cancel/{id}", sessionId,gameId);

		result.andExpect(status().isOk());
		RestResponseDTO responseDTO = getResult(result, RestResponseDTO.class);
		assertFalse(responseDTO.hasErrors());
		
		userData = getUserData("test");
		assertNull(userData.getUser().getGame());
	}	
	
	
	@Test
	public void testGameBet() throws Exception{
		ResponseEntity<LoginResponseDTO> ret = login("test","test");
		String sessionId=ret.getHeaders().getFirst("X-Auth-Token");
		
		
		//start game
		ResultActions result = get("/game/start",sessionId);
		
		result.andExpect(status().isOk());
		GameResponseDTO gameResponseDTO = getResult(result, GameResponseDTO.class);
		assertNotNull(gameResponseDTO);
		assertNotNull(gameResponseDTO.getObject());
		assertNotNull(gameResponseDTO.getObject().getId());
		
		UserResponseDTO userData = getUserData("test");
		assertNotNull(userData.getUser().getGame());
		assertNotNull(userData.getUser().getGame().getCurrentBet());
		
		BigDecimal prevBet = userData.getUser().getGame().getCurrentBet();
		
		
		//game cancel
		GameBetChangeDTO betDTO = new GameBetChangeDTO();
		Long gameId=gameResponseDTO.getObject().getId();
		betDTO.setGameId(gameId);
		betDTO.setBet(BigDecimal.TEN);
		
		result = post("/game/bet",betDTO, sessionId);

		result.andExpect(status().isOk());
		GameResponseDTO responseDTO = getResult(result, GameResponseDTO.class);
		assertFalse(responseDTO.hasErrors());
		
		userData = getUserData("test");
		assertNotNull(userData.getUser().getGame());
		
		assertEquals(prevBet.add(BigDecimal.TEN), userData.getUser().getGame().getCurrentBet());
	}		
	
	
}
