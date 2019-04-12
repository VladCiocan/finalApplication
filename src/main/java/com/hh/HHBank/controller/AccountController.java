package com.hh.HHBank.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hh.HHBank.Entities.Account;
import com.hh.HHBank.interfaces.ATM.AccountDAO;

@RestController
public class AccountController {
	@Autowired
	private AccountDAO acctR;
	
	@GetMapping("/accounts")
	public List<Account> getAllAccounts(){
		return acctR.getAllAccts();
	}
	
	@GetMapping("/account/{id}")
	public Account getAccountById(@PathVariable long id) {
		return acctR.getAcctById(id);
	}
	
	@PutMapping("/account/{aid}")
	public void updateAccount(@PathVariable long aid, @RequestBody @Valid Account accountReq) {
		acctR.updateAccount(accountReq);
	}
	
	@PostMapping("/account")
	public void createAccount(@RequestBody @Valid Account acct) {
		acctR.createAcct(acct);
	}
	
	@DeleteMapping("/account/{acctid}")
	public void deleteAccount(@PathVariable long acctid) {
		acctR.deleteById(acctid);
	}
	
	@GetMapping("/user/{uid}/accounts")
	public List<Account> getUserAccounts(@PathVariable long uid){
		return acctR.getByUserId(uid);
	}
}
