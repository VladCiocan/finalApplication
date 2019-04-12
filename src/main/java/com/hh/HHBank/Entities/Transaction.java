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
@Table(name = "transaction")
public class Transaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "transactionid")
	private long id;

	@Column(name = "transactiondate")
	private Timestamp transactionDate;

	@Column(name = "transactiontype")
	private String transactionType;

	@Column(name = "sourceaccount")
	private long sourceAccount;

	@Column(name = "targetaccount")
	private long targetAccount;

	@Column(name = "ammount")
	private double ammount;

	@Column(name = "message")
	private String message;

	@Column(name = "status")
	private String status;

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public long getSourceAccount() {
		return sourceAccount;
	}

	public void setSourceAccount(long sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	public long getTargetAccount() {
		return targetAccount;
	}

	public void setTargetAccount(long targetAccount) {
		this.targetAccount = targetAccount;
	}

	public double getAmmount() {
		return ammount;
	}

	public void setAmmount(double ammount) {
		this.ammount = ammount;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Transaction() {
		super();
	}
}