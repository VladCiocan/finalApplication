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

	@SuppressWarnings("unchecked")
	@Override
	public List<Logs> getAllLogs() {
		List<Logs> logs = null;
		try {
			logs = (List<Logs>) em.createQuery("select l from Logs l").getResultList();
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
	public void updateById(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createLog(Logs l) {
		l.setActiondate(new Timestamp(System.currentTimeMillis()));
		em.persist(l);
	}

}
