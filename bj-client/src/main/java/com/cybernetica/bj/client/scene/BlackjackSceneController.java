package com.cybernetica.bj.client.scene;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.context.EventProducer;
import com.cybernetica.bj.client.events.UserDataEvent;
import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.game.GameSession;
import com.cybernetica.bj.client.interfaces.IDataListener;
import com.cybernetica.bj.client.services.GameService;
import com.cybernetica.bj.common.CardSetUtils;
import com.cybernetica.bj.common.dto.CardDTO;
import com.cybernetica.bj.common.dto.user.GameDTO;
import com.cybernetica.bj.common.dto.user.UserDTO;
import com.cybernetica.bj.common.enums.GameStatus;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class BlackjackSceneController extends BaseSceneController<BlackjackSceneController> implements IDataListener {
	private static final Logger logger = LoggerFactory.getLogger(WelcomeSceneController.class);

	private BigDecimal betStep;

	private Label textLabel;
	private Label lblWinText;
	private Button btnDone;
	private Button btnClose;
	private Button btnTake;
	private Button btnCancel;

	@Override
	protected void postBuild() {

		textLabel = (Label) getElementById("msgText");
		lblWinText = (Label) getElementById("lblWinText");

		btnDone = (Button) getElementById("btnDone");
		btnClose = (Button) getElementById("btnClose");
		btnTake = (Button) getElementById("btnTake");
		btnCancel = (Button) getElementById("btnCancel");
		
		EventHandler<ActionEvent> quitAction=new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				logger.trace("form.game-quit.click");
				Long gameId = GameSession.get().getUser().getGame().getId();
				try {
					GameService.get().quitGame(gameId);
				} catch (ClientException e1) {
					logger.debug(e1.getMessage());
					setElementText(textLabel, e1.getMessage());
				}
			}
		};

		btnCancel.setOnAction(quitAction);
		btnClose.setOnAction(quitAction);

		btnTake.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				logger.trace("form.take.click");
				try {
					GameService.get().takeCard(GameSession.get().getUser().getGame().getId());
				} catch (ClientException e1) {
					logger.debug(e1.getMessage());
					setElementText(textLabel, e1.getMessage());
				}
			}
		});

		// btnPlay.setOnAction(new EventHandler<ActionEvent>() {
		//
		// @Override
		// public void handle(ActionEvent e) {
		// logger.trace("form.bet-play.click");
		// try {
		// GameService.get().beginGame(GameSession.get().getUser().getGame().getId());
		// } catch (ClientException e1) {
		// logger.debug(e1.getMessage());
		// setElementText(textLabel,e1.getMessage());
		// }
		// }
		// });
		//
		EventProducer.addUserDataListener(this);
	}

	public void onUserData(UserDataEvent event) {
		UserDTO userDTO = GameSession.get().getUser();
		if (userDTO == null)
			return;
		GameDTO game = userDTO.getGame();
		if (game == null)
			return;

		BigDecimal balance = userDTO.getBalance();
		if (balance == null)
			balance = BigDecimal.ZERO;
		BigDecimal currentBet = game.getCurrentBet();
		if (currentBet == null)
			currentBet = BigDecimal.ZERO;

		setElementTextById("username", userDTO.getUsername());
		setElementTextById("balance", "" + balance + "$");
		setElementTextById("lblCurrentBet", "" + currentBet + "$");
		//setElementText(lblWinText,"");

		if (game.getStatus() == GameStatus.BETTING)
			return;
		
		if(game.getStatus()==GameStatus.GAME_OVER) {
			btnTake.setVisible(false);
			btnCancel.setVisible(false);
			btnDone.setVisible(false);
			btnClose.setVisible(true);
			
			switch(game.getWinType()) {
			case 2:
				setElementText(lblWinText,"Draw");
				break;
			case 1:
				setElementText(lblWinText,"You lose");
				break;
			case 0:
				setElementText(lblWinText,"! WINNER !");
				break;							
			}
		} else {
			btnClose.setVisible(false);
			
			btnTake.setVisible(true);
			btnCancel.setVisible(true);
			btnDone.setVisible(true);
		}

		// if(currentBet.add(betStep).compareTo(balance)>0)
		// btnBet.setVisible(false);
		// else
		// btnBet.setVisible(true);
		//
		// if(currentBet.compareTo(BigDecimal.ZERO)>0)
		// btnPlay.setVisible(true);
		// else
		// btnPlay.setVisible(false);

		drawCards(game.getUserCards(), game.getDealerCards());
	}

	@Override
	protected void postActivate() {
		// simulate event
		onUserData(new UserDataEvent(GameSession.get().getUser()));

	}

	private void drawCards(Long userCards, Long dealerCards) {
		// clear prev ones
		for (int i = 0; i < 15; i++) {
			VBox vbox = (VBox) getElementById("vbUserCard" + i);
			vbox.getChildren().clear();
			vbox = (VBox) getElementById("vbDealerCard" + i);
			vbox.getChildren().clear();
		}

		Image image = new Image("playing-cards.gif");
		drawCardSet("vbUserCard", userCards, image);
		drawCardSet("vbDealerCard", dealerCards, image);

	}

	private void drawCardSet(String personPrefix, Long cards, Image imgSprite) {
		if (cards == null)
			System.out.println("");
		List<CardDTO> list = CardSetUtils.toList(cards);
		int cardPos = 0;
		for (CardDTO card : list) {
			drawCard(personPrefix, card.getSuit().getValue(), card.getRank().getValue(), cardPos, imgSprite);
			cardPos++;
		}
	}

	private void drawCard(String personPrefix, int row, int col, int cardPos, Image imgSprite) {
		int h = 117;
		int w = 81;

		int x = col * w;
		int y = row * h;

		ImageView iv = new ImageView();
		iv.setImage(imgSprite);
		iv.setViewport(new Rectangle2D(x, y, w, h));
		VBox vbox = (VBox) getElementById(personPrefix + cardPos);
		vbox.getChildren().add(iv);
	}

}
