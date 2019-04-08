package com.hh.HHBank.DAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hh.HHBank.Entities.Session;

@Repository
@Transactional
public class SessionDAO implements com.hh.HHBank.interfaces.ATM.SessionDAO {

	@PersistenceContext
	private EntityManager em;

	public String saveSession(Session session) {
		em.persist(session);

		return session.getUuid();
	}

	public Session getBySessionUUID(String sessionUUID) {
		Session session = null;
		try {
			session = (Session) em.createQuery("SELECT s FROM Session s WHERE sessionUUID = :sessionUUID")
					.setParameter("sessionUUID", sessionUUID).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}

	@Override
	public Session getSessionById(long id) {
		Session s = em.find(Session.class, id);
		return s;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Session> getAllSessions() {
		List<Session> allSessions = new ArrayList<Session>();
		try {
			allSessions = (List<Session>) em.createQuery("SELECT s FROM Session s").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allSessions;
	}

	@Override
	public void createSession(Session s) {
		em.persist(s);
	}

	@Override
	public boolean isValid(Session s) {
		Session session = em.find(Session.class, s.getId());
		Timestamp currentTs = new Timestamp(System.currentTimeMillis());
		Timestamp sessionExpires = new Timestamp(session.getSessionDate().getTime() + 60 * 30 * 1000);
		if (currentTs.after(sessionExpires)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String getSessionByUserId(long userId) {
		Session s = (Session) em.createQuery("SELECT s FROM Session s WHERE userid =: userID")
				.setParameter("userId", userId).getSingleResult();
		return s.getUuid().toString();
	}
}
