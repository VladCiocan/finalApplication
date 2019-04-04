package com.hh.HHBank.interfaces.Client;

import java.util.List;

import com.hh.HHBank.Entities.User;

public interface UserDAO {

	public User getUserLogin (String Username, String Password);
	
	public User getUserById (long id);
	
	public List<User> getAllUsers();
	
	public void deleteById(long id);
	
	public void updateById(long id);
	
	public void createUser(User u);
	
}
