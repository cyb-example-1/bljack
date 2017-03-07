package com.cybernetica.bj.common.dto.user;

import java.math.BigDecimal;

import com.cybernetica.bj.common.dto.BaseDTO;
import com.cybernetica.bj.common.enums.GameStatus;

/**
 * Game DTO
 * @author dmitri
 *
 */
@SuppressWarnings("serial")
public class GameDTO extends BaseDTO {
	/**
	 * id
	 */
	private Long id;
	/**
	 * Current bet
	 */
	private BigDecimal currentBet;

	/**
	 * status
	 */
	private GameStatus status;
	
	private Long userCards;
	
	private Long dealerCards;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getCurrentBet() {
		return currentBet;
	}

	public void setCurrentBet(BigDecimal currentBet) {
		this.currentBet = currentBet;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public Long getUserCards() {
		return userCards;
	}

	public void setUserCards(Long userCards) {
		this.userCards = userCards;
	}

	public Long getDealerCards() {
		return dealerCards;
	}

	public void setDealerCards(Long dealerCards) {
		this.dealerCards = dealerCards;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentBet == null) ? 0 : currentBet.hashCode());
		result = prime * result + ((dealerCards == null) ? 0 : dealerCards.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((userCards == null) ? 0 : userCards.hashCode());
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
		GameDTO other = (GameDTO) obj;
		if (currentBet == null) {
			if (other.currentBet != null)
				return false;
		} else if (!currentBet.equals(other.currentBet))
			return false;
		if (dealerCards == null) {
			if (other.dealerCards != null)
				return false;
		} else if (!dealerCards.equals(other.dealerCards))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (status != other.status)
			return false;
		if (userCards == null) {
			if (other.userCards != null)
				return false;
		} else if (!userCards.equals(other.userCards))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GameDTO [id=" + id + ", currentBet=" + currentBet + ", status=" + status + ", userCards=" + userCards
				+ ", dealerCards=" + dealerCards + "]";
	}


}
