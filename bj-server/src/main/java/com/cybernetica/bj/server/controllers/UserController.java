package com.cybernetica.bj.server.controllers;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cybernetica.bj.common.dto.user.BalanceChangeDTO;
import com.cybernetica.bj.common.dto.user.UserDTO;
import com.cybernetica.bj.common.dto.user.UserResponseDTO;
import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.models.User;
import com.cybernetica.bj.server.security.SecurityUtils;
import com.cybernetica.bj.server.services.UserService;

/**
 * Manages user data
 * 
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

	/**
	 * Returns user data
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/get", produces = "application/json",method=RequestMethod.GET)
	@ResponseBody
	public UserResponseDTO get() throws ServiceException {
		logger.trace("User read");
		String name = SecurityUtils.getLoggedUserName();
		return get(name);
	}
	
	/**
	 * REturns user data
	 * @param username
	 * @return
	 * @throws ServiceException
	 */
	public UserResponseDTO get(String username) throws ServiceException {
		User user;
		user = userService.loadByUsername(username);

		UserResponseDTO ret = new UserResponseDTO();
		ret.setUser(map(user));
		return ret;
	}	

	/**
	 * Updates user balance
	 * @param dto
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/balance", produces = "application/json", consumes = "application/json",method=RequestMethod.POST)
	@ResponseBody
	public UserResponseDTO balance(@RequestBody BalanceChangeDTO dto) throws ServiceException {
		logger.trace("User balance change {}", dto);
		String name = SecurityUtils.getLoggedUserName();

		User user;
		user = userService.updateBalance(name, dto.getBalanceChange());

		UserResponseDTO ret = new UserResponseDTO();
		ret.setUser(map(user));
		return ret;
	}

	/**
	 * mapping utility function
	 * @param user
	 * @return
	 */
	static UserDTO map(User user) {
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		// dto.setCurrency(currency);
		if (user.getBalance() == null)
			dto.setBalance(BigDecimal.ZERO);
		else
			dto.setBalance(user.getBalance());
		if (user.getGame() != null)
			dto.setGame(GameController.map(user.getGame()));
		return dto;
	}
}
