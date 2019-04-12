package com.hh.HHBank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.HHBank.DAO.AccountDAO;
import com.hh.HHBank.DAO.LogDAO;
import com.hh.HHBank.Entities.Account;
import com.hh.HHBank.Entities.Logs;

@Service
public class AccountService {
	@Autowired
	private AccountDAO accountDao;

	@Autowired
	private LogDAO logDao;

	public void updateAccountAmount(Account account) {
		accountDao.update(account);
		logDao.createLog(new Logs("Change amount", account.getUserId(),
				"The amount of the account with id " + account.getId() + " was changed to" + account.getAmmount()));
	}

	public List<Account> getAccountsByUserId(long id) {
		return accountDao.getAccountsByUserId(id);
	}

	public Account getAccountById(long id) {
		return accountDao.getAcctById(id);
	}
	
	public Account getAccountBySeries(String series) {
		return accountDao.getAcctBySeries(series);
	}

}
