package com.hh.HHBank.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hh.HHBank.Entities.Account;
import com.hh.HHBank.service.AccountService;
import com.hh.HHBank.service.SessionService;
import com.hh.HHBank.service.TransactionService;
import com.hh.HHBank.util.ExpiredSessionException;
import com.hh.HHBank.util.NotFoundException;
import com.hh.HHBank.util.TransactionContext;

@RestController
public class TransactionController {
	@Autowired
	private TransactionService transactionService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private SessionService sessionService;

	@PostMapping("/transaction")
	public String createTransaction(@Valid @RequestBody TransactionContext req) {
		if (!sessionService.isSessionValid(req.getUuid())) {
			throw new ExpiredSessionException("Session has expired");
		}
		long userId = sessionService.getSessionUserID(req.getUuid());
		String series=req.getSeries();
		Account dbAcc = accountService.getAccountBySeries(series);
		if (dbAcc == null || dbAcc.getUserId() != userId)
			throw new NotFoundException("Account does not exist");
		double balance = req.getAmount() + Double.parseDouble(dbAcc.getAmmount());
		// check if there are enough funds for a withdrawal
		if (req.getAmount() < 0 && balance < 0)
			return "Insufficient funds";

		// create a new transaction
		if (req.getAmount() < 0 && balance > 0)
			transactionService.createTransaction(dbAcc, false, Math.abs(req.getAmount()));
		else
			transactionService.createTransaction(dbAcc, true, Math.abs(req.getAmount()));

		// Update account amount after transaction
		dbAcc.setAmmount(String.valueOf(balance));
		dbAcc.setUserId(userId);
		accountService.updateAccountAmount(dbAcc);
		return "Transaction was successful";
	}
}
