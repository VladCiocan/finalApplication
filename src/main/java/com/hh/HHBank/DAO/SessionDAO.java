package com.hh.HHBank.DAO;

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
			session = (Session) em.createQuery("SELECT s FROM Session s WHERE uuid = :sessionUUID")
					.setParameter("sessionUUID", sessionUUID).getSingleResult();
		} catch (Exception e) {
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
	public long getUserId(Session s) {
		// TODO Auto-generated method stub
		return 0;
	}

}
