package com.cybernetica.bj.common.dto.user;

import java.math.BigDecimal;

import com.cybernetica.bj.common.dto.BaseDTO;

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
	 * Is betting finished
	 */
	private boolean betDone;
	

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

	public boolean isBetDone() {
		return betDone;
	}

	public void setBetDone(boolean betDone) {
		this.betDone = betDone;
	}

}
