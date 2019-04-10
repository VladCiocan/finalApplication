package com.hh.HHBank.controllers;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hh.HHBank.Entities.User;
import com.hh.HHBank.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public List<User> getAllUsers(Pageable pageable){
		return userService.getAllUsers();
	}
	
	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable Long id) {
		return userService.getUserByID(id);
	}
	
	@PutMapping("/user/{uid}")
	public void updateUser(@PathVariable long uid, @Valid @RequestBody User userReq) {
		User userTemp = userService.getUserByID(uid);
		userTemp.setEmail(userReq.getEmail());
		userTemp.setFirstName(userReq.getFirstName());
		userTemp.setLastName(userReq.getLastName());
		userTemp.setPassword(userReq.getPassword());
		userTemp.setPhone(userReq.getPhone());
		userTemp.setRole(userReq.getRole());
		userTemp.setUsername(userReq.getUsername());
		userService.updateUser(userTemp);
	}
	
	@PostMapping("/user")
	public void createUser(@Valid @RequestBody User userReq) {
		userService.createUser(userReq);
	}
	
	@DeleteMapping("/user/{uuid}")
	public void deleteUser(@PathVariable Long uuid) {
		userService.deleteUserById(uuid);
	}
	
	@PostMapping("/user/login")
	public String loginUser (@Valid String username, String password) {
		
		String message = userService.login(username, password);
		return message;
	}
	
	@PostMapping("/user/{userid}/password")
	public String changePassword (@PathVariable long userid, @Valid String password, String newPassword) {
		return userService.changePassword(userid, password, newPassword);
	}
}
