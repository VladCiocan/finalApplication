package com.hh.HHBank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.HHBank.DAO.UserDAO;
import com.hh.HHBank.Entities.User;
import com.hh.HHBank.util.Globals;

@Service
public class UserService {
	@Autowired
	private UserDAO userDAO;

	public User getUserById(long id) {
		User user = userDAO.getUserById(id);
		return user;
	}

	public User getUserByUsername(String username) {
		User user = userDAO.getUserByUsername(username);
		return user;
	}

	public String checkLoginCredentials(String username, String password) {
		User user = userDAO.getUserByUsername(username);
		if (user != null && user.getPassword().equals(password))
			return Globals.succesfulLoginMessage;

		return Globals.incorrectCredentialsMessage;
	}
}
