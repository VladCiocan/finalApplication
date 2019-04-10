package com.hh.HHBank.DAO;

import java.sql.Timestamp;
import java.util.ArrayList;
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

	@SuppressWarnings("unchecked")
	public List<Transaction> getTransactionByInterval(Timestamp ts, Timestamp tf) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		try {
			transactions = (List<Transaction>) em.createQuery(
					"select t from Transaction t where transactionDate > :transactionTs and transactionDate < :transactionTf")
					.setParameter("transactionTs", ts).setParameter("transactionTf", tf).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transactions;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Transaction> getAllTransactions() {
		List<Transaction> transactions = new ArrayList<Transaction>();
		try {
		transactions = (List<Transaction>)em.createQuery("select t from Transaction t").getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return transactions;
	}

	@Override
	public void createTransaction(Transaction t) {
		em.persist(t);
		
	}

	@Override
	public void updateById(Transaction transaction) {
		
		em.merge(transaction);	
	}

	@Override
	public void deletebById(long id) {
		Transaction transaction = em.find(Transaction.class, id);
		em.remove(transaction);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getBySourceAccount(long accountId) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		try {
			transactions = (List<Transaction>) em
					.createQuery("SELECT t FROM Transaction t WHERE sourceAccount = :sourceAccount")
					.setParameter("sourceAccount", accountId).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transactions;
	}

}
