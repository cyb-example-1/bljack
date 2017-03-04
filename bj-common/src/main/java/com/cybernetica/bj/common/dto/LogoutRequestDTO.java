package com.cybernetica.bj.common.dto;

@SuppressWarnings("serial")
public class LogoutRequestDTO  extends BaseDTO {

	private String username;
	private String sessionId;

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		username=name;
		
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String authToken) {
		sessionId=authToken;
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		LogoutRequestDTO other = (LogoutRequestDTO) obj;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LogoutRequestDTO [username=" + username + ", sessionId=" + sessionId + "]";
	}

}
