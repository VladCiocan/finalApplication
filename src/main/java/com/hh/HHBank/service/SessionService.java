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
	
}