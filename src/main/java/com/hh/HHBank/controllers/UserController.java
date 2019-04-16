package com.hh.HHBank.controllers;


import java.util.List;

import javax.persistence.EntityNotFoundException;
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

import com.hh.HHBank.Entities.Session;
import com.hh.HHBank.Entities.User;
import com.hh.HHBank.config.ExceptionNotFoundObject;
import com.hh.HHBank.service.SessionService;
import com.hh.HHBank.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private SessionService sessionService;
	
	@GetMapping("/users")
	public List<User> getAllUsers(Pageable pageable){	
		System.out.println("The UUID is: "+UserService.UUID);
		Session session = sessionService.getBySessionUUID(UserService.UUID);
		if(sessionService.isValid(session)) {
			return userService.getAllUsers();
		}
		throw new ExceptionNotFoundObject("Your session is expired!");
	}
	
	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable Long id) {
		Session session = sessionService.getBySessionUUID(UserService.UUID);
		if(sessionService.isValid(session)) {
			return userService.getUserByID(id);
		}
		throw new ExceptionNotFoundObject("Your session is expired!");
	}
	
	@PutMapping("/user/{uid}")
	public String updateUser(@PathVariable long uid, @Valid @RequestBody User userReq) {
		Session session = sessionService.getBySessionUUID(UserService.UUID);
		if(sessionService.isValid(session)) {
			User userTemp = userService.getUserByID(uid);
			userTemp.setEmail(userReq.getEmail());
			userTemp.setFirstName(userReq.getFirstName());
			userTemp.setLastName(userReq.getLastName());
			userTemp.setPassword(userReq.getPassword());
			userTemp.setPhone(userReq.getPhone());
			userTemp.setRole(userReq.getRole());
			userTemp.setUsername(userReq.getUsername());
			userService.updateUser(userTemp);
			return "The user was updated!";
		}
		throw new ExceptionNotFoundObject("Your session is expired!");
	}
	
	@PostMapping("/user")
	public String createUser(@Valid @RequestBody User userReq) {
		Session session = sessionService.getBySessionUUID(UserService.UUID);
		if(sessionService.isValid(session)) {
			userService.createUser(userReq);
			return "User created!";
		}
		throw new ExceptionNotFoundObject("Your session is expired!");
	}
	
	@DeleteMapping("/user/{uuid}")
	public String deleteUser(@PathVariable Long uuid) {
		Session session = sessionService.getBySessionUUID(UserService.UUID);
		if(sessionService.isValid(session)) {
			userService.deleteUserById(uuid);
			return "User created!";
		}
		throw new ExceptionNotFoundObject("Your session is expired!");
		
	}
	
	@PostMapping("/user/login")
	public String loginUser (@Valid String username, String password) {
		return userService.login(username, password);	
	}
	
	@PostMapping("/user/{userid}/password")
	public String changePassword (@PathVariable long userid, @Valid String password, String newPassword) {
		Session session = sessionService.getBySessionUUID(UserService.UUID);
		if(sessionService.isValid(session)) {
			return userService.changePassword(userid, password, newPassword);
		}
		throw new ExceptionNotFoundObject("Your session is expired!");
		
	}
	
}
