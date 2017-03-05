package com.cybernetica.bj.common.dto.user;

import java.math.BigDecimal;
import java.util.Currency;

import com.cybernetica.bj.common.dto.BaseRestResponseDTO;

@SuppressWarnings("serial")
public class UserResponseDTO extends BaseRestResponseDTO{

	private Long id;
	private String username;
	private Currency  currency;
	private BigDecimal balance;
	private Long gameId;
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
	
	
}
