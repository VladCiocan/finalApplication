package com.hh.HHBank.Entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "session")
public class Session implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "sessionid")
	private long id;
	
	@Column(name = "uuid")
	private String uuid;
	
	@Column(name = "sessiondate")
	private Timestamp sessionDate;
	
	@Column(name = "userid")
	private long userid;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Timestamp getSessionDate() {
		return sessionDate;
	}

	public void setSessionDate(java.sql.Timestamp timestamp) {
		this.sessionDate = timestamp;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public Session() {
		super();
	}
	
	
}
