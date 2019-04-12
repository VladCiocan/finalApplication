package com.hh.HHBank.controller;

import java.sql.Timestamp;
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

import com.hh.HHBank.Entities.Transaction;
import com.hh.HHBank.interfaces.ATM.TransactionDAO;

@RestController
public class TransactionController {
	@Autowired
	TransactionDAO transactionR;
	
	@GetMapping("/transactions")
	public List<Transaction> getAllTransactions(){
		return transactionR.getAllTransactions();
	}
	
	@GetMapping("/transaction/{id}")
	public Transaction getTransactionById(@PathVariable long id) {
		return transactionR.getTransactionById(id);
	}
	
	@GetMapping("/account/{aid}/transactions")
	public List<Transaction> getBySourceAccount(@PathVariable long aid){
		return transactionR.getBySourceAccount(aid);
	}
	
	@GetMapping("/{transactionTs}/{transactionTf}/transaction")
	public List<Transaction> getTransactionByDate(@PathVariable Timestamp transactionTs, @PathVariable Timestamp transactionTf) {
		return transactionR.getTransactionByInterval(transactionTs, transactionTf);
	}
	
	@PutMapping("/transaction/{tid}")
	public void updateTransaction(@PathVariable long tid, @RequestBody @Valid Transaction transactionReq) {
		transactionR.updateTransaction(transactionReq);
	}
	
	@PostMapping("/transaction")
	public void createTransaction(@RequestBody @Valid Transaction trans) {
		transactionR.createTransaction(trans);
	}
	
	@DeleteMapping("/transaction/{transId}")
	public void deleteTransaction(@PathVariable long transId) {
		transactionR.deletebById(transId);
	}
}
