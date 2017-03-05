package com.cybernetica.bj.client.events;

import com.cybernetica.bj.common.dto.LogoutResponseDTO;

/**
 * Logout event
 * @author dmitr@SuppressWarnings("serial")
i
 *
 */
@SuppressWarnings("serial")
public class LogoutEvent extends BaseEvent {
	private LogoutResponseDTO response;

	public LogoutEvent(LogoutResponseDTO response){
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
		LogoutEvent other = (LogoutEvent) obj;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LogoutEvent [response=" + response + "]";
	}
	
	
}
