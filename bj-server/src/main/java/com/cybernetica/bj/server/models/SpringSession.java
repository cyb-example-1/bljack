package com.cybernetica.bj.server.models;

import java.io.Serializable;

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
@SuppressWarnings("serial")
@Entity(name="SPRING_SESSION")
public class SpringSession  implements Serializable{
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationTime == null) ? 0 : creationTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastAccessTime == null) ? 0 : lastAccessTime.hashCode());
		result = prime * result + ((maxInactiveInterval == null) ? 0 : maxInactiveInterval.hashCode());
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
		SpringSession other = (SpringSession) obj;
		if (creationTime == null) {
			if (other.creationTime != null)
				return false;
		} else if (!creationTime.equals(other.creationTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastAccessTime == null) {
			if (other.lastAccessTime != null)
				return false;
		} else if (!lastAccessTime.equals(other.lastAccessTime))
			return false;
		if (maxInactiveInterval == null) {
			if (other.maxInactiveInterval != null)
				return false;
		} else if (!maxInactiveInterval.equals(other.maxInactiveInterval))
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
		return "SpringSession [id=" + id + ", username=" + username + ", maxInactiveInterval=" + maxInactiveInterval
				+ ", lastAccessTime=" + lastAccessTime + ", creationTime=" + creationTime + "]";
	}
	
	

}
