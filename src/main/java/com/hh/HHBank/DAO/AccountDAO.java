package com.hh.HHBank.DAO;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.hh.HHBank.Entities.Account;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> getAllAccts() {
		List<Account> acct = em.createQuery("select a from Account a").getResultList();
		return acct;
	}

	@SuppressWarnings("unchecked")
	public List<Account> getAccountsByUserId(long id) {
		List<Account> accounts = null;
		try {
			accounts = (List<Account>) em.createQuery("select a from Account a where userid=:userid")
					.setParameter("userid", id).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accounts;
	}

	@Override
	public void deleteById(long id) {
		Account acct = em.find(Account.class, id);
		em.remove(acct);
	}

	@Override
	public void update(Account account) {
		if (account.getAmmount() != null) {
			Account acct = em.find(Account.class, account.getId());
			acct.setAmmount(account.getAmmount());
			em.merge(acct);
		}
	}

	@Override
	public void createAcct(Account a) {
		a.setCreatedDate(new Timestamp(TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS)));
		em.persist(a);
	}

	public long getUserId(long id) {
		Account acct = em.find(Account.class, id);
		return acct.getUserId();
	}

	@SuppressWarnings("unchecked")
	public Account getAcctBySeries(String series) {
		Account acc = null;
		try {
			List<Account> accounts = (List<Account>) em.createQuery("Select a from Account a where a.series=:series")
					.setParameter("series", series).getResultList();
			if (accounts.size() > 0)
				acc = accounts.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return acc;
	}

}
