package com.hh.HHBank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.HHBank.DAO.UserDAO;
import com.hh.HHBank.Entities.User;

@Service
public class UserService {
	@Autowired
	private UserDAO userDAO;

	public User getUserById(long id) {
		User user = userDAO.getUserById(id);
		return user;
	}

	public User getUserByUsername(String username) {
		return userDAO.getUserByUsername(username);
	}

	public User getUserByLoginCredentials(String username, String password) {
		return userDAO.getUserByUsernameAndPassword(username, password);
	}
}
