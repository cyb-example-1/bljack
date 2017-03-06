package com.cybernetica.bj.server.services.impl;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		game.setBetDone(false);
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
			if(!game.getId().equals(gameId)) {
				logger.error(" Ids do not match {} to {}",game.getId(),gameId);
				throw new ServiceException("error.game.cancel.id-not-match");
			}
			user.setGame(null);;
			try {
				userDao.update(user);
				gameDao.delete(game);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}				
		}
	}
}
