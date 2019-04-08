package com.hh.HHBank.interfaces.ATM;

import java.sql.Timestamp;
import java.util.List;

import com.hh.HHBank.Entities.Transaction;


public interface TransactionDAO {

	public Transaction getTransactionById (long id);
	
	public List<Transaction> getTransactionByInterval (Timestamp ts, Timestamp tf);
	
	public List<Transaction> getAllTransactions();
	
	public void createTransaction(Transaction t);
	
	public void updateTransaction(Transaction transaction);
	
	public void deletebById(long id);
	
	public List<Transaction> getBySourceAccount(long accountId);
	
	public String approveTransaction(Transaction t, long userId);
	
	public String rejectTransaction(Transaction t, long userId);
}
