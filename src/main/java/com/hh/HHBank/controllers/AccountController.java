package com.hh.HHBank.controllers;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hh.HHBank.DAO.AccountDAO;
import com.hh.HHBank.Entities.Account;
import com.hh.HHBank.service.AccountService;

@RestController
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/accounts")
	public List<Account> getAllAccounts(Pageable pageable){
		return accountService.getAllAccts();
	}
	
	@GetMapping("/account/{id}")
	public Account getAccountById(@PathVariable long id) {
		return accountService.getAcctById(id);
	}
	
	@GetMapping("/user/{userid}/accounts")
	public List<Account> getUserAccounts(@PathVariable long userid){
		return accountService.getAccountsByUserId(userid);
	}
	
	
	@PutMapping("/account/{accountid}")
	public void updateAccount(@PathVariable long accountid, @RequestBody @Valid Account accountReq) {
		Account acctTemp = accountService.getAcctById(accountid);
		acctTemp.setStatus(accountReq.getStatus());
		acctTemp.setCurrency(accountReq.getCurrency());
		accountService.updateAccount(acctTemp);
	}
	
	@PostMapping("/account")
	public void createAccount(@RequestBody @Valid Account acct) {
		accountService.createAccount(acct);
	}
	
	@DeleteMapping("/account/{accountid}")
	public void deleteAccount(@PathVariable long accountid) {
		accountService.deleteById(accountid);
	}
	
	@PostMapping("/account/{sourceAcctId}/transfer")
	public String transferMoney(@PathVariable long sourceAcctId, long userId, double ammount, String currency, long destinationAcctId) {
		return accountService.transferMoney(sourceAcctId, userId, ammount, currency, destinationAcctId);
	}
	
	@PostMapping("/account/{accountId}/withdraw")
	public String withdrawMoney(@PathVariable long accountId, long userId, double ammount, String currency) {
		return accountService.withdrawMoney(accountId, userId, ammount, currency);
	}
	
	@PostMapping("/account/{accountid}/deposit")
	public String depositMoney(@PathVariable long accountid, String ammount, String type, String message) {
		double tempAmmount = Double.parseDouble(ammount);
		return accountService.depositMoney(tempAmmount, accountid, type, message);
	}
	
	@PostMapping("/account/{accountId}/topup")
	public String topUpAccount(@PathVariable long accountId, long userId, double ammount, String currency) {
		return accountService.topUpAccount(accountId, userId, ammount, currency);
	}
	
	
	
}
