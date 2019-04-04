package com.hh.HHBank.interfaces.ATM;

import java.util.List;

import com.hh.HHBank.Entities.Session;



public interface SessionDAO {
	public Session getSessionById(long id);
	
	public List<Session> getAllSessions();
	
	public void deleteById(long id);
	
	public void updateById(Session s, long id);
	
	public void createSession(Session s);
}