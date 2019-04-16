package com.hh.HHBank.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExceptionNotFoundObject extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	
	public ExceptionNotFoundObject() {
		super();
	}
	public ExceptionNotFoundObject(String message) {
		super(message);
	}
	public ExceptionNotFoundObject(String message, Throwable cause) {
		super(message, cause);
	}
}
