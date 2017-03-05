package com.cybernetica.bj.common.dto.user;

import com.cybernetica.bj.common.dto.BaseRestResponseDTO;

@SuppressWarnings("serial")
public class UserResponseDTO extends BaseRestResponseDTO{

	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
