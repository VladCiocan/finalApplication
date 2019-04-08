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
@Table(name="logs")

public class Logs implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column (name = "logId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name="actiondate")
	private Timestamp actiondate;
	
	@Column(name = "actiontype")
	private String actiontype;
	
	@Column(name = "userid")
	private long uid;
	
	@Column(name = "message")
	private String message;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getActiondate() {
		return actiondate;
	}

	public void setActiondate(Timestamp actiondate) {
		this.actiondate = actiondate;
	}

	public String getActiontype() {
		return actiontype;
	}

	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Logs() {
		super();
	}
	
	
	
}


