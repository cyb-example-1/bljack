package com.cybernetica.bj.client.scene;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.context.EventProducer;
import com.cybernetica.bj.client.events.UserDataEvent;
import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.game.GameSession;
import com.cybernetica.bj.client.interfaces.IDataListener;
import com.cybernetica.bj.client.services.GameService;
import com.cybernetica.bj.client.utils.Properties;
import com.cybernetica.bj.common.dto.user.GameDTO;
import com.cybernetica.bj.common.dto.user.UserDTO;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Bet scene controller
 * @author dmitri
 *
 */
public class BetSceneController extends BaseSceneController<BetSceneController> implements IDataListener {
	private static final Logger logger = LoggerFactory.getLogger(WelcomeSceneController.class);
	
	private BigDecimal betStep;
	
	private Label textLabel;
	private Button btnPlay;
	private Button btnBet;
	private Button btnCancel;
	
	
	@Override
	protected void postBuild() {
		
		betStep=Properties.getBigDecimal("app.game.bet.amount");
		
		textLabel = (Label) getElementById("msgText");
		
		btnPlay = (Button) getElementById("btnPlay");
		btnBet = (Button) getElementById("btnBet");
		btnCancel = (Button) getElementById("btnCancel");

		
		
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
            	logger.trace("form.bet-cancel.click");
            	Long gameId = GameSession.get().getUser().getGame().getId();
            	try {
					GameService.get().cancelBet(gameId);
				} catch (ClientException e1) {
					logger.debug(e1.getMessage());
					setElementText(textLabel,e1.getMessage());
				}
            }
        });
		
		btnBet.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
            	logger.trace("form.bet.click");
            	try {
					GameService.get().betGame(GameSession.get().getUser().getGame().getId(),new BigDecimal(5));
				} catch (ClientException e1) {
					logger.debug(e1.getMessage());
					setElementText(textLabel,e1.getMessage());
				}
            }
        });		
		
		
		btnPlay.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
            	logger.trace("form.bet-play.click");
            	try {
					GameService.get().beginGame(GameSession.get().getUser().getGame().getId());
				} catch (ClientException e1) {
					logger.debug(e1.getMessage());
					setElementText(textLabel,e1.getMessage());
				}
            }
        });
		
		EventProducer.addUserDataListener(this);
	}

	public void onUserData(UserDataEvent event){
		UserDTO userDTO = GameSession.get().getUser();
		if(userDTO==null)
			return;
		GameDTO game = userDTO.getGame();
		if(game==null)
			return;
		
		BigDecimal balance = userDTO.getBalance();
		if(balance==null)
			balance = BigDecimal.ZERO;
		BigDecimal currentBet =  game.getCurrentBet();
		if(currentBet==null)
			currentBet = BigDecimal.ZERO;
		
		setElementTextById("username",userDTO.getUsername());
		setElementTextById("balance",""+balance+"$");
		setElementTextById("lblCurrentBet",""+currentBet+"$");
		
		if(currentBet.add(betStep).compareTo(balance)>0)
			btnBet.setVisible(false);
		else
			btnBet.setVisible(true);
		
		if(currentBet.compareTo(BigDecimal.ZERO)>0)
			btnPlay.setVisible(true);
		else
			btnPlay.setVisible(false);
	}


	@Override
	protected void postActivate() {
		//simulate event
		onUserData(new UserDataEvent(GameSession.get().getUser()));
	}

}
