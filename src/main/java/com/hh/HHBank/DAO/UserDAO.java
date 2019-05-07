package com.hh.HHBank.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

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
		users = em.createQuery("select u from User u").getResultList();
		return users;
	}

	@SuppressWarnings("unchecked")
	public User getUserByUsernameAndPassword(String username, String password) {
		List<User> user = null;
		try {
			user = (List<User>) em.createQuery("SELECT u FROM User u WHERE Username = :username and Password=:password")
					.setParameter("username", username).setParameter("password", password).getResultList();
			if(user.size()>0)
				return user.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public User getUserByUsername(String username) {
		User user = null;
		try {
			user = (User) em.createQuery("SELECT u FROM User u WHERE Username = :username")
					.setParameter("username", username).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public void deleteById(long id) {
		User user = em.find(User.class, id);
		em.remove(user);
	}

	@Override
	public void updateById(long id) {
		User user = em.find(User.class, id);
		em.merge(user);
	}

	@Override
	public void createUser(User u) {
		em.persist(u);
	}

}
