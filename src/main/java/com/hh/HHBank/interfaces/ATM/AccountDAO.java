package com.hh.HHBank.interfaces.ATM;

import java.util.List;

import com.hh.HHBank.Entities.Account;

public interface AccountDAO {
		
	public Account getAcctById(long id);
		
	//create update delete select
	public List<Account> getAllAccts();
	
	public void deleteById(long id);
	
	public void updateAccount(Account acct);
	
	public void createAcct(Account a);
	
	public List<Account> getByUserId(long userId);
	
	public String withdrawMoney(long accountId, long userId, double ammount, String currency);
	
	public String topUpAccount(long accountId, long userId, double ammount, String currency);
	
	public String transferMoney(long sourceAcctId, long userId, double ammount, String currency, long destinationAcctId);
}
