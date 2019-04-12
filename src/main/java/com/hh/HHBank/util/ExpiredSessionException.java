package com.hh.HHBank.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="Expired session")
public class ExpiredSessionException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ExpiredSessionException(String errorMessage) {
        super(errorMessage);
    }

}
