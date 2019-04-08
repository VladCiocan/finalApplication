package com.hh.HHBank.controller;

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

import com.hh.HHBank.Entities.User;
import com.hh.HHBank.interfaces.ATM.UserDAO;

@RestController
public class UserController {
	@Autowired
	private UserDAO userR;
	
	@GetMapping("/users")
	public List<User> getAllUsers(Pageable pageable){
		return userR.getAllUsers();
	}
	
	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable Long id) {
		return userR.getUserById(id);
	}
	
	@PutMapping("/user/{uid}")
	public void updateUser(@PathVariable Long uid, @Valid @RequestBody User userReq) {
		userR.updateUser(userReq);
	}
	
	@PostMapping("/user")
	public void createUser(@Valid @RequestBody User userReq) {
		userR.createUser(userReq);
	}
	
	@DeleteMapping("/user/{uuid}")
	public void deleteUser(@PathVariable Long uuid) {
		userR.deleteById(uuid);
	}
}
