package com.cybernetica.bj.client.events;

import com.cybernetica.bj.common.dto.user.UserResponseDTO;

@SuppressWarnings("serial")
public class UserDataEvent extends BaseEvent {

	private UserResponseDTO response;

	public UserDataEvent(UserResponseDTO response) {
		this.response = response;
	}

	public UserResponseDTO getResponse() {
		return response;
	}

}
