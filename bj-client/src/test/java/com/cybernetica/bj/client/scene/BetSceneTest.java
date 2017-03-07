package com.cybernetica.bj.client.scene;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Test;

import com.cybernetica.bj.client.events.LoginEvent;
import com.cybernetica.bj.client.events.UserDataEvent;
import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.game.GameCoordinator;
import com.cybernetica.bj.client.game.GameSession;
import com.cybernetica.bj.client.test.BaseSceneTest;
import com.cybernetica.bj.client.utils.Manager;
import com.cybernetica.bj.common.dto.RestResponseDTO;
import com.cybernetica.bj.common.dto.game.GameResponseDTO;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;
import com.cybernetica.bj.common.dto.user.GameDTO;
import com.cybernetica.bj.common.dto.user.UserDTO;
import com.cybernetica.bj.common.dto.user.UserResponseDTO;
import com.cybernetica.bj.common.enums.GameStatus;

import javafx.stage.Stage;

public class BetSceneTest  extends BaseSceneTest{
	

	@Override
	protected void initScene(Stage stage) throws Exception {
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		userResponseDTO.setUser(new UserDTO());
		userResponseDTO.getUser().setBalance(new BigDecimal(100));
		GameDTO game=new GameDTO();
		game.setId(1L);
		game.setStatus(GameStatus.BETTING);
		userResponseDTO.getUser().setGame(game);

		GameCoordinator.get().getEventDispatcher().onEvent(new UserDataEvent(userResponseDTO));
		when(restService.get(eq("/user/get"),anyObject())).thenReturn(userResponseDTO);
		
		GameCoordinator.get().getEventDispatcher().onEvent(new LoginEvent(new  LoginResponseDTO()));
	}
	
    @Test
    public void testCancel() throws ClientException {
    	assertEquals(BetSceneController.class, Manager.current().getClass());
    	RestResponseDTO retDTO = new RestResponseDTO();
    	
    	when(restService.get(eq("/game/cancel/1"),anyObject())).thenReturn(retDTO);
    	
        // given:
    	clickOn("#btnCancel");
    	
    	assertEquals(WelcomeSceneController.class, Manager.current().getClass());
    }
    
    @Test
    public void testAddBet() throws ClientException {
    	assertEquals(BetSceneController.class, Manager.current().getClass());
    	assertNotNull(GameSession.get().getUser().getGame());
    	BigDecimal prevBet = GameSession.get().getUser().getGame().getCurrentBet();
    	if(prevBet==null)
    		prevBet=BigDecimal.ZERO;
    	
    	GameResponseDTO resultDto = new GameResponseDTO();
    	GameDTO game=new GameDTO();
		game.setId(1L);
		game.setStatus(GameStatus.BETTING);
		game.setCurrentBet(prevBet.add(new BigDecimal(10)));
		
    	resultDto.setObject(game);
    	
    	when(restService.post(eq("/game/bet"),anyObject(),anyObject())).thenReturn(resultDto);
    	
        // given:
    	clickOn("#btnBet");
    	
    	assertEquals(BetSceneController.class, Manager.current().getClass());
    	assertEquals(prevBet.add(new BigDecimal(10)), GameSession.get().getUser().getGame().getCurrentBet());
    }    
    
    @Test
    public void testStartPlay() throws ClientException {
    	testAddBet();   	
    	
    	GameDTO game= new GameDTO();
    	game.setId(1L);
    	game.setStatus(GameStatus.BET_DONE);
    	game.setCurrentBet(new BigDecimal(10));
    	
    	UserDTO user= new UserDTO();
    	user.setBalance(new BigDecimal(90));
		user.setGame(game);
		
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		userResponseDTO.setUser(user);	

		when(restService.get(eq("/user/get"),anyObject())).thenReturn(userResponseDTO);
    	when(restService.get(eq("/game/begin/1"),anyObject())).thenReturn(userResponseDTO);
    	
        // given:
    	clickOn("#btnPlay");
    	
    	assertEquals(BlackjackSceneController.class, Manager.current().getClass());
    }    

}
