package com.cybernetica.bj.common.dto;

import com.cybernetica.bj.common.enums.CardRank;
import com.cybernetica.bj.common.enums.CardSuit;

public class CardDTO {
	private CardSuit suit;
	private CardRank rank;
	
	public CardDTO(CardSuit suit,CardRank rank) {
		this.suit=suit;
		this.rank=rank;
	}
	
	

	public CardSuit getSuit() {
		return suit;
	}



	public CardRank getRank() {
		return rank;
	}



	@Override
	public String toString() {
		return "CardDTO [suit=" + suit + ", rank=" + rank + "]";
	}

}
