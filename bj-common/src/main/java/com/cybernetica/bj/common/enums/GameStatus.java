package com.cybernetica.bj.common.enums;

/**
 * Enum of game statuses
 * @author dmitri
 *
 */
public enum GameStatus {
	BETTING(1),
	BET_DONE(2),
	GAME_OVER(3);
	
	private int status;
	
	private GameStatus(int status) {
		this.status=status;
	}
	
	public static GameStatus valueOf(Integer status) {
		if(status==null)
			return null;
		
		for(GameStatus gs : GameStatus.values())
			if(gs.getValue()==status)
				return gs;
		return null;
	}

	public int getValue() {
		return status;
	}

}
