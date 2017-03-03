package com.cybernetica.bj.server.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("serial")
@Entity(name="USERS")
public class User implements Serializable{
	
	@Id
	@GenericGenerator(name="seq_users" , strategy="increment")
	@GeneratedValue(generator="seq_users")
	@Column(name="ID")
	private Long id;
	
	@Column(name="USERNAME",length=100)
	private String username;
	
	
	@Column(name="PASSWORD",length=100)
	private String password;	
	
	@Column(name="ENABLED")
	private Boolean enabled=true;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	

}
