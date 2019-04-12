package com.hh.HHBank.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.HHBank.DAO.AccountDAO;
import com.hh.HHBank.DAO.LogsDAO;
import com.hh.HHBank.DAO.TransactionDAO;
import com.hh.HHBank.DAO.UserDAO;
import com.hh.HHBank.Entities.Account;
import com.hh.HHBank.Entities.Logs;
import com.hh.HHBank.Entities.Transaction;
import com.hh.HHBank.Entities.User;

@Service
public class TransactionService {

	@Autowired
	private TransactionDAO transDao;
	@Autowired
	private AccountDAO accountDao;
	@Autowired
	private LogsDAO logsDao;
	@Autowired
	private UserDAO userDao;
	
	public Transaction getTransactionById(long id) {
		return transDao.getTransactionById(id);
	}
	
	public Transaction getTransactionByDate(Timestamp ts) 
	{
		return transDao.getTransactionByDate(ts);
	}
	
	public List<Transaction> getTransactionByInterval(Timestamp ts, Timestamp tf){
		return transDao.getTransactionByInterval(ts, tf);
	}
	
	public List<Transaction> getAllTransactions(){
		return transDao.getAllTransactions();
	}
	
	public String createTransaction(Transaction t) {
		transDao.createTransaction(t);
		return "OK";
	}
	
	public String updateTransaction(Transaction transaction) {
		transDao.updateById(transaction);
		return "OK";
	}
	
	public String deletebById(long id) {
		transDao.deletebById(id);
		return "OK";
	}
	
	public List<Transaction> getTransactionBySourceAccount(long accountId){
		return transDao.getBySourceAccount(accountId);
	}
	
	public String approveTransaction(long transactionID, long userId) {
		long currentTimestamp = TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
		Transaction tempTransaction = transDao.getTransactionById(transactionID);
		User tempUser = userDao.getUserById(userId);
		tempTransaction.setStatus("Approved");
		tempTransaction.setMessage("Transaction approved");
		transDao.updateById(tempTransaction);

		if (tempTransaction.getTransactionType().equals("top up")) {
			try {
				Account a = accountDao.getAcctById(tempTransaction.getSourceAccount());	
				a.setAmmount(a.getAmmount() + tempTransaction.getAmmount());
				accountDao.updateById(a);
				
				Logs l = new Logs();
				l.setActiondate(new Timestamp(currentTimestamp));
				l.setActiontype("approve transaction");
				l.setUid(userId);
				l.setMessage("Transaction " + String.valueOf(tempTransaction.getId()) + " approved by user " + tempUser.getUsername());
				logsDao.createLog(l);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (tempTransaction.getTransactionType().equals("transfer")) {
			try {
				Account aSrc = accountDao.getAcctById(tempTransaction.getSourceAccount());
				Account aDes = accountDao.getAcctById(tempTransaction.getTargetAccount());
				aSrc.setAmmount(aSrc.getAmmount() - tempTransaction.getAmmount());
						
				accountDao.updateById(aSrc);

				aDes.setAmmount(aDes.getAmmount() + tempTransaction.getAmmount());
				accountDao.updateById(aDes);
				
				Logs l = new Logs();
				l.setActiondate(new Timestamp(currentTimestamp));
				l.setActiontype("approve transaction");
				l.setUid(userId);
				l.setMessage("Transaction " + String.valueOf(tempTransaction.getId()) + " approved by user " + tempUser.getUsername());
				logsDao.createLog(l);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "Transaction approved";
	}
	
	public String rejectTransaction(long transactionID, long userId) {
		long currentTimestamp = TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
		Transaction tempTransaction = transDao.getTransactionById(transactionID);
		User tempUser = userDao.getUserById(userId);
		tempTransaction.setStatus("Rejected");
		tempTransaction.setMessage("Transaction rejected because of reasons");
		transDao.updateById(tempTransaction);

		Logs l = new Logs();
		l.setActiondate(new Timestamp(currentTimestamp));
		l.setActiontype("reject transaction");
		l.setUid(userId);
		l.setMessage("Transaction " + String.valueOf(tempTransaction.getId()) + " rejected by user " + tempUser.getUsername());
		logsDao.createLog(l);
		
		return tempTransaction.getMessage();
	}
}
