package com.cybernetica.bj.server.services.impl;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybernetica.bj.common.CardSetUtils;
import com.cybernetica.bj.server.dao.GameDao;
import com.cybernetica.bj.server.dao.UserDao;
import com.cybernetica.bj.server.exceptions.DaoException;
import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.models.Game;
import com.cybernetica.bj.server.models.User;
import com.cybernetica.bj.server.services.GameService;


@Service
@Transactional
public class GameServiceImpl extends BaseServiceImpl implements GameService {
	
	@Autowired
	private GameDao gameDao;
	
	@Autowired
	private UserDao userDao;

	@Override
	public Game createGame(Long userId) throws ServiceException {
		User user;
		try {
			user = userDao.get(userId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		
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

	@Override
	public void cancelGame(Long userId, Long gameId) throws ServiceException {
		User user;
		try {
			user = userDao.get(userId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		if(user.getGame()!=null) {
			Game game = user.getGame();

			user.setGame(null);;
			try {
				userDao.update(user);
				gameDao.delete(game);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}				
		}
	}

	@Override
	public Game betGame(Long userId, Long gameId, BigDecimal betAugment) throws ServiceException {
		User user;
		try {
			user = userDao.get(userId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		
		if(user.getGame()==null) {
			logger.error("Betting not started game User #{} for Game #{}",userId,gameId);
			throw new ServiceException("error.game.bet.not-started");
		}
		Game game = user.getGame();
		if(!game.getId().equals(gameId)) {
			logger.error(" Ids do not match {} to {}",game.getId(),gameId);
			throw new ServiceException("error.game.cancel.id-not-match");
		}		
		
		
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

	@Override
	public User beginGame(Long userId, Long gameId) throws ServiceException {
		User user;
		try {
			user = userDao.get(userId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		
		if(user.getGame()==null) {
			logger.error("Betting not started game User #{} for Game #{}",userId,gameId);
			throw new ServiceException("error.game.bet.not-started");
		}
		Game game = user.getGame();
		if(!game.getId().equals(gameId)) {
			logger.error(" Ids do not match {} to {}",game.getId(),gameId);
			throw new ServiceException("error.game.cancel.id-not-match");
		}	
		
		//generate cards
		Long userBitSet = CardSetUtils.generateSet(CardSetUtils.generateCard(0L));
		
		Long dealerHidden=CardSetUtils.generateCard(userBitSet);
		Long dealerSeen=CardSetUtils.generateCard(dealerHidden|userBitSet);
		
		user.setBalance(user.getBalance().subtract(game.getCurrentBet()));
		
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
	
}
