package com.hh.HHBank.controller;

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

import com.hh.HHBank.Entities.Logs;
import com.hh.HHBank.interfaces.ATM.LogsDAO;

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
		return logR.getLogsByUserId(uid);
	}
	
	@GetMapping("/actiontype/{atype}/logs")
	public List<Logs> getLogsByActionType(@PathVariable String atype){
		return logR.getLogsByActionType(atype);
	}
	
	@GetMapping("/{logTs}/{logsTf}/log")
	public List<Logs> getLogByDate(@PathVariable Timestamp ts, @PathVariable Timestamp tf) {
		return logR.getLogsByDate(ts, tf);
	}
	
	@PostMapping("/log")
	public void createLog(@RequestBody @Valid Logs log) {
		logR.createLog(log);
	}
}
