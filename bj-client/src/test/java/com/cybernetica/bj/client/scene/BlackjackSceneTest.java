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
import com.cybernetica.bj.common.CardSetUtils;
import com.cybernetica.bj.common.dto.RestResponseDTO;
import com.cybernetica.bj.common.dto.game.GameResponseDTO;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;
import com.cybernetica.bj.common.dto.user.GameDTO;
import com.cybernetica.bj.common.dto.user.UserDTO;
import com.cybernetica.bj.common.dto.user.UserResponseDTO;
import com.cybernetica.bj.common.enums.GameStatus;

import javafx.stage.Stage;

public class BlackjackSceneTest  extends BaseSceneTest{
	

	@Override
	protected void initScene(Stage stage) throws Exception {
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		userResponseDTO.setUser(new UserDTO());
		userResponseDTO.getUser().setBalance(new BigDecimal(90));
		GameDTO game=new GameDTO();
		game.setId(1L);
		game.setStatus(GameStatus.BET_DONE);
		game.setCurrentBet(BigDecimal.TEN);
		game.setUserCards(0x11L);
		game.setDealerCards(0x2L);
		
		userResponseDTO.getUser().setGame(game);
		
		when(restService.get(eq("/user/get"),anyObject())).thenReturn(userResponseDTO);
		
		GameCoordinator.get().getEventDispatcher().onEvent(new UserDataEvent(userResponseDTO));
	}
	
    @Test
    public void testCancel() throws ClientException {
    	assertEquals(BlackjackSceneController.class, Manager.current().getClass());
    	
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		userResponseDTO.setUser(new UserDTO());
		userResponseDTO.getUser().setBalance(GameSession.get().getUser().getBalance());
    	
    	when(restService.get(eq("/game/quit/1"),anyObject())).thenReturn(userResponseDTO);
    	
    	BigDecimal balance = GameSession.get().getUser().getBalance();
        // given:
    	clickOn("#btnCancel");
    	
    	assertEquals(WelcomeSceneController.class, Manager.current().getClass());
    	
    	assertEquals(balance, GameSession.get().getUser().getBalance());
    }
    
    //ok. no lose
    @Test
    public void testTakeCard() throws ClientException {
    	assertEquals(BlackjackSceneController.class, Manager.current().getClass());
    	assertNotNull(GameSession.get().getUser().getGame());
    	GameDTO game1 = GameSession.get().getUser().getGame();
    	

    	UserResponseDTO userResponseDTO = new UserResponseDTO(new UserDTO());
    	GameDTO game=new GameDTO();
		game.setId(1L);
		game.setStatus(GameStatus.BET_DONE);
		game.setCurrentBet(new BigDecimal(10));
		game.setUserCards(0x7L);
		game.setDealerCards(0x1L);
		
		userResponseDTO.getUser().setGame(game);
    	
    	when(restService.get(eq("/game/take/1"),anyObject())).thenReturn(userResponseDTO);
    	
        // given:
    	clickOn("#btnTake");
    	
    	assertEquals(BlackjackSceneController.class, Manager.current().getClass());
    	
    	GameDTO game2 = GameSession.get().getUser().getGame();
    	assertEquals(CardSetUtils.toBitset(game1.getUserCards()).cardinality()+1,CardSetUtils.toBitset(game2.getUserCards()).cardinality());
    }    
//    
//    @Test
//    public void testStartPlay() throws ClientException {
//    	testAddBet();   	
//    	
//    	GameDTO game= new GameDTO();
//    	game.setId(1L);
//    	game.setStatus(GameStatus.BET_DONE);
//    	game.setCurrentBet(new BigDecimal(10));
//    	
//    	UserDTO user= new UserDTO();
//    	user.setBalance(new BigDecimal(90));
//		user.setGame(game);
//		
//		UserResponseDTO userResponseDTO = new UserResponseDTO();
//		userResponseDTO.setUser(user);	
//
//		when(restService.get(eq("/user/get"),anyObject())).thenReturn(userResponseDTO);
//    	when(restService.get(eq("/game/begin/1"),anyObject())).thenReturn(userResponseDTO);
//    	
//        // given:
//    	clickOn("#btnPlay");
//    	
//    	assertEquals(BlackjackSceneController.class, Manager.current().getClass());
//    }    

}
