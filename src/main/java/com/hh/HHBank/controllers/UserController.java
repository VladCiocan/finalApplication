package com.hh.HHBank.controllers;

import java.awt.print.Pageable;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hh.HHBank.DAO.UserDAO;
import com.hh.HHBank.Entities.User;

@RestController
public class UserController {
	
	@Autowired
	private UserDAO userDao;
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userDao.getAllUsers();
	}
	
	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable Long id) {
		return userDao.getUserById(id);
	}
	
	@PutMapping("/user/{uid}")
	public void updateUser(@PathVariable Long uid, @Valid @RequestBody User userReq) {
		User userTemp = userDao.getUserById(uid);
		userTemp.setEmail(userReq.getEmail());
		userTemp.setFirstName(userReq.getFirstName());
		userTemp.setLastName(userReq.getLastName());
		userTemp.setPassword(userReq.getPassword());
		userTemp.setPhone(userReq.getEmail());
		userTemp.setRole(userReq.getRole());
		userTemp.setUsername(userReq.getUsername());
		userDao.updateById(userTemp);
	}
	
	@PostMapping("/user")
	public void createUser(@Valid @RequestBody User userReq) {
		userDao.createUser(userReq);
	}
	
	@DeleteMapping("/user/{uuid}")
	public void deleteUser(@PathVariable Long uuid) {
		userDao.deleteById(uuid);
	}
}
