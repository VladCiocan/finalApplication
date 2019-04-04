package com.hh.HHBank.DAO;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.hh.HHBank.Entities.Transaction;

@Repository
@Transactional
public class TransactionDAO  implements com.hh.HHBank.interfaces.ATM.TransactionDAO{

	
	@PersistenceContext	
	private EntityManager em;
	
	@Override
	public Transaction getTransactionById(long id) {
		Transaction transaction = em.find(Transaction.class, id);
		if (transaction ==null) {
			throw new EntityNotFoundException("Can't find Transaction for ID " + id);
		}
		return transaction;
	}

	@Override
	public Transaction getTransactionByDate(Timestamp ts) {
		Transaction transaction = null;	
		try {
			transaction  = (Transaction) em.createQuery("SELECT t FROM Transaction t WHERE transactiondate = :timestamp")
					.setParameter("timestamp", ts).getSingleResult();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return transaction;
	}

	@Override
	public List<Transaction> getAllTransactions() {
		List<Transaction> transactions = em.createQuery("select t from Transaction t").getResultList();
		return transactions;
	}

	@Override
	public void createTransaction(Transaction t) {
		em.persist(t);
		
	}

	@Override
	public void updateById(Transaction t) {
		em.merge(t);
		
	}

	@Override
	public void deletebById(long id) {
		Transaction transaction = em.find(Transaction.class, id);
		em.remove(transaction);
		
	}

}
