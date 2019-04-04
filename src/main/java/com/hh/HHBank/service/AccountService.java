package com.hh.HHBank.service;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.HHBank.DAO.AccountDAO;
import com.hh.HHBank.DAO.TransactionDAO;
import com.hh.HHBank.Entities.Account;
import com.hh.HHBank.Entities.Transaction;

@Service
public class AccountService {
	
	@Autowired
	AccountDAO accountDao;
	@Autowired
	TransactionDAO transactionDao;
	
	public String depositMoney(double ammount, long accountid, String type, String message) {
		
		long currentTimestamp = TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
		Account account = new Account();
		Transaction transaction = new Transaction();
		
		account = accountDao.getAcctById(accountid);
		
		transaction.setAmmount(ammount);
		transaction.setTransactionDate(new Timestamp(currentTimestamp));
		transaction.setTransactionType(type);
		transaction.setMessage(message);
		transaction.setTargetAccount(accountid);
		transaction.setSourceAccount(accountid);
		
		transactionDao.createTransaction(transaction);
		accountDao.updateById(account);
		
		return "The account was tranzactioned by " + ammount + account.getCurrency();
	}
}
