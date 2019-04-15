package com.hh.HHBank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hh.HHBank.Entities.Account;
import com.hh.HHBank.service.AccountService;
import com.hh.HHBank.service.SessionService;
import com.hh.HHBank.util.ExpiredSessionException;
import com.hh.HHBank.util.NotFoundException;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private SessionService sessionService;

	@PostMapping(value = "/account")
	public List<Account> getAllAccounts(@RequestParam String uuid) {

		if (!sessionService.isSessionValid(uuid)) {
			throw new ExpiredSessionException("Session has expired");
		}
		long userId = sessionService.getSessionUserID(uuid);
		return accountService.getAccountsByUserId(userId);

	}

	@PostMapping("/account/id")
	public Account getAccountbySeries(@RequestParam String uuid, @RequestParam String series) {
		if (!sessionService.isSessionValid(uuid)) {
			throw new ExpiredSessionException("Session has expired");
		}
		long userId = sessionService.getSessionUserID(uuid);
		Account dbAcc = accountService.getAccountBySeries(series);
		if (dbAcc == null || dbAcc.getUserId() != userId)
			throw new NotFoundException("Account does not exist");
		return dbAcc;
	}
}
