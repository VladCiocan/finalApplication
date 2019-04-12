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
		// TODO Auto-generated method stub
		User user = em.find(User.class, id);
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<User>();
		users = em.createQuery("select u from User u").getResultList();
		return users;
	}

	@SuppressWarnings("unchecked")
	public User getUserByUsername(String username) {
		User user = null;
		try {
			List<User> users = (List<User>) em.createQuery("SELECT u FROM User u WHERE Username = :username")
					.setParameter("username", username).getResultList();
			if (users.size() > 0)
				user = users.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		User user = em.find(User.class, id);
		em.remove(user);
	}

	@Override
	public void updateById(long id) {
		// TODO Auto-generated method stub
		User user = em.find(User.class, id);
		em.merge(user);
	}

	@Override
	public void createUser(User u) {
		// TODO Auto-generated method stub
		em.persist(u);
	}

}
