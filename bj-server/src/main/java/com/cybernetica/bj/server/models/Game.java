package com.cybernetica.bj.server.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

/**
 * Game model
 * @author dmitri
 *
 */
@SuppressWarnings("serial")
@Entity(name="GAMES")
public class Game  implements Serializable{

	
	@Id
	@GenericGenerator(name="seq_games" , strategy="increment")
	@GeneratedValue(generator="seq_games")
	@Column(name="ID")
	private Long id;

	
	@Column(name="CURRENT_BET")
	private BigDecimal currentBet;
	
	@Column(name="USER_CARDS")
	private Long userCards;
	
	@Column(name="DEALER_OPENED")
	private Long dealerCardOpened;
	
	@Column(name="DEALER_CLOSED")
	private Long dealerCardClosed;
	

	/**
	 * 1 - betting in progress
	 * 2 - user has finished
	 * 3 - game over
	 */
	@Column(name="STATUS")
	private int status;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
	private Set<User> players;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<User> getPlayers() {
		return players;
	}

	public void setPlayers(Set<User> players) {
		this.players = players;
	}

	public BigDecimal getCurrentBet() {
		return currentBet;
	}

	public void setCurrentBet(BigDecimal currentBet) {
		this.currentBet = currentBet;
	}



	public Long getUserCards() {
		return userCards;
	}

	public void setUserCards(Long userCards) {
		this.userCards = userCards;
	}

	public Long getDealerCardOpened() {
		return dealerCardOpened;
	}

	public void setDealerCardOpened(Long dealerCardOpened) {
		this.dealerCardOpened = dealerCardOpened;
	}

	public Long getDealerCardClosed() {
		return dealerCardClosed;
	}

	public void setDealerCardClosed(Long dealerCardClosed) {
		this.dealerCardClosed = dealerCardClosed;
	}



	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentBet == null) ? 0 : currentBet.hashCode());
		result = prime * result + ((dealerCardClosed == null) ? 0 : dealerCardClosed.hashCode());
		result = prime * result + ((dealerCardOpened == null) ? 0 : dealerCardOpened.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((players == null) ? 0 : players.hashCode());
		result = prime * result + status;
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
		Game other = (Game) obj;
		if (currentBet == null) {
			if (other.currentBet != null)
				return false;
		} else if (!currentBet.equals(other.currentBet))
			return false;
		if (dealerCardClosed == null) {
			if (other.dealerCardClosed != null)
				return false;
		} else if (!dealerCardClosed.equals(other.dealerCardClosed))
			return false;
		if (dealerCardOpened == null) {
			if (other.dealerCardOpened != null)
				return false;
		} else if (!dealerCardOpened.equals(other.dealerCardOpened))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (players == null) {
			if (other.players != null)
				return false;
		} else if (!players.equals(other.players))
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
		return "Game [id=" + id + ", currentBet=" + currentBet + ", userCards=" + userCards + ", dealerCardOpened="
				+ dealerCardOpened + ", dealerCardClosed=" + dealerCardClosed + ", status=" + status + ", players="
				+ players + "]";
	}

	

		
	
}
