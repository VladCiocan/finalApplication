package com.hh.HHBank.controllers;


import java.sql.Timestamp;
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


import com.hh.HHBank.service.TransactionService;
import com.hh.HHBank.Entities.*;

@RestController
public class TransactionController {

	@Autowired
	private TransactionService transService;
	
	@GetMapping("/transactions")
	public List<Transaction> getAllTransactions(Pageable pageable){
		return transService.getAllTransactions();
	}
	
	@GetMapping("/transaction/{id}")
	public Transaction getTransactionById(@PathVariable long id) {
		return transService.getTransactionById(id);
	}
	
	@GetMapping("/account/{aid}/transactions")
	public List<Transaction> getBySourceAccount(@PathVariable long aid){
		return transService.getTransactionBySourceAccount(aid);
	}
	
	@GetMapping("/{transactionTs}/{transactionTf}/transaction")
	public List<Transaction> getTransactionByDate(@PathVariable Timestamp transactionTs, @PathVariable Timestamp transactionTf) {
		return transService.getTransactionByInterval(transactionTs, transactionTf);
	}
	
	@PutMapping("/transaction/{tid}")
	public void updateTransaction(@PathVariable long tid, @RequestBody @Valid Transaction transactionReq) {
		Transaction transaction = transService.getTransactionById(tid);
		transaction.setStatus(transactionReq.getStatus());
		transaction.setMessage(transactionReq.getMessage());
		transService.updateTransaction(transaction);
	}
	
	@PostMapping("/transaction")
	public void createTransaction(@RequestBody @Valid Transaction trans) {
		transService.createTransaction(trans);
	}
	
	@DeleteMapping("/transaction/{transId}")
	public void deleteTransaction(@PathVariable long transId) {
		transService.deletebById(transId);
	}
	
	@PostMapping("/transaction/approve")
	public String approveTransaction(@Valid long transID, long userID) {
		return transService.approveTransaction(transID, userID);
	}
	
	@PostMapping("/transaction/reject")
	public String rejectTransaction(@Valid long transID, long userID) {
		return transService.rejectTransaction(transID, userID);
	}
}
