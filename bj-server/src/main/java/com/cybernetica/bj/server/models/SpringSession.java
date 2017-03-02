package com.cybernetica.bj.server.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * CREATE TABLE SPRING_SESSION (
	SESSION_ID CHAR(36),
	CREATION_TIME BIGINT NOT NULL,
	LAST_ACCESS_TIME BIGINT NOT NULL,
	MAX_INACTIVE_INTERVAL INT NOT NULL,
	PRINCIPAL_NAME VARCHAR(100),
	CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (SESSION_ID)
);

CREATE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (LAST_ACCESS_TIME);
 * @author dmitri
 *
 */
@Entity(name="SPRING_SESSION")
public class SpringSession {
	
	@Id
	@Column(name="SESSION_ID",length=36)
	private String id;
	
	@Column(name="PRINCIPAL_NAME",length=100)
	private String username;
	
	@Column(name="MAX_INACTIVE_INTERVAL")
	private Integer maxInactiveInterval;
	
	@Column(name="LAST_ACCESS_TIME")
	private Long lastAccessTime;
	
	@Column(name="CREATION_TIME")
	private Long creationTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	public void setMaxInactiveInterval(Integer maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}

	public Long getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public Long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Long creationTime) {
		this.creationTime = creationTime;
	}
	
	

}
