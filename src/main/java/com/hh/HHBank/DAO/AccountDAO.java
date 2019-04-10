package com.hh.HHBank.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.hh.HHBank.Entities.Account;

@Repository
@Transactional
public class AccountDAO implements com.hh.HHBank.interfaces.ATM.AccountDAO{
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Account getAcctById(long id) {
		Account acct = em.find(Account.class, id);
		if (acct ==null) {
			throw new EntityNotFoundException("Can't find Account for ID " + id);
		}
		return acct;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Account> getAllAccts() {
		List<Account> acct = new ArrayList<Account>();
		try {
			acct = (List<Account>) em.createQuery("select a from Account a").getResultList();
		}
		catch(Exception e){
			 e.printStackTrace();
		}
		
		return acct;
	}

	@Override
	public void deleteById(long id) {
		Account acct = em.find(Account.class, id);
		em.remove(acct);
	}

	@Override
	public void updateById(Account a) {
		em.merge(a);
	}

	@Override
	public void createAcct(Account a) {
		em.persist(a);
	
	}
	
	public long saveAcct(Account a) {
		em.persist(a);
		return a.getId();
	
	}
	
	@SuppressWarnings("unchecked")
	public List<Account> getAccountByUserId(long id) {
		List<Account> acct = null;
		try {
			acct = em.createQuery("SELECT a FROM Account a WHERE userid = :userid").setParameter("userid", id).getResultList();
		}
		catch(Exception e){
			 e.printStackTrace();
		}
		
		if (acct ==null) {
			throw new EntityNotFoundException("Can't find Account for ID " + id);
		}
		return acct;
	}
	
	public Account getAccountByIdAndUserId (long accountid, long userid) {
		Account account = null;
		try {
			account = (Account)em.createQuery("SELECT a FROM Account a WHERE id = :accountId and userId = :userId").setParameter("accountId", accountid).setParameter("userId", userid).getSingleResult();
		}
		catch(Exception e){
			 e.printStackTrace();
		}
		if (account == null) {
			throw new EntityNotFoundException("Can't find Account for " + accountid);
		}
		return account;
	}
	

}
