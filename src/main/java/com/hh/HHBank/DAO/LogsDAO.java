package com.hh.HHBank.DAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.hh.HHBank.Entities.Logs;

@Repository
@Transactional
public class LogsDAO implements com.hh.HHBank.interfaces.ATM.LogsDAO{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Logs getLogById(long id) {
		Logs logs = em.find(Logs.class, id);
		if (logs ==null) {
			throw new EntityNotFoundException("Can't find Log for ID " + id);
		}
		return logs;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Logs> getAllLogs() {
		List<Logs> logs = new ArrayList<Logs>();
		try {
		logs = em.createQuery("select l from Logs l").getResultList();
		}catch(Exception e) {
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
	public void updateById(Logs l, long id) {
		Logs log = em.find(Logs.class, id);
		log.setActiondate(l.getActiondate());
		log.setActiontype(l.getActiontype());
		log.setMessage(l.getMessage());
		log.setUid(l.getUid());
		em.merge(log);
	}

	@Override
	public void createLog(Logs l) {
		em.persist(l);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Logs> GetByUserID(long id) {
		List<Logs> log = null;
		try {
			log  = em.createQuery("SELECT l FROM Logs l WHERE userid = :userid")
					.setParameter("userid", id).getResultList();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return log;
	}
	
	@SuppressWarnings("unchecked")
	public List<Logs> GetByActionType(String actiontype) {
		List<Logs> log = null;
		try {
			log  =  em.createQuery("SELECT l FROM Logs l WHERE actiontype = :actiontype")
					.setParameter("actiontype", actiontype).getResultList();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return log;
	}
	
	@SuppressWarnings("unchecked")
	public List<Logs> GetByDate(Timestamp ts, Timestamp tf) {
		List<Logs> log = null;
		try {
			log = (List<Logs>) em.createQuery("SELECT l FROM Logs l WHERE actiondate >= :ts and actiondate <= :tf")
					.setParameter("ts", ts).setParameter("tf", tf).getResultList();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return log;
	}
	
	public String saveLog(Logs logs) {
		em.persist(logs);
		return logs.getMessage();
	}
	

}
