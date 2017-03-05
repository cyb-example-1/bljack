package com.cybernetica.bj.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cybernetica.bj.common.dto.user.BalanceChangeDTO;
import com.cybernetica.bj.common.dto.user.GameDTO;
import com.cybernetica.bj.common.dto.user.UserDTO;
import com.cybernetica.bj.common.dto.user.UserResponseDTO;
import com.cybernetica.bj.server.exceptions.ControllerException;
import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.models.Game;
import com.cybernetica.bj.server.models.User;
import com.cybernetica.bj.server.security.SecurityUtils;
import com.cybernetica.bj.server.services.UserService;

/**
 * Manages user data
 * @author dmitri
 *
 */
@RestController
@RequestMapping("/user")
@Secured("USER")
public class UserController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/get",produces = "application/json", consumes="application/json")
	@ResponseBody
	public UserResponseDTO get() {
		logger.trace("User read");
		String name = SecurityUtils.getLoggedUserName();
		User user;
		try {
			user = userService.findByUsername(name);
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
		
		UserResponseDTO ret = new UserResponseDTO();
		ret.setUser(map(user));
		return ret;
	}
	
	@RequestMapping(value="/balance",produces = "application/json", consumes="application/json")
	@ResponseBody
	public UserResponseDTO balance(@RequestBody BalanceChangeDTO dto) {
		logger.trace("User balance change {}",dto);
		String name = SecurityUtils.getLoggedUserName();
		
		
		User user;
		try {
			user = userService.updateBalance(name,dto.getBalanceChange());
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
		
		UserResponseDTO ret = new UserResponseDTO();
		ret.setUser(map(user));
		return ret;
	}

	private UserDTO map(User user){
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		//dto.setCurrency(currency);
		dto.setBalance(user.getBalance());
		if(user.getGame()!=null)
			dto.setGame(map(user.getGame()));
		return dto;
	}

	private GameDTO map(Game game) {
		GameDTO ret = new GameDTO();
		ret.setId(game.getId());
		return ret;
	}
}
