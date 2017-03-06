package com.cybernetica.bj.common.dto.game;

import java.math.BigDecimal;

import com.cybernetica.bj.common.dto.BaseDTO;

@SuppressWarnings("serial")
public class GameBetChangeDTO extends BaseDTO {
	private Long gameId;
	private BigDecimal bet;
	
	public Long getGameId() {
		return gameId;
	}
	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
	public BigDecimal getBet() {
		return bet;
	}
	public void setBet(BigDecimal bet) {
		this.bet = bet;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bet == null) ? 0 : bet.hashCode());
		result = prime * result + ((gameId == null) ? 0 : gameId.hashCode());
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
		GameBetChangeDTO other = (GameBetChangeDTO) obj;
		if (bet == null) {
			if (other.bet != null)
				return false;
		} else if (!bet.equals(other.bet))
			return false;
		if (gameId == null) {
			if (other.gameId != null)
				return false;
		} else if (!gameId.equals(other.gameId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "GameBetDTO [gameId=" + gameId + ", bet=" + bet + "]";
	}
}
