package com.hh.HHBank.interfaces.ATM;

import java.sql.Timestamp;
import java.util.List;

import com.hh.HHBank.Entities.Transaction;


public interface TransactionDAO {

	public Transaction getTransactionById (long id);
	
	public Transaction getTransactionByDate (Timestamp ts);
	
	public List<Transaction> getAllTransactions();
	
	public void createTransaction(Transaction t);
	
	public void updateById(Transaction t);
	
	public void deletebById(long id);
	
	
}
