package com.cybernetica.bj.common.dto;

@SuppressWarnings("serial")
public class RestObjectReponseDTO<T> extends RestResponseDTO {
	private T object;

	public RestObjectReponseDTO() {
	}
	
	public RestObjectReponseDTO(T dto) {
		object=dto;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RestObjectReponseDTO other = (RestObjectReponseDTO) obj;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RestReponseDTO [object=" + object + "]";
	}
	
	
}
