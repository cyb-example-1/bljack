package com.cybernetica.bj.common.dto.user;

import com.cybernetica.bj.common.dto.BaseDTO;

/**
 * Game DTO
 * @author dmitri
 *
 */
@SuppressWarnings("serial")
public class GameDTO extends BaseDTO {
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
