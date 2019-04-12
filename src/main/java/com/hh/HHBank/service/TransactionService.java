package com.hh.HHBank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.HHBank.DAO.LogDAO;
import com.hh.HHBank.DAO.TransactionDAO;
import com.hh.HHBank.Entities.Account;
import com.hh.HHBank.Entities.Logs;
import com.hh.HHBank.Entities.Transaction;

@Service
public class TransactionService {
	@Autowired
	private TransactionDAO transactionDao;

	@Autowired
	private LogDAO logDao;

	public void createTransaction(Account account, Boolean deposit, double amount) {
		String type = null;
		if (deposit) {
			type = "deposit";
		} else {
			type = "withdrawal";
		}
		long id = account.getUserId();

		Transaction t = new Transaction();
		t.setAmmount(amount);
		t.setMessage("The user with id" + id + " made a " + type + " with the amount " + amount);
		t.setSourceAccount(account.getId());
		t.setStatus("Pending");
		t.setTransactionType(type);
		transactionDao.createTransaction(t);

		logDao.createLog(
				new Logs(type, id, "The user with id" + id + " made a " + type + " with the amount " + amount));
	}

}
