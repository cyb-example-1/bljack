package com.cybernetica.bj.client.scene;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.context.EventProducer;
import com.cybernetica.bj.client.events.UserDataEvent;
import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.game.GameSession;
import com.cybernetica.bj.client.interfaces.IDataListener;
import com.cybernetica.bj.client.services.AuthService;
import com.cybernetica.bj.client.services.UserService;
import com.cybernetica.bj.client.utils.Properties;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *  Main authorized window
 * @author dmitri
 *
 */
public class WelcomeSceneController extends BaseSceneController<WelcomeSceneController> implements IDataListener {
	private static final Logger logger = LoggerFactory.getLogger(WelcomeSceneController.class);
	
	private Label textLabel;
	private Button btnPlay;
	private BigDecimal playMinimum;
	
	@Override
	protected void postBuild() {
		
		playMinimum=Properties.getBigDecimal("app.game.play-minimum");
		
		textLabel = (Label) getElementById("msgText");
		
		btnPlay = (Button) getElementById("btnPlay");
		
		Button btnLogout = (Button) getElementById("btnLogout");
		Button btnBalance = (Button) getElementById("btnBalance");
		
		btnLogout.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
            	logger.trace("form.logout.click");
            	try {
					AuthService.get().logout();
				} catch (ClientException e1) {
					logger.debug(e1.getMessage());
					setElementText(textLabel,e1.getMessage());
				}
            }
        });
		
		btnBalance.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
            	logger.trace("form.balance.click");
            	try {
					UserService.get().updateBalance(new BigDecimal(100));
				} catch (ClientException e1) {
					logger.debug(e1.getMessage());
					setElementText(textLabel,e1.getMessage());
				}
            }
        });		
		
		
	}

	public void onUserData(UserDataEvent event){
		BigDecimal balance = GameSession.get().getUser().getBalance();
		setElementTextById("username",GameSession.get().getUser().getUsername());
		setElementTextById("balance",""+balance+"$");
		if(balance.compareTo(playMinimum)>=0)
			btnPlay.setVisible(true);
		else
			btnPlay.setVisible(false);
	}


	@Override
	protected void postActivate() {
		EventProducer.addUserDataListener(this);
		//simulate event
		onUserData(new UserDataEvent(GameSession.get().getUser()));
	}

}
