package com.hh.HHBank.DAO;


import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hh.HHBank.Entities.Account;
import com.hh.HHBank.Entities.Session;


@Repository
@Transactional
public class SessionDAO implements com.hh.HHBank.interfaces.Client.SessionDAO{

	@PersistenceContext	
	private EntityManager em;
	
	public String saveSession(Session session) {
		em.persist(session);
		
		return session.getUuid();	
	}
	
	public Session getBySessionUUID(String uuid) {
		Session session = null;	
		try {
			session  = (Session) em.createQuery("SELECT s FROM session s WHERE s.id = :id")
					.setParameter("sessionid", uuid).getSingleResult();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return session;
	}

	@Override
	public Session getSessionById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Session> getAllSessions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getSession(Timestamp sessionDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateById(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createSession(Session session) {
		// TODO Auto-generated method stub
		
	}
	

	
}
