package com.hh.HHBank.DAO;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.hh.HHBank.Entities.Logs;
import com.hh.HHBank.interfaces.ATM.LogsDAO;

@Repository
@Transactional
public class LogDAO implements LogsDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Logs getLogById(long id) {
		Logs log = em.find(Logs.class, id);
		return log;
	}

	@Override
	public List<Logs> getAllLogs() {
		List<Logs> logs = em.createQuery("SELECT l FROM Logs l").getResultList();
		return logs;
	}

	@Override
	public void deleteById(long id) {
		Logs log = em.find(Logs.class, id);
		em.remove(log);
	}

	@Override
	public void updateLog(Logs log) {
		em.merge(log);
	}

	@Override
	public void createLog(Logs l) {
		em.persist(l);
	}

	@Override
	public List<Logs> getLogsByUserId(long userId) {
		List<Logs> logs = em.createQuery("SELECT l FROM Logs l WHERE uid = :uid").setParameter("uid", userId)
				.getResultList();
		return logs;
	}

	@Override
	public List<Logs> getLogsByActionType(String actionType) {
		List<Logs> logs = em.createQuery("SELECT l FROM Logs l WHERE actiontype = :actionType")
				.setParameter("actionType", actionType).getResultList();
		return logs;
	}

	@Override
	public Logs getLogsByDate(Timestamp ts) {
		Logs log = (Logs) em.createQuery("SELECT l FROM Logs l WHERE actiondate = :actiondate").setParameter("actiondate", ts).getSingleResult();
		return log;
	}

}
