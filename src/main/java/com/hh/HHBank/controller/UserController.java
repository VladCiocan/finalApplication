package com.hh.HHBank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hh.HHBank.Entities.Logs;
import com.hh.HHBank.Entities.User;
import com.hh.HHBank.service.LogService;
import com.hh.HHBank.service.SessionService;
import com.hh.HHBank.service.UserService;
import com.hh.HHBank.util.Globals;
import com.hh.HHBank.util.UnauthorizedException;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService US;

	@Autowired
	private SessionService SS;

	@Autowired
	private LogService LS;

	@PostMapping("/atm/login")
	public String loginUser(@RequestParam("username") String username, @RequestParam("password") String password) {
		User user = US.getUserByLoginCredentials(username, password);
		if (user == null)
			throw new UnauthorizedException("Incorrect username or password!");
		LS.createLog(new Logs(Globals.login, user.getUserID(), Globals.succesfulLoginMessage));
		return SS.createNewSessionAndReturnIt(user.getUserID());

	}
}
