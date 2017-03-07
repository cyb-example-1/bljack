package com.cybernetica.bj.common.dto.user;

import com.cybernetica.bj.common.dto.RestObjectReponseDTO;

@SuppressWarnings("serial")
public class UserResponseDTO extends RestObjectReponseDTO<UserDTO> {

	public UserResponseDTO(UserDTO dto) {
		super(dto);
	}

	public UserResponseDTO() {
	}

	public void setUser(UserDTO dto) {
		setObject(dto);		
	}

	public UserDTO getUser() {
		return getObject();
	}	

}
