package com.hh.HHBank.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.HHBank.DAO.AccountDAO;
import com.hh.HHBank.DAO.LogsDAO;
import com.hh.HHBank.DAO.TransactionDAO;
import com.hh.HHBank.Entities.Account;
import com.hh.HHBank.Entities.Logs;
import com.hh.HHBank.Entities.Transaction;

@Service
public class AccountService {
	
	@Autowired
	AccountDAO accountDao;
	@Autowired
	TransactionDAO transactionDao;
	@Autowired
	LogsDAO logsDao;
	
	public Account getAcctById(long id) {
		return accountDao.getAcctById(id);
	}
	
	public List<Account> getAllAccts(){
		return accountDao.getAllAccts();
	}
	
	public void deleteById(long id) {
		accountDao.deleteById(id);
	}
	
	public void updateAccount(Account a) {
		accountDao.updateById(a);
	}
	
	public void createAccount(Account a) {
		long currentTimestamp = TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
		a.setCreatedDate(new Timestamp(currentTimestamp));
		accountDao.createAcct(a);
	}
	
	public List<Account> getAccountsByUserId(long id){
		return accountDao.getAccountByUserId(id);
	}
	public Account getAccountByIdAndUserId (long accountid, long userid) {
		return accountDao.getAccountByIdAndUserId(accountid, userid);
	}
	
	public String depositMoney(double ammount, long accountid, String type, String message) {
		
		long currentTimestamp = TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
		Account account = new Account();
		Transaction transaction = new Transaction();
		
		account = accountDao.getAcctById(accountid);
		double total = account.getAmmount() + ammount;
		account.setAmmount(total);
		accountDao.updateById(account);
		
		transaction.setAmmount(ammount);
		transaction.setTransactionDate(new Timestamp(currentTimestamp));
		transaction.setTransactionType(type);
		transaction.setMessage(message);
		transaction.setTargetAccount(accountid);
		
		transactionDao.createTransaction(transaction);
		
		return "The ammount of " + ammount +" "+ account.getCurrency() + " was deposited the account";
	}
	
	public String transferMoney(long sourceAcctId, long userId, double ammount, String currency, long destinationAcctId) {
		String returnMessage = "";
		try {
			Account aSrc = accountDao.getAccountByIdAndUserId(sourceAcctId, userId);
			long currentTimestamp = TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
			if (aSrc.getAmmount() >= ammount) {
				Transaction t = new Transaction();
				t.setTransactionDate(new Timestamp(currentTimestamp));
				t.setTransactionType("transfer");
				t.setSourceAccount(sourceAcctId);
				t.setTargetAccount(destinationAcctId);
				t.setAmmount(ammount);
				t.setMessage("Transfer");
				t.setStatus("Pending aproval");
				transactionDao.createTransaction(t);

				Logs l = new Logs();
				l.setActiondate(new Timestamp(currentTimestamp));
				l.setActiontype("transfer");
				l.setUid(userId);
				l.setMessage("Transferred " + String.valueOf(ammount) + currency + " from account "
						+ String.valueOf(sourceAcctId) + " to account " + String.valueOf(destinationAcctId));
				logsDao.createLog(l);

				returnMessage = "Transfer registered successfully, please wait for approval!";
			} else {
				Logs l = new Logs();
				l.setActiondate(new Timestamp(currentTimestamp));
				l.setActiontype("transfer");
				l.setUid(userId);
				l.setMessage("Failed to transfer " + String.valueOf(ammount) + currency + " from account "
						+ String.valueOf(sourceAcctId) + " to account " + String.valueOf(destinationAcctId));
				logsDao.createLog(l);
				returnMessage = "Insufficient funds for transfer!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMessage;
	}
	
	public String topUpAccount(long accountId, long userId, double ammount, String currency) {
		long currentTimestamp = TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
		Transaction t = new Transaction();
		t.setTransactionDate(new Timestamp(currentTimestamp));
		t.setTransactionType("top up");
		t.setSourceAccount(accountId);
		t.setAmmount(ammount);
		t.setMessage("Top up account");
		t.setStatus("Pending aproval");
		transactionDao.createTransaction(t);

		Logs l = new Logs();
		l.setActiondate(new Timestamp(currentTimestamp));
		l.setActiontype("top up");
		l.setUid(userId);
		l.setMessage("Top up account " + String.valueOf(accountId) + " with " + String.valueOf(ammount) + currency);
		logsDao.createLog(l);

		return "Transaction registered, wait for approval";
	}
	
	public String withdrawMoney(long accountId, long userId, double ammount, String currency) {
		String returnMessage = "";
		try {
			Account a = accountDao.getAccountByIdAndUserId(accountId, userId);
			long currentTimestamp = TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
			if (a.getAmmount() >= ammount) {
				Transaction t = new Transaction();
				t.setTransactionDate(new Timestamp(currentTimestamp));
				t.setTransactionType("withdraw");
				t.setSourceAccount(accountId);
				t.setAmmount(ammount);
				t.setMessage("Withdraw money");
				t.setStatus("Approved");
				transactionDao.createTransaction(t);

				a.setAmmount(a.getAmmount() - ammount);
				accountDao.updateById(a);

				Logs l = new Logs();
				l.setActiondate(new Timestamp(currentTimestamp));
				l.setActiontype("withdraw");
				l.setUid(userId);
				l.setMessage("Withdrawed " + String.valueOf(ammount) + currency + " from account "
						+ String.valueOf(accountId));
				logsDao.createLog(l);

				returnMessage = "Money on the way";
			} else {
				Logs l = new Logs();
				l.setActiondate(new Timestamp(currentTimestamp));
				l.setActiontype("withdraw");
				l.setUid(userId);
				l.setMessage("Failed to withdraw " + String.valueOf(ammount) + currency + " from account "
						+ String.valueOf(accountId));
				logsDao.createLog(l);

				returnMessage = "Insufficient funds";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMessage;
	}
}
