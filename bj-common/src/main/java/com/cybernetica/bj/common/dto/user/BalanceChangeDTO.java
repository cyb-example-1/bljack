package com.cybernetica.bj.common.dto.user;

import java.math.BigDecimal;

import com.cybernetica.bj.common.dto.BaseDTO;

@SuppressWarnings("serial")
public class BalanceChangeDTO extends BaseDTO {
	private BigDecimal balanceChange;

	public BigDecimal getBalanceChange() {
		return balanceChange;
	}

	public void setBalanceChange(BigDecimal balanceChange) {
		this.balanceChange = balanceChange;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((balanceChange == null) ? 0 : balanceChange.hashCode());
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
		BalanceChangeDTO other = (BalanceChangeDTO) obj;
		if (balanceChange == null) {
			if (other.balanceChange != null)
				return false;
		} else if (!balanceChange.equals(other.balanceChange))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BalanceChangeDTO [balanceChange=" + balanceChange + "]";
	}
	
	

}
