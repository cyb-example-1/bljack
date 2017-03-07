package com.cybernetica.bj.common.enums;

/**
 * Card suits
 * @author dmitri
 *
 */
public enum CardSuit {
	HEARTS(0), TILES(1), CLOVERS(2), PIKES(3);

	private int value;

	private CardSuit(int value) {
		this.value = value;
	}

	public static CardSuit valueOf(Integer value) {
		if (value == null)
			return null;

		for (CardSuit gs : CardSuit.values())
			if (gs.getValue() == value)
				return gs;
		return null;
	}

	public int getValue() {
		return value;
	}

}
