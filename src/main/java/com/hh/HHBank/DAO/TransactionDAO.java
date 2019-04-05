package com.hh.HHBank.DAO;

import java.sql.Timestamp;
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

	@Override
	public List<Transaction> getTransactionByInterval(Timestamp ts, Timestamp tf) {
		List<Transaction> transactions = em.createQuery(
				"select t from Transaction t where transactionDate > :transactionTs and transactionDate < :transactionTf")
				.setParameter("transactionTs", ts).setParameter("transactionTf", tf).getResultList();
		return transactions;
	}

	@Override
	public List<Transaction> getAllTransactions() {
		List<Transaction> transactions = em.createQuery("SELECT t FROM Transaction t").getResultList();
		return transactions;
	}

	@Override
	public void createTransaction(Transaction t) {
		em.persist(t);
	}

	@Override
	public void updateTransaction(Transaction transaction) {
		em.merge(transaction);
	}

	@Override
	public void deletebById(long id) {
		Transaction transaction = em.find(Transaction.class, id);
		em.remove(transaction);

	}

	@Override
	public List<Transaction> getBySourceAccount(long accountId) {
		List<Transaction> transactions = em
				.createQuery("SELECT t FROM Transaction t WHERE sourceAccount = :sourceAccount")
				.setParameter("sourceAccount", accountId).getResultList();
		return transactions;
	}

	@Override
	public void approveTransaction(Transaction t, long userId) {

		t.setStatus("Approved");
		t.setMessage("Transaction approved");
		em.merge(t);

		if (t.getTransactionType() == "top up") {
			Account a = (Account) em.createQuery("SELECT a FROM Account a WHERE id = :accountID")
					.setParameter("accountId", t.getSourceAccount()).getSingleResult();
			a.setAmmount(a.getAmmount() + t.getAmmount());
			em.merge(a);
			em.flush();
		} else if (t.getTransactionType() == "transfer") {
			Account aSrc = (Account) em.createQuery("SELECT a FROM Account a WHERE id = :accountID")
					.setParameter("accountId", t.getSourceAccount()).getSingleResult();
			Account aDes = (Account) em.createQuery("SELECT a FROM Account a WHERE id = :accountID")
					.setParameter("accountId", t.getTargetAccount()).getSingleResult();
			aSrc.setAmmount(aSrc.getAmmount() - t.getAmmount());
			em.merge(aSrc);
			em.flush();

			aDes.setAmmount(aDes.getAmmount() + t.getAmmount());
			em.merge(aDes);
			em.flush();
		}

		Logs l = new Logs();
		l.setActiondate(new Timestamp(System.currentTimeMillis()));
		l.setActiontype("approve transaction");
		l.setUid(userId);
		l.setMessage("Transaction " + String.valueOf(t.getId()) + " approved by user " + String.valueOf(userId));
		em.persist(l);
		em.flush();

	}

	@Override
	public void rejectTransaction(Transaction t, long userId) {
		t.setStatus("Rejected");
		t.setMessage("Transaction rejected because of reasons");
		em.merge(t);

		Logs l = new Logs();
		l.setActiondate(new Timestamp(System.currentTimeMillis()));
		l.setActiontype("reject transaction");
		l.setUid(userId);
		l.setMessage("Transaction " + String.valueOf(t.getId()) + " rejected by user " + String.valueOf(userId));
		em.persist(l);
		em.flush();
	}

}
