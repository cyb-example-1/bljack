package com.cybernetica.bj.server.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cybernetica.bj.server.dao.GameDao;

/**
 * Game process DAO implementation
 * @author dmitri
 *
 */
@Repository
public class GameDaoImpl extends BaseDaoImpl implements GameDao {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(GameDao.class);

}
