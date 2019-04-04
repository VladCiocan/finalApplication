package com.hh.HHBank.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.hh.HHBank.Entities.Account;
import com.hh.HHBank.Entities.Session;

public class AccountDAO implements com.hh.HHBank.interfaces.Client.AccountDAO{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Account getAcctById(long id) {
		Account acct = em.find(Account.class, id);
		return acct;
	}
	
	@Override
	public List<Account> getAccountsByUser(long userId) {
		List<Account> acct = new ArrayList<Account>();
		try {
			acct  = (ArrayList<Account>) em.createQuery("SELECT s FROM Account s WHERE s.userId = :userId").setParameter("userId", userId).getResultList();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return acct;
	}
	
	@Override
	public List<Account> getAllAccts() {
		List<Account> acct = em.createQuery("select a from Account a").getResultList();
		return acct;
	}
	
	@Override
	public void deleteById(long id) {
		Account acct = em.find(Account.class, id);
		em.remove(acct);
	}

	@Override
	public void updateById(long id) {
		// TODO Auto-generated method stub
		//de facut!!!!!!!
	}
	
}
