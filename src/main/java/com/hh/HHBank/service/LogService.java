package com.hh.HHBank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.HHBank.DAO.LogDAO;
import com.hh.HHBank.Entities.Logs;

@Service
public class LogService {
	@Autowired
	private LogDAO logDao;

	public void createLog(Logs l) {
		logDao.createLog(l);
	}
}
