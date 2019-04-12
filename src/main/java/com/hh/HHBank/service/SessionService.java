package com.hh.HHBank.service;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.HHBank.DAO.SessionDAO;
import com.hh.HHBank.Entities.Session;

@Service
public class SessionService {
	@Autowired
	private SessionDAO sessionDAO;

	public String createNewSessionAndReturnIt(long userId) {
		Session session = new Session();

		long currentTimestamp = System.currentTimeMillis();

		session.setSessionDate(new Timestamp(currentTimestamp));
		session.setUuid(UUID.randomUUID().toString());
		session.setUserid(userId);
		sessionDAO.saveSession(session);
		return session.getUuid();
	}

	public long getSessionUserID(String sessionUUID) {
		Session session = sessionDAO.getBySessionUUID(sessionUUID);
		long userid = session.getUserid();

		return userid;
	}

	public boolean isSessionValid(String sessionUUID) {
		Session session = sessionDAO.getBySessionUUID(sessionUUID);
		if (session == null)
			return false;
		Timestamp sessionDate = session.getSessionDate();
		long sessionDateTime = sessionDate.getTime();
		Timestamp currentTimestamp = new Timestamp(
				System.currentTimeMillis());
		long currentTimestampTime = currentTimestamp.getTime();

		if ((currentTimestampTime - sessionDateTime) < 1800000)
			return true;
		else
			return false;
	}

	public Session getSessionBySessionUUID(String sessionUUID) {
		return sessionDAO.getBySessionUUID(sessionUUID);
	}
}