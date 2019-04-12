package com.hh.HHBank.interfaces.ATM;

import java.util.List;

import com.hh.HHBank.Entities.Account;

public interface AccountDAO {

	public Account getAcctById(long id);

	// create update delete select
	public List<Account> getAllAccts();

	public void deleteById(long id);

	public void update(Account account);

	public void createAcct(Account a);

}
