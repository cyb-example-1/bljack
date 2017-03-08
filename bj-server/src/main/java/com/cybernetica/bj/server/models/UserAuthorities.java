package com.cybernetica.bj.server.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * Authorities
 * @author dmitri
 *
 */
@SuppressWarnings("serial")
@Entity(name="AUTHORITIES")
public class UserAuthorities  implements Serializable{
	@Id
	@GenericGenerator(name="seq_authorities" , strategy="increment")
	@GeneratedValue(generator="seq_authorities")
	@Column(name="ID")
	private Long id;
	
	@Column(name="USERNAME",length=100)
	private String username;
	
	
	@Column(name="AUTHORITY",length=100)
	private String authority;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getAuthority() {
		return authority;
	}


	public void setAuthority(String authority) {
		this.authority = authority;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authority == null) ? 0 : authority.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		UserAuthorities other = (UserAuthorities) obj;
		if (authority == null) {
			if (other.authority != null)
				return false;
		} else if (!authority.equals(other.authority))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "UserAuthorities [id=" + id + ", username=" + username + ", authority=" + authority + "]";
	}	
	

}
