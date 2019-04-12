package com.hh.HHBank.DAO;

import java.sql.Timestamp;
import java.util.ArrayList;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		try {
			users = (List<User>) em.createQuery("select u from User u").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public void deleteById(long id) {
		User user = em.find(User.class, id);
		em.remove(user);
	}

	@Override
	public void updateUser(User user) {
		User tempUser = em.find(User.class, user.getUserID());
		tempUser.setEmail(user.getEmail());
		tempUser.setFirstName(user.getFirstName());
		tempUser.setLastName(user.getLastName());
		tempUser.setPhone(user.getEmail());
		tempUser.setRole(user.getRole());
		em.merge(tempUser);
	}

	@Override
	public void createUser(User u) {
		em.persist(u);
	}

	@Override
	public String login(String username, String password) {
		User user = null;
		String returnMessage = "";
		try {
			user = (User) em.createQuery("SELECT u from User u WHERE username = :username")
					.setParameter("username", username).getSingleResult();
			if (user != null && user.getPassword().equals(password)) {
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

				returnMessage = s.getUuid();
			} else if (user != null && !user.getPassword().equals(password)) {
				Logs l = new Logs();
				l.setActiondate(new Timestamp(System.currentTimeMillis()));
				l.setActiontype("login");
				l.setUid(user.getUserID());
				l.setMessage("Failed login");
				em.persist(l);
				em.flush();
				returnMessage =  "Password is incorrect, please try again";
			}
		} catch (Exception e) {
			returnMessage = "Login credentials are incorrect!";
		}
		return returnMessage;
	}

	@Override
	public String changePassword(long userId, String password, String newPassword) {
		User tempUser = em.find(User.class, userId);
		if(password.equals(tempUser.getPassword())) {
			tempUser.setPassword(newPassword);
			em.merge(tempUser);
			return "Password change successfull!";
		} else {
			return "Current password is incorrect, please try again!";
		}
	}

}
