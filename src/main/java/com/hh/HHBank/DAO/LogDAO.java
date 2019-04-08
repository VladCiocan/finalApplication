package com.hh.HHBank.DAO;

import java.sql.Timestamp;
import java.util.ArrayList;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Logs> getAllLogs() {
		List<Logs> logs = new ArrayList<Logs>();
		try {
			logs = (List<Logs>) em.createQuery("SELECT l FROM Logs l").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logs;
	}

	@Override
	public void deleteById(long id) {
		Logs log = em.find(Logs.class, id);
		em.remove(log);
	}

	@Override
	public void updateLog(Logs log) {
		Logs tempLog = em.find(Logs.class, log.getId());
		tempLog.setActiontype(log.getActiontype());
		tempLog.setMessage(log.getMessage());
		em.merge(tempLog);
	}

	@Override
	public void createLog(Logs l) {
		em.persist(l);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Logs> getLogsByUserId(long userId) {
		List<Logs> logs = new ArrayList<Logs>();
		try {
			logs = (List<Logs>) em.createQuery("SELECT l FROM Logs l WHERE uid = :uid").setParameter("uid", userId)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Logs> getLogsByActionType(String actionType) {
		List<Logs> logs = new ArrayList<Logs>();
		try {
			logs = (List<Logs>) em.createQuery("SELECT l FROM Logs l WHERE actiontype = :actionType")
					.setParameter("actionType", actionType).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Logs> getLogsByDate(Timestamp ts, Timestamp tf) {
		List<Logs> logs = new ArrayList<Logs>();
		try {
			logs = (List<Logs>) em.createQuery("SELECT l FROM Logs l WHERE actiondate >= :ts and actiondate <= :tf")
					.setParameter("ts", ts).setParameter("tf", tf).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logs;
	}

}
