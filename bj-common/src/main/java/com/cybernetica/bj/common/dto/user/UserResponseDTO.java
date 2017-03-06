package com.cybernetica.bj.common.dto.user;

import com.cybernetica.bj.common.dto.RestResponseDTO;

@SuppressWarnings("serial")
public class UserResponseDTO extends RestResponseDTO {
	private UserDTO user;

	public UserResponseDTO(){
		
	}
	public UserResponseDTO(UserDTO user) {
		this.user=user;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserResponseDTO other = (UserResponseDTO) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserResponseDTO [user=" + user + "]";
	}
	
	

}
