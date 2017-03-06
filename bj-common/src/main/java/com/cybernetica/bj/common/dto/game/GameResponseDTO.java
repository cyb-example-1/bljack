package com.cybernetica.bj.common.dto.game;

import com.cybernetica.bj.common.dto.RestObjectReponseDTO;
import com.cybernetica.bj.common.dto.user.GameDTO;

@SuppressWarnings("serial")
public class GameResponseDTO extends RestObjectReponseDTO<GameDTO> {

	public GameResponseDTO() {
		
	}
	public GameResponseDTO(GameDTO dto) {
		super(dto);
	}

}
