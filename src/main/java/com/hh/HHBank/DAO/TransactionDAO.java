package com.hh.HHBank.DAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
	public List<Transaction> getTransactionByInterval(String ts, String tf) {
		Date fDate = LogsDAO.convertStringToDate(ts);
		Date sDate = LogsDAO.convertStringToDate(tf);
		Timestamp fts = new Timestamp(fDate.getTime());
		System.out.println(fts);
		Timestamp sts = new Timestamp(sDate.getTime());
		System.out.println(sts);
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		try {
			transactions = (List<Transaction>) em.createQuery(
					"select t from Transaction t where transactionDate > :transactionTs and transactionDate < :transactionTf")
					.setParameter("transactionTs", fts).setParameter("transactionTf", sts).getResultList();
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
