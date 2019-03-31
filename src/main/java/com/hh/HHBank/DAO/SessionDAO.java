package com.hh.HHBank.DAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hh.HHBank.Entities.Session;


@Repository
@Transactional
public class SessionDAO {

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
	
}
