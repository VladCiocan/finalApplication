package com.hh.HHBank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hh.HHBank.Entities.Logs;
import com.hh.HHBank.Entities.User;
import com.hh.HHBank.service.LogService;
import com.hh.HHBank.service.SessionService;
import com.hh.HHBank.service.UserService;
import com.hh.HHBank.util.Globals;

@RestController
public class UserController {

	@Autowired
	private UserService US;

	@Autowired
	private SessionService SS;

	@Autowired
	private LogService LS;

	@GetMapping("/atm/login")
	public String loginUserPage() {
		return "login";
	}

	@PostMapping("/atm/login")
	public String loginUser(@RequestBody User user) {

		if (US.checkLoginCredentials(user.getUsername(), user.getPassword()).equals(Globals.succesfulLoginMessage)) {
			User dbUser = US.getUserByUsername(user.getUsername());
			LS.createLog(new Logs("login", dbUser.getUserID(), "Successful login"));
			return SS.createNewSessionAndReturnIt(dbUser.getUserID());
		} else if (US.checkLoginCredentials(user.getUsername(), user.getPassword())
				.equals(Globals.incorrectPasswordMessage)) {
			User dbUser = US.getUserByUsername(user.getUsername());
			LS.createLog(new Logs("login", dbUser.getUserID(), "Failed login"));
			return Globals.incorrectPasswordMessage;
		} else {
			return Globals.noUserMessage;
		}
	}
}
