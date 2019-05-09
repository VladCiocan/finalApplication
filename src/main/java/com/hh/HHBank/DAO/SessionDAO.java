package com.hh.HHBank.DAO;

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

	@SuppressWarnings("unchecked")
	public Session getBySessionUUID(String sessionUUID) {
		List<Session> session = null;
		try {
			session = (List<Session>) em.createQuery("SELECT s FROM Session s WHERE uuid = :sessionUUID")
					.setParameter("sessionUUID", sessionUUID).getResultList();
			if (session.size() > 0)
				return session.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Session getSessionById(long id) {
		return null;
	}

	@Override
	public long getUserId(Session s) {
		return 0;
	}

}
