package com.hh.HHBank.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

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
		
		if(user == null) {
			
			throw new EntityNotFoundException("User not found");
		}
		
		return user;
	}
	
	public Session checkSession() {
		
		return null;
	}

}
