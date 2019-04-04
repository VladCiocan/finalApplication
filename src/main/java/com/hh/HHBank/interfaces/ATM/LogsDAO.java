package com.hh.HHBank.interfaces.ATM;

import java.util.List;

import com.hh.HHBank.Entities.Logs;



public interface LogsDAO {

	public Logs getLogById(long id);
	
	
	public List<Logs> getAllLogs();
	
	public void deleteById(long id);
	
	public void updateById(Logs l,long id);
	
	public void createLog(Logs l);
	
}
