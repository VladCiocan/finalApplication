package com.hh.HHBank.DAO;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.hh.HHBank.Entities.Transaction;

@Repository
@Transactional
public class TransactionDAO implements com.hh.HHBank.interfaces.ATM.TransactionDAO {
	@PersistenceContext
	private EntityManager em;

	@Override
	public Transaction getTransactionById(long id) {
		Transaction transaction = em.find(Transaction.class, id);
		return transaction;
	}

	@Override
	public Transaction getTransactionByDate(Timestamp ts) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transaction> getAllTransactions() {
		List<Transaction> transactions = null;
		try {
			transactions = (List<Transaction>) em.createQuery("select t from Transaction t").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transactions;
	}

	@Override
	public void createTransaction(Transaction t) {
		t.setTransactionDate(new Timestamp(System.currentTimeMillis()));
		em.persist(t);

	}

	@Override
	public void updateById(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletebById(long id) {
		Transaction transaction = em.find(Transaction.class, id);
		em.remove(transaction);

	}

}
