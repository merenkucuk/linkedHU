package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class UnauthorizedAction extends RuntimeException{
	
	private static final long serialVersionUID =1L;
	
	public UnauthorizedAction(String message) {
		super(message);
	}
}
