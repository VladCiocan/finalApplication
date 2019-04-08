package com.hh.HHBank.interfaces.ATM;

import java.util.List;

import com.hh.HHBank.Entities.User;

public interface UserDAO {

		public User getUserById (long id);
	
		
		public List<User> getAllUsers();
		
		public void deleteById(long id);
		
		public void updateUser(User user);
		
		public void createUser(User u);
		
		public String login(String username, String password);
		
		public String changePassword(long userId, String password, String newPassword);
}
