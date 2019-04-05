package com.hh.HHBank.DAO;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.hh.HHBank.Entities.Logs;
import com.hh.HHBank.Entities.Session;
import com.hh.HHBank.Entities.User;

@Repository
@Transactional
public class UserDAO implements com.hh.HHBank.interfaces.ATM.UserDAO{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public User getUserById(long id) {
		User user = em.find(User.class, id);
		if (user ==null) {
			throw new EntityNotFoundException("Can't find User for ID " + id);
		}
		return user;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = em.createQuery("select u from User u").getResultList();
		return users;
	}

	@Override
	public void deleteById(long id) {
		User user = em.find(User.class, id);
		em.remove(user);
	}

	@Override
	public void updateById(User user) {
		em.merge(user);
	}

	@Override
	public void createUser(User u) {
		em.persist(u);
	}
	
	public User Login(String username, String password) {
		
		User user = null;	
		try {
			user  = (User) em.createQuery("SELECT u FROM User u WHERE username = :username AND password = :password")
					.setParameter("username", username).setParameter("password", password).getSingleResult();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		if (user != null) {
			
			Session session = new Session();
			session.setUuid(UUID.randomUUID().toString());
			long currentTimestamp = TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
			session.setSessionDate(new Timestamp(currentTimestamp));
			session.setUserid(user.getUserID());
			
			Logs l = new Logs();
			l.setActiondate(new Timestamp(currentTimestamp));
			l.setActiontype("Login");
			l.setMessage("Login Successful");
			l.setUid(user.getUserID());
			em.persist(l);
			em.persist(session);
			em.flush();
			
			return user;
			
		}
		
		Logs l = new Logs();
		long currentTimestamp = TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
		l.setActiondate(new Timestamp(currentTimestamp));
		l.setActiontype("Login");
		l.setMessage("Login Failed");
		l.setUid(user.getUserID());
		em.persist(l);
		em.flush();
		
		throw new EntityNotFoundException("User not found");
		
	}
	
	public long checkSession(String sessionUUID) {
		Session session = null;
		Timestamp sessionExpires;
		long currentTimestamp = TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
		Timestamp currentTs = new Timestamp(currentTimestamp);
		try {
			session = (Session) em.createQuery("SELECT s FROM Session s WHERE uuid = :uuid")
					.setParameter("uuid", sessionUUID).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		sessionExpires = new Timestamp(session.getSessionDate().getTime() + 60 * 30 * 1000);

		if (currentTs.after(sessionExpires)) {
			return 0;
		} else {
			return session.getUserid();
		}
	}

}
