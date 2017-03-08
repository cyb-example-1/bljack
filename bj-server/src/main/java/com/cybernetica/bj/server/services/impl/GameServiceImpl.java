package com.cybernetica.bj.server.services.impl;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybernetica.bj.common.CardSetUtils;
import com.cybernetica.bj.common.enums.GameStatus;
import com.cybernetica.bj.server.dao.GameDao;
import com.cybernetica.bj.server.dao.UserDao;
import com.cybernetica.bj.server.exceptions.DaoException;
import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.models.Game;
import com.cybernetica.bj.server.models.User;
import com.cybernetica.bj.server.services.GameService;
import com.cybernetica.bj.server.services.UserService;


/**
 * Game processing service implementation
 * @author dmitri
 *
 */
@Service
@Transactional
public class GameServiceImpl extends BaseServiceImpl implements GameService {
	
	@Autowired
	private GameDao gameDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserService userService;

	/**
	 * {@inheritDoc}
	 */	
	@Override
	public Game createGame(Long userId) throws ServiceException {
		User user=getUser(userId);
		
		Game game =  new Game();
		game.setStatus(1);
		game.setCurrentBet(BigDecimal.ZERO);
		try {
			gameDao.persist(game);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		
		user.setGame(game);
		try {
			userDao.update(user);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return game;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User cancelGame(Long userId, Long gameId) throws ServiceException {
		User user=getUser(userId);
		if(user.getGame()!=null) {
			Game game = getGame(user,gameId);

			user.setGame(null);;
			try {
				userDao.update(user);
				gameDao.delete(game);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}				
		}
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Game betGame(Long userId, Long gameId, BigDecimal betAugment) throws ServiceException {
		User user=getUser(userId);
		Game game = getGame(user,gameId);
		
		BigDecimal newBet = game.getCurrentBet().add(betAugment);
		if(newBet.compareTo(user.getBalance())>0)
			throw new ServiceException("error.game.bet.money-exceeded");
		game.setCurrentBet(newBet);
		try {
			gameDao.update(game);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		
		return game;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User beginGame(Long userId, Long gameId) throws ServiceException {
		User user=getUser(userId);
		Game game = getGame(user,gameId);

		//generate cards
		Long userBitSet = CardSetUtils.generateSet(CardSetUtils.generateCard(0L));
		
		Long dealerHidden=CardSetUtils.generateCard(userBitSet);
		Long dealerSeen=CardSetUtils.generateCard(dealerHidden|userBitSet);
		
		dealerSeen=dealerSeen^dealerHidden;
		dealerHidden=dealerHidden^userBitSet;
		
		user = userService.updateBalance(user.getUsername(), game.getCurrentBet().negate());
		
		game.setStatus(2);
		game.setUserCards(userBitSet);
		game.setDealerCardClosed(dealerHidden);
		game.setDealerCardOpened(dealerSeen);
		
		try {
			userDao.update(user);
			gameDao.update(game);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User quitGame(Long userId, Long gameId) throws ServiceException {
		return cancelGame(userId,gameId);
	}
	
	/**
	 * {@inheritDoc}
	 */	
	@Override
	public User takeCard(Long userId, Long gameId) throws ServiceException {
		User user=getUser(userId);
		Game game = getGame(user,gameId);
		if(game.getStatus()==GameStatus.GAME_OVER.getValue())
			return user;//completed
		Long dealerCards = game.getDealerCardClosed()|game.getDealerCardOpened();
		Long usedCards = dealerCards |game.getUserCards();
		Long newSet = CardSetUtils.generateSet(usedCards);
		Long userCards = newSet^dealerCards;
		game.setUserCards(userCards);
		if(CardSetUtils.getSetScore(userCards)>21) {
			game.setStatus(GameStatus.GAME_OVER.getValue());
			game.setWinType(1);
		}
		
		try {
			gameDao.update(game);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}		
		return user;
	}	
	
	/**
	 * {@inheritDoc}
	 */		
	@Override
	public User finishGame(Long userId, Long gameId) throws ServiceException {
		User user=getUser(userId);
		Game game = getGame(user,gameId);
		if(game.getStatus()==GameStatus.GAME_OVER.getValue())
			return user;//completed
		
		
		Long dealerCards = game.getDealerCardClosed()|game.getDealerCardOpened();
		Long userCards = game.getUserCards();
		
		BigDecimal balanceUpdate=null;
		Integer winType = null;
		
		if(CardSetUtils.getSetScore(userCards)==21) {//Blackjack. user get x1.5 profit
			balanceUpdate=game.getCurrentBet().multiply(new BigDecimal("2.5"));
			winType = 0;//player wins
		}
		
		if(winType==null) {
			//dealer takes until 17
			while(CardSetUtils.getSetScore(dealerCards)<17) {
				Long newSet = CardSetUtils.generateSet(dealerCards|userCards);
				dealerCards = newSet^userCards;
			}
			
			if(CardSetUtils.getSetScore(dealerCards)>21) {
				winType=0;//player wins
				balanceUpdate=game.getCurrentBet().multiply(new BigDecimal(2));
			} else if(CardSetUtils.getSetScore(dealerCards)==CardSetUtils.getSetScore(userCards)){
				winType=2;//draw
				balanceUpdate=game.getCurrentBet();
			}
			else if(CardSetUtils.getSetScore(dealerCards)>CardSetUtils.getSetScore(userCards)){
				winType=1;//dealer wins
			}
			else {//player wins
				balanceUpdate=game.getCurrentBet().multiply(new BigDecimal(2));
				winType=0;
			}
		}
		
		if(balanceUpdate!=null)
			user=userService.updateBalance(user.getUsername(),balanceUpdate);
		game.setDealerCardClosed(0L);
		game.setDealerCardOpened(dealerCards);		
		game.setStatus(GameStatus.GAME_OVER.getValue());	
		game.setWinType(winType);
		try {
			gameDao.update(game);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}		
		
		return user;
	}	
	
	/**
	 * Loads and checks user
	 * @return
	 * @throws ServiceException
	 */
	private User getUser(Long userId) throws ServiceException{
		User user;
		try {
			user = userDao.get(userId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		if(user==null) {
			logger.error("User #{} not found",userId);
			throw new ServiceException("error.user.not-found");
		}
		return user;
	}
	
	/**
	 * Loads and checks game
	 * @param user
	 * @param gameId
	 * @return
	 * @throws ServiceException
	 */
	private Game getGame(User user,Long gameId) throws ServiceException{
		if(user.getGame()==null) {
			logger.error("Game #{} not present for User #{}",gameId,user.getId());
			throw new ServiceException("error.game.bet.not-started");
		}
		Game game = user.getGame();
		if(!game.getId().equals(gameId)) {
			logger.error(" Ids do not match {} to {}",game.getId(),gameId);
			throw new ServiceException("error.game.id-not-match");
		}	
		return game;
	}
}
