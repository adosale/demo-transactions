package com.demo.transactions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class ExceededTimeException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ExceededTimeException(String errorMessage) {
		super(errorMessage);
	}
}
