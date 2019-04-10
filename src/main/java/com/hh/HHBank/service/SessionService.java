package com.hh.HHBank.service;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.HHBank.DAO.SessionDAO;
import com.hh.HHBank.Entities.Session;


@Service
public class SessionService {
	@Autowired
	private SessionDAO sessionDAO;
	

	public String createNewSessionAndReturnIt(int userId) {
		Session session = new Session();
		
		long currentTimestamp = TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
		
		session.setSessionDate(new Timestamp(currentTimestamp));
		session.setUuid(UUID.randomUUID().toString());
		session.setUserid(userId);
		sessionDAO.saveSession(session);
		return session.getUuid();
	}
	
	public boolean isValid(Session s) {
		
		Session session = new Session ();
		session = sessionDAO.getSessionById(s.getId());
		long currentTimestamp = TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
		Timestamp currentTs = new Timestamp(currentTimestamp);
		Timestamp sessionExpires = new Timestamp(session.getSessionDate().getTime() + 60 * 30 * 1000);
		
		if (currentTs.after(sessionExpires)) {
			return false;
		} 
		else 
		{
			return true;
		}
	}
}