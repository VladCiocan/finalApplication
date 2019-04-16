package com.hh.HHBank.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hh.HHBank.Entities.Session;


@Repository
@Transactional
public class SessionDAO implements com.hh.HHBank.interfaces.ATM.SessionDAO{

	@PersistenceContext	
	private EntityManager em;
	
	public String saveSession(Session session) {
		em.persist(session);
		
		return session.getUuid();	
	}
	
	public Session getBySessionUUID(String sessionUUID) {
		Session session = null;	
		try {
			session  = (Session) em.createQuery("SELECT s FROM Session s WHERE uuid = :uuid")
					.setParameter("uuid", sessionUUID).getSingleResult();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return session;
	}

	@Override
	public Session getSessionById(long id) {
		Session sess = em.find(Session.class, id);
		return sess;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Session> getAllSessions() {
		List<Session> sessions = new ArrayList<Session>();
		try {
			sessions = (List<Session>)em.createQuery("SELECT s FROM Session s").getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return sessions;
	}

	@Override
	public void deleteById(long id) {
		Session sess = em.find(Session.class, id);
		em.remove(sess);
	}

	@Override
	public void updateById(Session s) {
		em.merge(s);	
	}

	@Override
	public void createSession(Session s) {
		em.persist(s);
		
	}
	
	public String getSessionByUserId(long userId) {
		Session s = (Session) em.createQuery("SELECT s FROM Session s WHERE userid =: userId")
				.setParameter("userId", userId).getSingleResult();
		return s.getUuid().toString();
	}
	
}
