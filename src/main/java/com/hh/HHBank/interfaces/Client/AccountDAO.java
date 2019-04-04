package com.hh.HHBank.interfaces.Client;

import java.util.List;

import com.hh.HHBank.Entities.Account;

public interface AccountDAO {

	public Account getAcctById(long id);
	
	public List<Account> getAccountsByUser(long userId);
	
	//create update delete select
	public List<Account> getAllAccts();
	
	public void deleteById(long id);
	
	public void updateById(long id);
	
	public void createAcct(Account a);
	
}
