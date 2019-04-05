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
		double total = account.getAmmount() + ammount;
		account.setAmmount(total);
		accountDao.updateById(account);
		
		transaction.setAmmount(ammount);
		transaction.setTransactionDate(new Timestamp(currentTimestamp));
		transaction.setTransactionType(type);
		transaction.setMessage(message);
		transaction.setTargetAccount(accountid);
		
		transactionDao.createTransaction(transaction);
		
		return "The account was tranzactioned by " + ammount + account.getCurrency();
	}
	
	public String transferMoney (double ammount, long sourceaccount, long targetaccount, String transactiontype, String message) {
		long currentTimestamp = TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
		Account receiveraccount = new Account();
		Account senderaccount = new Account();
		Transaction transaction = new Transaction();
		
		receiveraccount = accountDao.getAcctById(targetaccount);
		senderaccount = accountDao.getAcctById(sourceaccount);
		
		if (senderaccount.getAmmount() >= ammount) {	
			double totalsent = senderaccount.getAmmount() - ammount;
			double totalreceived = receiveraccount.getAmmount() + ammount;
			senderaccount.setAmmount(totalsent);
			receiveraccount.setAmmount(totalreceived);
			accountDao.updateById(receiveraccount);
			accountDao.updateById(senderaccount);
			
			transaction.setAmmount(ammount);
			transaction.setTransactionDate(new Timestamp(currentTimestamp));
			transaction.setTransactionType(transactiontype);
			transaction.setMessage(message);
			transaction.setSourceAccount(sourceaccount);
			transaction.setTargetAccount(targetaccount);
			
			transactionDao.createTransaction(transaction);
			return ammount + " " + senderaccount.getCurrency()+ " were transfered to " + targetaccount;
		}
		
		return "Insufficient funds for this tranzaction";
	}
}
