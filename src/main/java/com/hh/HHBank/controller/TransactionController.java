package com.hh.HHBank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hh.HHBank.Entities.Account;
import com.hh.HHBank.service.AccountService;
import com.hh.HHBank.service.SessionService;
import com.hh.HHBank.service.TransactionService;
import com.hh.HHBank.util.ExpiredSessionException;
import com.hh.HHBank.util.NotFoundException;
import com.hh.HHBank.util.ServerException;

@CrossOrigin(origins = "*")
@RestController
public class TransactionController {
	@Autowired
	private TransactionService transactionService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private SessionService sessionService;

	@PostMapping("/transaction")
	public String createTransaction(@RequestParam("uuid") String uuid, @RequestParam("series") String series,
			@RequestParam("amount") double amount) {
		if (!sessionService.isSessionValid(uuid)) {
			throw new ExpiredSessionException("Session has expired");
		}
		long userId = sessionService.getSessionUserID(uuid);
		Account dbAcc = accountService.getAccountBySeries(series);
		if (dbAcc == null || dbAcc.getUserId() != userId)
			throw new NotFoundException("Account does not exist");
		double balance = amount + Double.parseDouble(dbAcc.getAmmount());
		// check if there are enough funds for a withdrawal
		if (amount < 0 && balance < 0)
			throw new ServerException("Insufficient funds");

		// create a new transaction
		if (amount < 0 && balance > 0)
			transactionService.createTransaction(dbAcc, false, Math.abs(amount));
		else
			transactionService.createTransaction(dbAcc, true, Math.abs(amount));

		accountService.updateAccountAmount(dbAcc, amount);
		return "Transaction was successful";
	}

	@PostMapping("/transfer")
	public String createTransfer(@RequestParam("uuid") String uuid, @RequestParam("originSeries") String originSeries,
			@RequestParam("amount") double amount, @RequestParam("destinationSeries") String destinationSeries,
			@RequestParam("message") String message) {
		if (!sessionService.isSessionValid(uuid)) {
			throw new ExpiredSessionException("Session has expired");
		}
		long userId = sessionService.getSessionUserID(uuid);
		Account oAcc = accountService.getAccountBySeries(originSeries);
		if (oAcc == null || oAcc.getUserId() != userId)
			throw new NotFoundException("Origin account does not exist");
		Account dAcc = accountService.getAccountBySeries(destinationSeries);
		if (dAcc == null)
			throw new NotFoundException("Destination account does not exist");
		double balance = Double.parseDouble(oAcc.getAmmount()) - amount;
		// check if there are enough funds for a withdrawal
		if (balance < 0)
			throw new ServerException("Insufficient funds");

		// create a new transfer
		transactionService.createTransfer(oAcc, dAcc, amount, message);

		// Update origin account amount after transfer
		accountService.updateAccountAmount(oAcc, -amount);

		accountService.updateAccountAmount(dAcc, amount);
		return "Transfer was successful";
	}
}
