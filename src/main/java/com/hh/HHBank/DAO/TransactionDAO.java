package com.hh.HHBank.DAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.hh.HHBank.Entities.Account;
import com.hh.HHBank.Entities.Logs;
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

	@SuppressWarnings("unchecked")
	@Override
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
			transactions = (List<Transaction>) em.createQuery("SELECT t FROM Transaction t").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transactions;
	}

	@Override
	public void createTransaction(Transaction t) {
		em.persist(t);
	}

	@Override
	public void updateTransaction(Transaction transaction) {
		Transaction tempTransaction = em.find(Transaction.class, transaction.getId());
		tempTransaction.setStatus(transaction.getStatus());
		tempTransaction.setMessage(transaction.getMessage());
		em.merge(tempTransaction);
	}

	@Override
	public void deletebById(long id) {
		Transaction transaction = em.find(Transaction.class, id);
		em.remove(transaction);

	}

	@SuppressWarnings("unchecked")
	@Override
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

	@Override
	public String approveTransaction(Transaction t, long userId) {
		Transaction tempTransaction = em.find(Transaction.class, t.getId());
		tempTransaction.setStatus("Approved");
		tempTransaction.setMessage("Transaction approved");
		em.merge(tempTransaction);

		if (tempTransaction.getTransactionType() == "top up") {
			try {
				Account a = (Account) em.createQuery("SELECT a FROM Account a WHERE id = :accountID")
						.setParameter("accountId", tempTransaction.getSourceAccount()).getSingleResult();
				a.setAmmount(a.getAmmount() + tempTransaction.getAmmount());
				em.merge(a);
				em.flush();
				
				Logs l = new Logs();
				l.setActiondate(new Timestamp(System.currentTimeMillis()));
				l.setActiontype("approve transaction");
				l.setUid(userId);
				l.setMessage("Transaction " + String.valueOf(t.getId()) + " approved by user " + String.valueOf(userId));
				em.persist(l);
				em.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (tempTransaction.getTransactionType() == "transfer") {
			try {
				Account aSrc = (Account) em.createQuery("SELECT a FROM Account a WHERE id = :accountID")
						.setParameter("accountId", tempTransaction.getSourceAccount()).getSingleResult();
				Account aDes = (Account) em.createQuery("SELECT a FROM Account a WHERE id = :accountID")
						.setParameter("accountId", tempTransaction.getTargetAccount()).getSingleResult();
				aSrc.setAmmount(aSrc.getAmmount() - tempTransaction.getAmmount());
				em.merge(aSrc);
				em.flush();

				aDes.setAmmount(aDes.getAmmount() + tempTransaction.getAmmount());
				em.merge(aDes);
				em.flush();
				
				Logs l = new Logs();
				l.setActiondate(new Timestamp(System.currentTimeMillis()));
				l.setActiontype("approve transaction");
				l.setUid(userId);
				l.setMessage("Transaction " + String.valueOf(tempTransaction.getId()) + " approved by user " + String.valueOf(userId));
				em.persist(l);
				em.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "Transaction approved";
	}

	@Override
	public String rejectTransaction(Transaction t, long userId) {
		Transaction tempTransaction = em.find(Transaction.class, t.getId());
		tempTransaction.setStatus("Rejected");
		tempTransaction.setMessage("Transaction rejected because of reasons");
		em.merge(tempTransaction);

		Logs l = new Logs();
		l.setActiondate(new Timestamp(System.currentTimeMillis()));
		l.setActiontype("reject transaction");
		l.setUid(userId);
		l.setMessage("Transaction " + String.valueOf(tempTransaction.getId()) + " rejected by user " + String.valueOf(userId));
		em.persist(l);
		em.flush();
		
		return tempTransaction.getMessage();
	}

}
