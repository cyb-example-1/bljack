package com.cybernetica.bj.client.events;

import com.cybernetica.bj.common.dto.login.LoginResponseDTO;

/**
 * Sent on successful login
 * @author dmitri
 *
 */
@SuppressWarnings("serial")
public class LoginEvent extends BaseEvent {
	
	private LoginResponseDTO user;

	public LoginEvent(LoginResponseDTO user){
		this.user = user;
	}

	public LoginResponseDTO getUser() {
		return user;
	}

	public void setUser(LoginResponseDTO user) {
		this.user = user;
	}
	
	

}
