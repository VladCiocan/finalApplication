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

import com.hh.HHBank.service.SessionService;
import com.hh.HHBank.service.TransactionService;
import com.hh.HHBank.service.UserService;
import com.hh.HHBank.Entities.*;
import com.hh.HHBank.config.ExceptionNotFoundObject;

@RestController
public class TransactionController {

	@Autowired
	private TransactionService transService;
	@Autowired
	private SessionService sessionService;
		
	@GetMapping("/transactions")
	public List<Transaction> getAllTransactions(Pageable pageable){
		Session session = sessionService.getBySessionUUID(UserService.UUID);
		if(sessionService.isValid(session)) {
			return transService.getAllTransactions();
		}
		throw new ExceptionNotFoundObject("Your session is expired!");
	}
	
	@GetMapping("/transaction/{id}")
	public Transaction getTransactionById(@PathVariable long id) {
		Session session = sessionService.getBySessionUUID(UserService.UUID);
		if(sessionService.isValid(session)) {
			return transService.getTransactionById(id);
		}
		throw new ExceptionNotFoundObject("Your session is expired!");
	}
	
	@GetMapping("/account/{aid}/transactions")
	public List<Transaction> getBySourceAccount(@PathVariable long aid){
		Session session = sessionService.getBySessionUUID(UserService.UUID);
		if(sessionService.isValid(session)) {
			return transService.getTransactionBySourceAccount(aid);
		}
		throw new ExceptionNotFoundObject("Your session is expired!");
	}
	
	@GetMapping("/{transactionTs}/{transactionTf}/transaction")
	public List<Transaction> getTransactionByDate(@PathVariable String transactionTs, @PathVariable String transactionTf) {
		Session session = sessionService.getBySessionUUID(UserService.UUID);
		if(sessionService.isValid(session)) {
			return transService.getTransactionByInterval(transactionTs, transactionTf);
		}
		throw new ExceptionNotFoundObject("Your session is expired!");
	}
	
	@PutMapping("/transaction/{tid}")
	public String updateTransaction(@PathVariable long tid, @RequestBody @Valid Transaction transactionReq) {
		Session session = sessionService.getBySessionUUID(UserService.UUID);
		if(sessionService.isValid(session)) {
			Transaction transaction = transService.getTransactionById(tid);
			transaction.setStatus(transactionReq.getStatus());
			transaction.setMessage(transactionReq.getMessage());
			transService.updateTransaction(transaction);
			return "Transaction " + transaction.getId() + " was updated!";
		}
		throw new ExceptionNotFoundObject("Your session is expired!");
	}
	
	@PostMapping("/transaction")
	public String createTransaction(@RequestBody @Valid Transaction trans) {
		Session session = sessionService.getBySessionUUID(UserService.UUID);
		if(sessionService.isValid(session)) {
			transService.createTransaction(trans);
			return "Transaction " + trans.getId() + " was created!";
		}
		throw new ExceptionNotFoundObject("Your session is expired!");
	}
	
	@DeleteMapping("/transaction/{transId}")
	public String deleteTransaction(@PathVariable long transId) {
		
		Session session = sessionService.getBySessionUUID(UserService.UUID);
		if(sessionService.isValid(session)) {
			transService.deletebById(transId);
			return "Transaction with id " + transId + " was deleted!";
		}
		throw new ExceptionNotFoundObject("Your session is expired!");
	}
	
	@PostMapping("/transaction/approve")
	public String approveTransaction(@Valid long transID, long userID) {
		Session session = sessionService.getBySessionUUID(UserService.UUID);
		if(sessionService.isValid(session)) {
			return transService.approveTransaction(transID, userID);
		}
		throw new ExceptionNotFoundObject("Your session is expired!");
	}
	
	@PostMapping("/transaction/reject")
	public String rejectTransaction(@Valid long transID, long userID) {
		Session session = sessionService.getBySessionUUID(UserService.UUID);
		if(sessionService.isValid(session)) {
			return transService.rejectTransaction(transID, userID);
		}
		throw new ExceptionNotFoundObject("Your session is expired!");
	}
}
