package com.cybernetica.bj.server.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * 
CREATE TABLE SPRING_SESSION_ATTRIBUTES (
	SESSION_ID CHAR(36),
	ATTRIBUTE_NAME VARCHAR(200),
	ATTRIBUTE_BYTES LONGVARBINARY,
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_ID, ATTRIBUTE_NAME),
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_ID) REFERENCES SPRING_SESSION(SESSION_ID) ON DELETE CASCADE
);

CREATE INDEX SPRING_SESSION_ATTRIBUTES_IX1 ON SPRING_SESSION_ATTRIBUTES (SESSION_ID);
 * @author dmitri
 *
 */
@SuppressWarnings("serial")
@Entity(name="SPRING_SESSION_ATTRIBUTES")
public class SpringSessionAttributes implements Serializable{
	
	@Id
	@Column(name="SESSION_ID",length=36)
	private String id;
	
	@Id
	@Column(name="ATTRIBUTE_NAME",length=200)
	private String attrName;
	
	@Column(name="ATTRIBUTE_BYTES")
	@Lob
	private byte[] data;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	

}
