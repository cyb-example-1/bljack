package com.cybernetica.bj.client.events;

import com.cybernetica.bj.common.dto.login.LoginResponseDTO;

/**
 * Sent on successful login
 * @author dmitri
 *
 */
@SuppressWarnings("serial")
public class LoginEvent extends BaseEvent {
	
	private LoginResponseDTO response;

	public LoginEvent(LoginResponseDTO response){
		this.response = response;
	}

	public LoginResponseDTO getResponse() {
		return response;
	}

	public void setResponse(LoginResponseDTO response) {
		this.response = response;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((response == null) ? 0 : response.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginEvent other = (LoginEvent) obj;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LoginEvent [response=" + response + "]";
	}

}
