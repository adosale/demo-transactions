package com.demo.transactions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CREATED)
public class SuccessResponse extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public SuccessResponse(String responseMessage) {
		super(responseMessage);
	}
}
