package com.hh.HHBank.interfaces.ATM;

import com.hh.HHBank.Entities.Session;

public interface SessionDAO {
	public Session getSessionById(long id);

	public String saveSession(Session s);

	public long getUserId(Session s);

	public Session getBySessionUUID(String sessionUUID);
}