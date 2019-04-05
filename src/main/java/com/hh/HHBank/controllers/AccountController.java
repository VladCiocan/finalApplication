package com.hh.HHBank.controllers;

import java.awt.print.Pageable;
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

import com.hh.HHBank.DAO.AccountDAO;
import com.hh.HHBank.Entities.Account;
import com.hh.HHBank.service.AccountService;

@RestController
public class AccountController {
	
	@Autowired
	private AccountDAO accountDao;
	
	@Autowired
	private AccountService accountServ;
	
	@GetMapping("/accounts")
	public List<Account> getAllAccounts(Pageable pageable){
		return accountDao.getAllAccts();
	}
	
	@GetMapping("/user/{userid}/accounts")
	public List<Account> getUserAccounts(@PathVariable long userid){
		return accountDao.getAccountByUserId(userid);
	}
	
	@GetMapping("/account/{id}")
	public Account getAccountById(@PathVariable long id) {
		return accountDao.getAcctById(id);
	}
	
	@PutMapping("/account/{accountid}")
	public void updateAccount(@PathVariable long accountid, @RequestBody @Valid Account accountReq) {
		Account acctTemp = accountDao.getAcctById(accountid);
		acctTemp.setStatus(accountReq.getStatus());
		acctTemp.setCurrency(accountReq.getCurrency());
		accountDao.updateById(acctTemp);
	}
	
	@PostMapping("/account")
	public void createAccount(@RequestBody @Valid Account acct) {
		accountDao.createAcct(acct);
	}
	@DeleteMapping("/account/{accountid}")
	public void deleteAccount(@PathVariable long accountid) {
		accountDao.deleteById(accountid);
	}
	
	@PostMapping("/account/deposit/{accountid}")
	public String depositInAccount(@PathVariable long accountid, @RequestBody @Valid double ammount, String type, String message) {
		accountServ.depositMoney(ammount, accountid, type, message);
		return "ok";
	}
	
	@PostMapping("/account/{sourceaccount}/transfer/{targetaccount}")
	public String transferMoneyToAccounts(@PathVariable long sourceaccount, long targetaccount, @RequestBody @Valid double ammount, String type, String message) {
		accountServ.transferMoney(ammount, sourceaccount, targetaccount, type, message);
		return "ok";
	}
}
