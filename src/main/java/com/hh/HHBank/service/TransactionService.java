package com.hh.HHBank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.HHBank.DAO.LogDAO;
import com.hh.HHBank.DAO.TransactionDAO;
import com.hh.HHBank.Entities.Account;
import com.hh.HHBank.Entities.Logs;
import com.hh.HHBank.Entities.Transaction;
import com.hh.HHBank.util.Globals;

@Service
public class TransactionService {
	@Autowired
	private TransactionDAO transactionDao;

	@Autowired
	private LogDAO logDao;

	public void createTransaction(Account account, Boolean deposit, double amount) {
		String type = null;
		if (deposit) {
			type = Globals.deposit;
		} else {
			type = Globals.withdraw;
		}
		long id = account.getUserId();

		Transaction t = new Transaction();
		t.setAmmount(amount);
		t.setMessage("The user with id" + id + " made a " + type + " with the amount " + amount);
		t.setSourceAccount(account.getId());
		t.setTargetAccount(account.getId());
		t.setStatus(Globals.pending);
		t.setTransactionType(type);
		transactionDao.createTransaction(t);

		logDao.createLog(
				new Logs(type, id, "The user with id" + id + " made a " + type + " with the amount " + amount));
	}

	public void createTransfer(Account oAccount, Account dAccount, double amount, String message) {
		long id = oAccount.getUserId();

		Transaction t = new Transaction();
		t.setAmmount(amount);
		t.setMessage(message);
		t.setSourceAccount(oAccount.getId());
		t.setSourceAccount(dAccount.getId());
		t.setStatus(Globals.pending);
		t.setTransactionType(Globals.transfer);
		transactionDao.createTransaction(t);

		logDao.createLog(new Logs(Globals.transfer, id, "The user with id" + id + " made a tranfer with the amount "
				+ amount + " to the user with id " + dAccount.getId()));
	}
}
