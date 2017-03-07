package com.cybernetica.bj.common.enums;

/**
 * Card rank
 * @author dmitri
 *
 */
public enum CardRank {
	TWO(0,2),
	THREE(1,3),
	FOUR(2,4),
	FIVE(3,5),
	SIX(4,6),
	SEVEN(5,7),
	EIGTH(6,8),
	NINE(7,9),
	TEN(8,10),
	JACK(9,10),
	QUEEN(10,10),
	KING(11,10),
	ACE(12,11)
	;
	
	private int value;
	private int score;

	private CardRank(int value,int score) {
		this.value = value;
		this.score=score;
	}

	public static CardRank valueOf(Integer value) {
		if (value == null)
			return null;

		for (CardRank gs : CardRank.values())
			if (gs.getValue() == value)
				return gs;
		return null;
	}

	public int getValue() {
		return value;
	}

	public int getScore() {
		return score;
	}	
}
