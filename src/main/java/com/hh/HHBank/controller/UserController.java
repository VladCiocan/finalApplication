package com.hh.HHBank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@PostMapping("/atm/login")
	public String loginUser(@RequestParam String username, @RequestParam String password) {
		if (US.checkLoginCredentials(username, password).equals(Globals.succesfulLoginMessage)) {
			User dbUser = US.getUserByUsername(username);
			LS.createLog(new Logs(Globals.login, dbUser.getUserID(), Globals.succesfulLoginMessage));
			return SS.createNewSessionAndReturnIt(dbUser.getUserID());
		}
		return Globals.incorrectCredentialsMessage;

	}
}
