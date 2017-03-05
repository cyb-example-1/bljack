package com.cybernetica.bj.client.events;

import com.cybernetica.bj.common.dto.user.UserDTO;
import com.cybernetica.bj.common.dto.user.UserResponseDTO;

@SuppressWarnings("serial")
public class UserDataEvent extends BaseEvent {

	private UserResponseDTO response;
	
	public UserDataEvent(){}
	public UserDataEvent(UserDTO user){
		this.response = new UserResponseDTO(user);
	}

	public UserDataEvent(UserResponseDTO response) {
		this.response = response;
	}

	public UserResponseDTO getResponse() {
		return response;
	}

}
