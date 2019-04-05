package com.hh.HHBank.DAO;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.hh.HHBank.Entities.Logs;
import com.hh.HHBank.Entities.Session;
import com.hh.HHBank.Entities.User;

@Repository
@Transactional
public class UserDAO implements com.hh.HHBank.interfaces.ATM.UserDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public User getUserById(long id) {
		User user = em.find(User.class, id);
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
	public void updateUser(User user) {
		em.merge(user);
	}

	@Override
	public void createUser(User u) {
		em.persist(u);
	}

	@Override
	public String login(String username, String password) {
		User user = null;
		try {
			user = (User) em.createQuery("SELECT u from User u WHERE username = :username")
					.setParameter("username", username).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (user != null && user.getPassword() == password) {
			Session s = new Session();
			s.setUserid(user.getUserID());
			s.setUuid(UUID.randomUUID().toString());
			Date d = new Date();
			s.setSessionDate(new Timestamp(d.getTime()));
			em.persist(s);
			em.flush();

			Logs l = new Logs();
			l.setActiondate(new Timestamp(System.currentTimeMillis()));
			l.setActiontype("login");
			l.setUid(user.getUserID());
			l.setMessage("Successfull login");
			em.persist(l);
			em.flush();

			return s.getUuid();
		} else if (user != null && user.getPassword() != password) {
			Logs l = new Logs();
			l.setActiondate(new Timestamp(System.currentTimeMillis()));
			l.setActiontype("login");
			l.setUid(user.getUserID());
			l.setMessage("Failed login");
			em.persist(l);
			em.flush();
			return "Password is incorrect, please try again";
		} else {
			return "Login credentials are incorrect!";
		}
	}

	@Override
	public long checkSession(String sesiune) {
		Session session = null;
		Timestamp sessionExpires;
		Timestamp currentTs = new Timestamp(System.currentTimeMillis());
		try {
			session = (Session) em.createQuery("SELECT s FROM Session s WHERE uuid = :uuid")
					.setParameter("uuid", sesiune).getSingleResult();
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
