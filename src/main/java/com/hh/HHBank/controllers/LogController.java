package com.hh.HHBank.controllers;

import java.awt.print.Pageable;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hh.HHBank.DAO.LogsDAO;
import com.hh.HHBank.Entities.Logs;

@RestController
public class LogController {
	@Autowired
	LogsDAO logR;
	
	@GetMapping("/logs")
	public List<Logs> getAllLogs(Pageable pageable){
		return logR.getAllLogs();
	}
	
	@GetMapping("/user/{uid}/logs")
	public List<Logs> getLogsByUserId(@PathVariable long uid){
		return logR.GetByUserID(uid);
	}
	
	@GetMapping("/actiontype/{actiontype}/logs")
	public List<Logs> getLogsByActionType(@PathVariable String actiontype){
		return logR.GetByActionType(actiontype);
	}
	
	@GetMapping("/{logTs}/log")
	public List<Logs> getLogByDate(@PathVariable Timestamp ts) {
		return logR.GetByDate(ts);
	}
	
	@PostMapping("/log")
	public void createLog(@RequestBody @Valid Logs log) {
		logR.createLog(log);
	}
}
