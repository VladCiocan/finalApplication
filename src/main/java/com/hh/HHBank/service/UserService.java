package com.hh.HHBank.service;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.hh.HHBank.DAO.UserDAO;
import com.hh.HHBank.Entities.User;


@Service
public class UserService {
	
	@Autowired
	private UserDAO userDao;
	public static String UUID;
	
	public User getUserByID (long id) {
		User user = userDao.getUserById(id);
		return user;
	}
	
	public List<User> getAllUsers(){
		List<User> users = userDao.getAllUsers();
		return users;
	}
	
	public String deleteUserById(long id) {
		userDao.deleteById(id);
		return "OK";
	}
	
	public String updateUser(User user) {
		userDao.updateById(user);
		return "OK";
	}
	
	public String createUser(User u) {
		userDao.createUser(u);
		return "OK";
	}
	
	public String login(String username, String password) {
		UUID = userDao.login(username, password);
		return UUID;
	}
	
	public String changePassword (long userid, String password, String newPassword) {	
		return userDao.changePassword(userid, password, newPassword);
	}
	
}
