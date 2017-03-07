package com.cybernetica.bj.client.scene;

import java.math.BigDecimal;
import java.util.BitSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.events.UserDataEvent;
import com.cybernetica.bj.client.game.GameSession;
import com.cybernetica.bj.common.CardSetUtils;
import com.cybernetica.bj.common.dto.user.GameDTO;
import com.cybernetica.bj.common.dto.user.UserDTO;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class BlackjackSceneController extends BaseSceneController<BlackjackSceneController> {
	private static final Logger logger = LoggerFactory.getLogger(WelcomeSceneController.class);

	private BigDecimal betStep;

	private Label textLabel;
	private Button btnPlay;
	private Button btnBet;
	private Button btnCancel;

	@Override
	protected void postBuild() {

		// betStep=Properties.getBigDecimal("app.game.bet.amount");
		//
		// textLabel = (Label) getElementById("msgText");
		//
		// btnPlay = (Button) getElementById("btnPlay");
		// btnBet = (Button) getElementById("btnBet");
		// btnCancel = (Button) getElementById("btnCancel");
		//
		//
		//
		// btnCancel.setOnAction(new EventHandler<ActionEvent>() {
		//
		// @Override
		// public void handle(ActionEvent e) {
		// logger.trace("form.bet-cancel.click");
		// Long gameId = GameSession.get().getUser().getGame().getId();
		// try {
		// GameService.get().cancelBet(gameId);
		// } catch (ClientException e1) {
		// logger.debug(e1.getMessage());
		// setElementText(textLabel,e1.getMessage());
		// }
		// }
		// });
		//
		// btnBet.setOnAction(new EventHandler<ActionEvent>() {
		//
		// @Override
		// public void handle(ActionEvent e) {
		// logger.trace("form.bet.click");
		// try {
		// GameService.get().betGame(GameSession.get().getUser().getGame().getId(),new
		// BigDecimal(5));
		// } catch (ClientException e1) {
		// logger.debug(e1.getMessage());
		// setElementText(textLabel,e1.getMessage());
		// }
		// }
		// });
		//
		//
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
		// EventProducer.addUserDataListener(this);
	}

	public void onUserData(UserDataEvent event) {
		UserDTO userDTO = GameSession.get().getUser();
		if (userDTO == null)
			return;
		GameDTO game = userDTO.getGame();
		if (game == null)
			return;

		// BigDecimal balance = userDTO.getBalance();
		// if(balance==null)
		// balance = BigDecimal.ZERO;
		// BigDecimal currentBet = game.getCurrentBet();
		// if(currentBet==null)
		// currentBet = BigDecimal.ZERO;
		//
		// setElementTextById("username",userDTO.getUsername());
		// setElementTextById("balance",""+balance+"$");
		// setElementTextById("lblCurrentBet",""+currentBet+"$");
		//
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
		BitSet bitset = CardSetUtils.toBitset(cards);

		int cardPos=0;
		for (int i = 0; i < 52; i++) {
			if(bitset.get(i)) {
				int row = i/14;
				int col = i-row*14;

				drawCard(personPrefix,row,col,cardPos,imgSprite);
				cardPos++;
			}

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
