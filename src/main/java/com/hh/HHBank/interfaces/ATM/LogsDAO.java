package com.hh.HHBank.interfaces.ATM;

import java.sql.Timestamp;
import java.util.List;

import com.hh.HHBank.Entities.Logs;



public interface LogsDAO {

	public Logs getLogById(long id);
	
	
	public List<Logs> getAllLogs();
	
	public void deleteById(long id);
	
	public void updateLog(Logs log);
	
	public void createLog(Logs l);
	
	public List<Logs> getLogsByUserId(long userId);
	
	public List<Logs> getLogsByActionType(String actionType);
	
	public Logs getLogsByDate(Timestamp ts);
}
