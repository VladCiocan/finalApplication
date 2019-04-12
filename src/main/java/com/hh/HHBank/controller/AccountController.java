package com.hh.HHBank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hh.HHBank.Entities.Account;
import com.hh.HHBank.Entities.Session;
import com.hh.HHBank.service.AccountService;
import com.hh.HHBank.service.SessionService;
import com.hh.HHBank.util.AccountContext;
import com.hh.HHBank.util.ExpiredSessionException;
import com.hh.HHBank.util.NotFoundException;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private SessionService sessionService;

	@PostMapping("/account")
	public List<Account> getAllAccounts(@RequestBody Session s) {
		if (!sessionService.isSessionValid(s.getUuid())) {
			throw new ExpiredSessionException("Session has expired");
		}
		long userId = sessionService.getSessionUserID(s.getUuid());
		return accountService.getAccountsByUserId(userId);

	}

	@PostMapping("/account/id")
	public Account getAccountbySeries(@RequestBody AccountContext req) {
		if (!sessionService.isSessionValid(req.getUuid())) {
			throw new ExpiredSessionException("Session has expired");
		}
		long userId = sessionService.getSessionUserID(req.getUuid());
		Account dbAcc = accountService.getAccountBySeries(req.getSeries());
		if (dbAcc == null || dbAcc.getUserId() != userId)
			throw new NotFoundException("Account does not exist");
		return dbAcc;
	}
}
