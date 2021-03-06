package com.cybernetica.bj.client.scene;

import static org.junit.Assert.assertEquals;
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
import com.cybernetica.bj.common.dto.LogoutResponseDTO;
import com.cybernetica.bj.common.dto.game.GameResponseDTO;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;
import com.cybernetica.bj.common.dto.user.GameDTO;
import com.cybernetica.bj.common.dto.user.UserDTO;
import com.cybernetica.bj.common.dto.user.UserResponseDTO;
import com.cybernetica.bj.common.enums.GameStatus;

import javafx.stage.Stage;

public class WelcomeSceneTest  extends BaseSceneTest{

	@Override
	protected void initScene(Stage stage) throws Exception {
		UserResponseDTO userDTO = new UserResponseDTO();
		userDTO.setUser(new UserDTO());
		userDTO.getUser().setBalance(BigDecimal.ZERO);
		GameCoordinator.get().getEventDispatcher().onEvent(new UserDataEvent(userDTO));
		when(restService.get(eq("/user/get"),anyObject())).thenReturn(userDTO);
	}
	
    @Test
    public void testLogout() throws ClientException {
    	assertEquals(WelcomeSceneController.class, Manager.current().getClass());
    	LogoutResponseDTO logoutDto = new LogoutResponseDTO();
    	when(restService.post(eq("/session/logout"),anyObject(),anyObject())).thenReturn(logoutDto);
    	
        // given:
    	clickOn("#btnLogout");
    	
    	assertEquals(LoginSceneController.class, Manager.current().getClass());
    }
    
    @Test
    public void testAddBalance() throws ClientException {
    	assertEquals(WelcomeSceneController.class, Manager.current().getClass());
    	BigDecimal prevBalance = GameSession.get().getUser().getBalance();
    	if(prevBalance==null)
    		prevBalance=BigDecimal.ZERO;
    	
    	UserResponseDTO resultDto = new UserResponseDTO();
    	UserDTO user= new UserDTO();
    	resultDto.setUser(user);
    	user.setBalance(prevBalance.add(new BigDecimal(100)));
    	
    	
    	when(restService.post(eq("/user/balance"),anyObject(),anyObject())).thenReturn(resultDto);
    	
        // given:
    	clickOn("#btnBalance");
    	
    	assertEquals(WelcomeSceneController.class, Manager.current().getClass());
    }    
    
    @Test
    public void testStartPlay() throws ClientException {   	
    	GameDTO game= new GameDTO();
    	game.setId(1L);
    	game.setStatus(GameStatus.BETTING);
    	
    	UserResponseDTO resultDto = new UserResponseDTO();
    	UserDTO user= new UserDTO();
    	resultDto.setUser(user);
    	user.setBalance(new BigDecimal(100));
		user.setGame(game);
		
		GameResponseDTO gameResponseDTO = new GameResponseDTO(game);

		when(restService.get(eq("/user/get"),anyObject())).thenReturn(resultDto);    	
    	when(restService.get(eq("/game/start"),anyObject())).thenReturn(gameResponseDTO);
    	
    	GameSession.get().getUser().setBalance(BigDecimal.TEN);
    	Manager.get(WelcomeSceneController.class).onUserData(new UserDataEvent(GameSession.get().getUser()));   	
    	
        // given:
    	clickOn("#btnPlay");
    	
    	assertEquals(BetSceneController.class, Manager.current().getClass());
    }    

}
