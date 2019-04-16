package com.hh.HHBank.service;

import java.sql.Timestamp;
import java.util.List;
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
		
		session.setSessionDate(new Timestamp(System.currentTimeMillis()));
		session.setUuid(UUID.randomUUID().toString());
		session.setUserid(userId);
		sessionDAO.saveSession(session);
		return session.getUuid();
	}
	
	public boolean isValid(Session s) {
		
		Session session = new Session ();
		session = sessionDAO.getSessionById(s.getId());
		Timestamp currentTs = new Timestamp(System.currentTimeMillis());
		Timestamp sessionExpires = new Timestamp(session.getSessionDate().getTime() + 60 * 30 * 100);
		
		System.out.println("Current Timestamp is: " + currentTs.toLocalDateTime());
		System.out.println("Expires Timestamp is: " + sessionExpires.toLocalDateTime());
		
		if (currentTs.after(sessionExpires)) {
			return false;
		} 
		else 
		{
			return true;
		}
	}
	
	public Session getBySessionUUID(String sessionUUID) {
		return sessionDAO.getBySessionUUID(sessionUUID);
	}
	
	public Session getSessionById(long id) {
		return sessionDAO.getSessionById(id);
	}
	
	public List<Session> getAllSessions(){
		return sessionDAO.getAllSessions();
	}
	
	public String getSessionByUserId(long userId) {
		return sessionDAO.getSessionByUserId(userId);
	}
	
	public void deleteById(long id) {
		sessionDAO.deleteById(id);
	}
	
	public void updateSession(Session s) {
		sessionDAO.updateById(s);
	}
	
	public void createSession(Session s) {
		sessionDAO.createSession(s);
	}
}