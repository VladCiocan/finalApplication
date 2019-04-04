package com.hh.HHBank.interfaces.Client;

import java.sql.Timestamp;
import java.util.List;

import com.hh.HHBank.Entities.Transaction;


public interface TransactionDAO {

	public Transaction getTransactionById (long id);
	
	public Transaction getTransactionByDate (Timestamp ts);
	
	public List<Transaction> getAllTransactions();
	
	public void createTransaction(Transaction t);
	
	public void updateById(long id);
	
	public void deletebById(long id);
	
	
}