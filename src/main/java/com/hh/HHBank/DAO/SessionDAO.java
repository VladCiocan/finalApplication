package com.hh.HHBank.DAO;

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
			session  = (Session) em.createQuery("SELECT s FROM Session s WHERE sessionUUID = :sessionUUID")
					.setParameter("sessionUUID", sessionUUID).getSingleResult();
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

	@Override
	public List<Session> getAllSessions() {
		List<Session> sessions = em.createQuery("SELECT s FROM Session s").getResultList();
		return sessions;
	}

	@Override
	public void deleteById(long id) {
		Session sess = em.find(Session.class, id);
		em.remove(sess);
	}

	@Override
	public void updateById(Session s, long id) {
		Session sess = em.find(Session.class, id);
		sess.setSessionDate(s.getSessionDate());
		sess.setUserid(s.getUserid());
		sess.setUuid(s.getUuid());
		em.merge(sess);
		
	}

	@Override
	public void createSession(Session s) {
		em.persist(s);
		
	}
	
}
