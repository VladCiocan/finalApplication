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
public class AccountDAO implements com.hh.HHBank.interfaces.ATM.AccountDAO {
	@PersistenceContext
	private EntityManager em;

	@Override
	public Account getAcctById(long id) {
		Account acct = em.find(Account.class, id);
		return acct;
	}

	@Override
	public List<Account> getAllAccts() {
		List<Account> acct = em.createQuery("SELECT a FROM Account a").getResultList();
		return acct;
	}

	@Override
	public void deleteById(long id) {
		Account acct = em.find(Account.class, id);
		em.remove(acct);
	}

	@Override
	public void updateAccount(Account acct) {
		em.merge(acct);
	}

	@Override
	public void createAcct(Account a) {
		em.persist(a);
	}

	@Override
	public List<Account> getByUserId(long userId) {
		List<Account> userAccts = em.createQuery("SELECT a FROM Account a WHERE userid = :userid")
				.setParameter("userid", userId).getResultList();
		return userAccts;
	}

	@Override
	public String withdrawMoney(long accountId, long userId, double ammount, String currency) {
		Account a = (Account) em.createQuery("SELECT a FROM Account a WHERE id = :accountID and userId = :userId")
				.setParameter("accountId", accountId).setParameter("userId", userId).getSingleResult();
		if (a.getAmmount() >= ammount) {
			Transaction t = new Transaction();
			t.setTransactionDate(new Timestamp(System.currentTimeMillis()));
			t.setTransactionType("withdraw");
			t.setSourceAccount(accountId);
			t.setAmmount(ammount);
			t.setMessage("Withdraw money");
			t.setStatus("Approved");
			em.persist(t);
			em.flush();

			a.setAmmount(a.getAmmount() - ammount);
			em.merge(a);
			em.flush();

			Logs l = new Logs();
			l.setActiondate(new Timestamp(System.currentTimeMillis()));
			l.setActiontype("withdraw");
			l.setUid(userId);
			l.setMessage(
					"Withdrawed " + String.valueOf(ammount) + currency + " from account " + String.valueOf(accountId));
			em.persist(l);
			em.flush();

			return "Money on the way";
		} else {
			Logs l = new Logs();
			l.setActiondate(new Timestamp(System.currentTimeMillis()));
			l.setActiontype("withdraw");
			l.setUid(userId);
			l.setMessage("Failed to withdraw " + String.valueOf(ammount) + currency + " from account "
					+ String.valueOf(accountId));
			em.persist(l);
			em.flush();

			return "Insufficient funds";
		}
	}

	@Override
	public String topUpAccount(long accountId, long userId, double ammount, String currency) {
		Transaction t = new Transaction();
		t.setTransactionDate(new Timestamp(System.currentTimeMillis()));
		t.setTransactionType("top up");
		t.setSourceAccount(accountId);
		t.setAmmount(ammount);
		t.setMessage("Top up account");
		t.setStatus("Pending aproval");
		em.persist(t);
		em.flush();

		Logs l = new Logs();
		l.setActiondate(new Timestamp(System.currentTimeMillis()));
		l.setActiontype("top up");
		l.setUid(userId);
		l.setMessage("Top up account " + String.valueOf(accountId) + " with " + String.valueOf(ammount) + currency);
		em.persist(l);
		em.flush();

		return "Transaction registered, wait for approval";
	}

	@Override
	public String transferMoney(long sourceAcctId, long userId, double ammount, String currency,
			long destinationAcctId) {
		Account aSrc = (Account) em.createQuery("SELECT a FROM Account a WHERE id = :accountID and userId = :userId")
				.setParameter("accountId", sourceAcctId).setParameter("userId", userId).getSingleResult();
		if (aSrc.getAmmount() >= ammount) {
			Transaction t = new Transaction();
			t.setTransactionDate(new Timestamp(System.currentTimeMillis()));
			t.setTransactionType("transfer");
			t.setSourceAccount(sourceAcctId);
			t.setTargetAccount(destinationAcctId);
			t.setAmmount(ammount);
			t.setMessage("Transfer");
			t.setStatus("Pending aproval");
			em.persist(t);
			em.flush();

			Logs l = new Logs();
			l.setActiondate(new Timestamp(System.currentTimeMillis()));
			l.setActiontype("transfer");
			l.setUid(userId);
			l.setMessage("Transferred " + String.valueOf(ammount) + currency + " from account "
					+ String.valueOf(sourceAcctId) + " to account " + String.valueOf(destinationAcctId));
			em.persist(l);
			em.flush();

			return "Transfer registered successfully, please wait for approval!";
		} else {
			Logs l = new Logs();
			l.setActiondate(new Timestamp(System.currentTimeMillis()));
			l.setActiontype("transfer");
			l.setUid(userId);
			l.setMessage("Failed to transfer " + String.valueOf(ammount) + currency + " from account "
					+ String.valueOf(sourceAcctId) + " to account " + String.valueOf(destinationAcctId));
			em.persist(l);
			em.flush();
			return "Insufficient funds for transfer!";
		}
	}

}
